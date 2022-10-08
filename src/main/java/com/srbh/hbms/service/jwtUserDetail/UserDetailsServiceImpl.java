package com.srbh.hbms.service.jwtUserDetail;

import com.srbh.hbms.model.entity.User;
import com.srbh.hbms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Fetch user from database using username
        User user = userService.getUser(username);

        //Build UserDetailsImpl of the user and then return it
        return UserDetailsImpl.build(user);

    }

}
