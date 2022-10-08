package com.srbh.hbms.service.jwtUserDetail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.srbh.hbms.model.entity.User;
import com.srbh.hbms.model.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID=1L;

    private int id;

    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(){}

    public UserDetailsImpl(int id, String username, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {

        //Initialize a list of granted authorities
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        //Fetch role from user
        Role role = user.getRole();

        //Convert role to string, them n to simpleGrantedAuthorities and add to the authorities list
        grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));

        //Create an object of userDetailsImpl and return it
        return new UserDetailsImpl(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o)  {
        if(this==o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        return  id == ((UserDetailsImpl) o).getId();
    }
}
