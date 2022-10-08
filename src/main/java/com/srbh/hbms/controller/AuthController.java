package com.srbh.hbms.controller;

import com.srbh.hbms.jwt.JwtUtil;
import com.srbh.hbms.model.entity.User;
import com.srbh.hbms.model.enums.Role;
import com.srbh.hbms.model.request.SignInRequest;
import com.srbh.hbms.model.request.SignUpRequest;
import com.srbh.hbms.service.jwtUserDetail.UserDetailsImpl;
import com.srbh.hbms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @PostMapping("/signUp")
    public String signUpUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if(userService.existsByUsername(signUpRequest.getUsername()))
            return "Error: Username already exists!";

        if(userService.existsByEmail(signUpRequest.getEmail()))
            return "Error: Email already exists!";

        if(userService.existsByMobile(signUpRequest.getMobile()))
            return "Error: Mobile no. already exists!";

        //Adding to the user table
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(
                        (signUpRequest.getRole().equalsIgnoreCase("ADMIN"))
                                ? Role.ADMIN
                                : Role.CUSTOMER
                )
                .mobile(signUpRequest.getMobile())
                .build();
        userService.addUser(user);

        return  "Registered Successfully";
    }

    @PostMapping("/signIn")
    public String generateJwtToSignIn(@Valid @RequestBody SignInRequest signInRequest)throws Exception{

        if(!userService.existsByUsername(signInRequest.getUsername()))
            return "No user with that username";

        Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),
                signInRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList()).get(0);

        return "Role: "+role+"\n"+"Token: "+jwt+"\n"+"Put this token in authorize head at the top in order to access";

    }

}
