package com.blog.cesmusic.mapper.implementations;

import com.blog.cesmusic.data.DTO.v1.output.UserDTO;
import com.blog.cesmusic.mapper.Mapper;
import com.blog.cesmusic.model.User;
import com.blog.cesmusic.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ModelMapperImplTest {

    @Autowired
    private Mapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ModelMapperImpl();
    }

    @Test
    @DisplayName("Should map User object to UserDTO")
    void mapCase1() {
        User user = new User(
                UUID.randomUUID(),
                "João Carlos",
                "joaocarlos@gmail.com",
                "password",
                Role.USER,
                "About me",
                null,
                null,
                LocalDateTime.now(),
                true
        );

        UserDTO userDTO = mapper.map(user, UserDTO.class);

        assertThat(user.getId()).isEqualTo(userDTO.getId());
        assertThat(user.getName()).isEqualTo(userDTO.getName());
        assertThat(user.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(user.getRole()).isEqualTo(userDTO.getRole());
        assertThat(user.getAbout()).isEqualTo(userDTO.getAbout());
        assertThat(user.getCreatedAt()).isEqualTo(userDTO.getCreatedAt());
        assertThat(user.getActive()).isEqualTo(userDTO.getActive());
    }

    @Test
    @DisplayName("Should map list of User objects to list of UserDTO")
    void mapCase2() {
        List<User> users = new ArrayList<>();
        users.add(new User(
                UUID.randomUUID(),
                "user 1",
                "user1@gmail.com",
                "password",
                Role.USER,
                "About user 1",

null,
                null,LocalDateTime.now(),
                true
        ));
        users.add(new User(
                UUID.randomUUID(),
                "user 2",
                "user2@gmail.com",
                "password",
                Role.USER,
                "About user 2",

null,
                null,LocalDateTime.now(),
                false
        ));
        users.add(new User(
                UUID.randomUUID(),
                "admin 1",
                "admin1@gmail.com",
                "password",
                Role.ADMIN,
                "About admin 1",
                null,
                null,
                LocalDateTime.now(),
                true
        ));
        users.add(new User(
                UUID.randomUUID(),
                "admin 2",
                "admin2@gmail.com",
                "password",
                Role.ADMIN,
                "About admin 2",
                null,
                null,
                LocalDateTime.now(),
                true
        ));

        List<UserDTO> userDTOs = mapper.map(users, UserDTO.class);

        assertThat(users.size()).isEqualTo(userDTOs.size());
        for (int i = 0; i < users.size(); i++) {
            assertThat(users.get(i).getId()).isEqualTo(userDTOs.get(i).getId());
            assertThat(users.get(i).getName()).isEqualTo(userDTOs.get(i).getName());
            assertThat(users.get(i).getEmail()).isEqualTo(userDTOs.get(i).getEmail());
            assertThat(users.get(i).getRole()).isEqualTo(userDTOs.get(i).getRole());
            assertThat(users.get(i).getAbout()).isEqualTo(userDTOs.get(i).getAbout());
            assertThat(users.get(i).getCreatedAt()).isEqualTo(userDTOs.get(i).getCreatedAt());
            assertThat(users.get(i).getActive()).isEqualTo(userDTOs.get(i).getActive());
        }
    }
}