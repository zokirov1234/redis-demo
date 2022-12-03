package com.example.controller;

import com.example.model.dto.UserReceiveDto;
import com.example.model.dto.UserResDto;
import com.example.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public UserResDto addUser(
            @RequestBody UserReceiveDto userReceiveDto
            ) throws UserPrincipalNotFoundException {
        return userService.addUser(userReceiveDto);
    }

    @GetMapping("/get/{username}")
    public UserResDto getUser(
            @PathVariable("username") String username
    ) {
        return userService.getUser(username);
    }
}
