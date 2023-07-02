package com.prodavnica.shop2.Security;

import com.prodavnica.shop2.user.entity.User;
import com.prodavnica.shop2.user.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//    http://localhost:8080/login -> alden.efendic
// User user -> alden.efendic
// CustomUserDetails -> username, password
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not founded");
        }
        return new CustomUserDetails(user);
    }
}
