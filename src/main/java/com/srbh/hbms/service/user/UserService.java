package com.srbh.hbms.service.user;

import com.srbh.hbms.model.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserService {

    List<User> getAllUsers();

    User getUser(int id) throws Exception;

    User addUser(User user);

    User removeUser(int id) throws Exception;

    User getUser(String username) throws UsernameNotFoundException;

    boolean existsByUsername(String user_name);

    boolean existsByEmail(String email);

    boolean existsByMobile(String email);

}