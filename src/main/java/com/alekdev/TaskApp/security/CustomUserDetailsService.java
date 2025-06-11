package com.alekdev.TaskApp.security;

import com.alekdev.TaskApp.entity.User;
import com.alekdev.TaskApp.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUserName(username).orElseThrow(()->
                new UsernameNotFoundException("User not found with username: " + username));

            return AuthUser.builder()
                    .user(user)
                    .build();
    }
}
