package com.srbh.hbms.service.user;

import com.srbh.hbms.model.entity.User;
import com.srbh.hbms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {

        //Returning list of all rows of User table
        return userRepository.findAll();
    }

    @Override
    public User getUser(int id) throws Exception {

        //Fetching user with id
        Optional<User> result = userRepository.findById(id);

        //If User fetched with id is not present
        //Throw error
        if(!result.isPresent())
            throw new Exception("No user found with id: "+id);

        //Else return the User fetched after the call
        return result.get();
    }

    @Override
    public User addUser(User user) {

        //Adding a row to User table
        return userRepository.save(user);
    }

    @Override
    public User removeUser(int id) throws Exception {

        //Fetching the user with id from above created method
        User user = getUser(id);

        //Deleting the user fetched
        userRepository.delete(user);

        //Returning the user fetched
        return user;
    }

    @Override
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent())
            throw new UsernameNotFoundException("User not found");
        return user.get();
    }
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByMobile(String mobile) {
        return userRepository.existsByMobile(mobile);
    }

}