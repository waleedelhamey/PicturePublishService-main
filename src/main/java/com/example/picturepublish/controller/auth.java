package com.example.picturepublish.controller;

import com.example.picturepublish.entity.Role;
import com.example.picturepublish.entity.UserInfo;
import com.example.picturepublish.repository.UserRepository;
import com.example.picturepublish.services.MyUserDetailsService;
import com.example.picturepublish.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class auth {

 @Autowired
 private AuthenticationManager authenticationManager ;

 @Autowired
 private JwtUtil jwtTokenUtil;

 @Autowired
 private UserRepository userRepository;


 @Autowired
 private MyUserDetailsService userDetailsService;

 @Autowired
 private PasswordEncoder passwordEncoder;



 @PostMapping("/auth")
 public ResponseEntity<String> createAuthToken(@RequestBody UserInfo user) throws Exception{

  Authentication authentication;
  try {
   authentication = authenticationManager.authenticate(
           new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
   );
  }
  catch (BadCredentialsException e) {
   throw new Exception("Incorrect username or password", e);
  }


  final UserDetails userDetails = userDetailsService
          .loadUserByUsername(user.getUsername());

  final String jwt = jwtTokenUtil.generateToken(userDetails);

  return ResponseEntity.ok(jwt);

 }



 @PostMapping("/signup")
 public UserInfo addNewUser(@RequestBody UserInfo user) throws Exception{
  return this.userRepository.save(UserInfo.builder().username(user.getUsername()).password(passwordEncoder.encode(user.getPassword())).role(Role.user).build());
 }

 @PreAuthorize("hasAnyAuthority('admin')")
 @PostMapping("/adminSignup")
 public UserInfo addNewAdminUser(@RequestBody UserInfo user) throws Exception{
  return this.userRepository.save(UserInfo.builder().username(user.getUsername()).password(user.getPassword()).role(Role.admin).build());
 }

}
