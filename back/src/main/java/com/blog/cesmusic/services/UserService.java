package com.blog.cesmusic.services;

import com.blog.cesmusic.data.DTO.v1.auth.*;
import com.blog.cesmusic.data.DTO.v1.output.PendingUserDTO;
import com.blog.cesmusic.data.DTO.v1.output.RegisterResponseDTO;
import com.blog.cesmusic.data.DTO.v1.output.UserDTO;
import com.blog.cesmusic.exceptions.auth.*;
import com.blog.cesmusic.exceptions.general.ResourceNotFoundException;
import com.blog.cesmusic.mapper.Mapper;
import com.blog.cesmusic.model.enums.Role;
import com.blog.cesmusic.model.User;
import com.blog.cesmusic.repositories.UserRepository;
import com.blog.cesmusic.services.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UserService {
    private final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private final UserRepository repository;
    private final MailService mailService;
    private final Mapper mapper;

    @Autowired
    public UserService(
            UserRepository repository,
            MailService mailService,
            Mapper mapper
    ) {
        this.repository = repository;
        this.mailService = mailService;
        this.mapper = mapper;
    }

    public UserDTO findById(UUID id) {
        LOGGER.info("Finding user by id");
        return mapper.map(
                repository.findById(id)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(String.format("Nenhum usuário encontrado para este id (%s)", id))
                    ),
                UserDTO.class
        );
    }

    public UserDTO findProfileById(UUID id) {
        LOGGER.info("Finding user by id");
        return mapper.map(
                repository.findProfileById(id)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(String.format("Nenhum perfil ativo encontrado para este id (%s)", id))
                    ),
                UserDTO.class
        );
    }

    private User findEntityById(UUID id) {
        LOGGER.info("Finding user by id");

        return repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("No records found for this id")
        );
    }

    public UserDetails findUserDetailsByEmail(String email) {
        LOGGER.info("Finding user by email");

        return repository.findUserDetailsByEmail(email);
    }

    public UserDTO findByEmail(String email) {
        LOGGER.info("Finding user by email");

        return mapper.map(
                repository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Nenhum usuáro encontrado para este email")),
                UserDTO.class
        );
    }

    public void checkUserActivity(AuthenticationDTO data) {
        LOGGER.info("Checking if user is inactive");
        if (!findByEmail(data.getEmail()).getActive()) throw new InactiveUserException("Usuário inativo");
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDTO register(PendingUserDTO data) {
        LOGGER.info("Registering new user");
        if (emailAlreadyInUse(data.getEmail())) throw new EmailAlreadyUsedException("Este e-mail já está sendo usado");
        User entity = userFactory(data);
        UserDTO user = create(entity);
        sendMailToAdmins(user);
        return user;
    }

    private UserDTO create(User entity) {
        LOGGER.info("Creating new user");
        return mapper.map(
                repository.save(entity),
                UserDTO.class
        );
    }

    private User userFactory(PendingUserDTO data) {
        return new User(
                data.getName(),
                data.getEmail(),
                data.getPassword(),
                Role.USER,
                "",
                data.getCreatedAt(),
                false
        );
    }

    public RegisterResponseDTO acceptUser(UUID id) {
        LOGGER.info("Accepting a user");
        User entity = findEntityById(id);
        if (entity.getActive()) throw new UserIsAlreadyActiveException("Este usuário já foi aceito");
        entity.setActive(true);
        User user = repository.save(entity);
        mailService.sendAcceptedUserNotification(mapper.map(user, UserDTO.class));
        return mapper.map(user, RegisterResponseDTO.class);
    }

    public List<UserDTO> findInactiveUsers() {
        LOGGER.info("Finding inactive users");

        return mapper.map(
                repository.findByActiveIsFalse(),
                UserDTO.class
        );
    }

    private void sendMailToAdmins(UserDTO user) {
        for (String email : repository.findEmailsByRole(Role.ADMIN)) {
            mailService.sendNotificationOfNewRegistrationToAdmin(user, email);
        }
    }

    public boolean emailAlreadyInUse(String email) {
        return repository.findByEmail(email).isPresent();
    }
}