package com.srbh.hbms.controller;

import com.srbh.hbms.model.entity.User;
import com.srbh.hbms.model.enums.Role;
import com.srbh.hbms.model.request.SignUpRequest;
import com.srbh.hbms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id) throws Exception {
        return userService.getUser(id);
    }

    @PostMapping
    public User addUser(@RequestBody SignUpRequest signUpRequest){
        User user =User.builder()
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword())
                .email(signUpRequest.getEmail())
                .mobile(signUpRequest.getMobile())
                .role(signUpRequest.getRole().equalsIgnoreCase("ADMIN")
                        ? Role.ADMIN:
                        Role.CUSTOMER).build();
        return userService.addUser(user);
    }
    @DeleteMapping("/{id}")
    public User removeUser(@PathVariable("id") int id) throws Exception {
        return userService.removeUser(id);
    }
}
