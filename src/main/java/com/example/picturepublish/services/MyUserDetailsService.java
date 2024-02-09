package com.example.picturepublish.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.picturepublish.entity.UserInfo;
import com.example.picturepublish.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserInfo userInfo = userRepository.findByUsername(username);


        if (userInfo == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(userInfo);
    }
}
