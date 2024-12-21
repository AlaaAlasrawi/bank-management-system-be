package com.bank.backend.domain.services.security;

import com.bank.backend.domain.model.SysUser;
import com.bank.backend.persistance.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserDetailsService implements UserDetailsService {
    private final SysUserRepository sysUserRepository; // mapper between sys-user and user

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserRepository.findByUsername(username);
        return User.builder()
                .username(sysUser.getUsername())
                .password(sysUser.getPassword())
                .build();
    }

}