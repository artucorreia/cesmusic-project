package com.blog.cesmusic.services;

import com.blog.cesmusic.data.DTO.v1.auth.*;
import com.blog.cesmusic.data.DTO.v1.output.PendingUserDTO;
import com.blog.cesmusic.data.DTO.v1.output.RegisterResponseDTO;
import com.blog.cesmusic.data.DTO.v1.output.UserDTO;
import com.blog.cesmusic.exceptions.auth.*;
import com.blog.cesmusic.exceptions.general.ResourceNotFoundException;
import com.blog.cesmusic.mapper.Mapper;
import com.blog.cesmusic.model.PendingUser;
import com.blog.cesmusic.repositories.PendingUserRepository;
import com.blog.cesmusic.services.mail.MailService;
import com.blog.cesmusic.services.util.LoginCodeGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class PendingUserService {
    private final Logger LOGGER = Logger.getLogger(PendingUserService.class.getName());
    private final PendingUserRepository repository;
    private final UserService userService;
    private final MailService mailService;
    private final Mapper mapper;

    @Autowired
    public PendingUserService(
            PendingUserRepository repository,
            UserService userService,
            MailService mailService,
            Mapper mapper
    ) {
        this.repository = repository;
        this.userService = userService;
        this.mailService = mailService;
        this.mapper = mapper;
    }

    public PendingUserDTO findById(Long id) {
        LOGGER.info("Finding pending user by id");

        return mapper.map(
                repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("Nenhum usuário pendente encontrado para este id (%s)", id))),
                PendingUserDTO.class
        );
    }

    public void delete(Long id) {
        LOGGER.info("Deleting a pending user by id");
        repository.delete(
                mapper.map(
                        findById(id),
                        PendingUser.class
                )
        );
    }

    @Transactional(rollbackOn = Exception.class)
    public RegisterResponseDTO register(RegisterDTO data) {
        LOGGER.info("Registering a new pending user");
        checkEmailAvailability(data);
        PendingUser entity = pendingUserFactory(data);
        mailService.sendEmailValidationCode(mapper.map(entity, PendingUserDTO.class));
        return mapper.map(create(entity), RegisterResponseDTO.class);
    }

    private PendingUserDTO findByCode(String code) {
        LOGGER.info("Finding pending user by code");
        return mapper.map(
                repository.findByCode(code)
                        .orElseThrow(
                                () -> new InvalidCodeException("Código inválido ou expirado")
                        ),
                PendingUserDTO.class
        );
    }

    public RegisterResponseDTO validateCode(String code) {
        LOGGER.info("Validating code");
        PendingUserDTO pendingUser = findByCode(code);
        UserDTO user = userService.register(pendingUser);
        delete(pendingUser.getId());
        return mapper.map(user, RegisterResponseDTO.class);
    }

    private PendingUser pendingUserFactory(RegisterDTO data) {
        String passwordEncoder = new BCryptPasswordEncoder().encode(data.getPassword());
        PendingUser result = mapper.map(data, PendingUser.class);
        result.setName(result.getName().toUpperCase());
        result.setCode(LoginCodeGenerator.generateCode());
        result.setPassword(passwordEncoder);
        return result;
    }

    private PendingUserDTO create(PendingUser entity) {
        LOGGER.info("Creating a new pending user");
        return mapper.map(
                repository.save(entity),
                PendingUserDTO.class
        );
    }

    private void checkEmailAvailability(RegisterDTO data) {
        LOGGER.info("Validating registration data");
        if (emailAlreadyInUse(data.getEmail())) throw new PendingUserAlreadyRegisteredException("Um código de validação já foi enviado para este email");
        if (userService.emailAlreadyInUse(data.getEmail())) throw new EmailAlreadyUsedException("Este email já está em uso");
    }

    private boolean emailAlreadyInUse(String email) {
        return repository.findByEmail(email).isPresent();
    }
}