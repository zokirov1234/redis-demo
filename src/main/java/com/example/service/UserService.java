package com.example.service;

import com.example.model.dto.UserReceiveDto;
import com.example.model.dto.UserResDto;
import com.example.model.entity.UserEntity;
import com.example.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResDto addUser(UserReceiveDto userReceiveDto) throws UserPrincipalNotFoundException {

        Optional<UserEntity> optionalUser =
                userRepository.findByUsername(userReceiveDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new UserPrincipalNotFoundException("User found exception");
        }
        UserEntity user = new UserEntity();
        user.setName(userReceiveDto.getName());
        user.setUsername(userReceiveDto.getUsername());
        user.setPassword(userReceiveDto.getPassword());
        userRepository.save(user);

        return new UserResDto(user.getName(), user.getUsername());
    }

    @SneakyThrows
    @Cacheable(value = "userCache", key = "#username")
    public UserResDto getUser(String username) {
        Optional<UserEntity> user =
                userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UserPrincipalNotFoundException("User not found");
        }

        return new UserResDto(user.get().getName(), user.get().getUsername());
    }
}
