package com.bank.backend.domain.providers;

import com.bank.backend.domain.model.SysUser;
import com.bank.backend.domain.services.security.SysUserDetailsService;
import com.bank.backend.persistance.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IdentityProvider {

    private final SysUserRepository sysUserRepository;
    private final SysUserDetailsService sysUserDetailsService;

    public SysUser currentIdentity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            if(authentication.getPrincipal() instanceof UserDetails userDetails) {
                return sysUserRepository.findByUsername(userDetails.getUsername());
            }

            if(authentication.getPrincipal() instanceof String username) {
                return sysUserRepository.findByUsername(username);
            }
        }

        return null;
    }

    public UserDetails currentIdentityDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            if(authentication.getPrincipal() instanceof UserDetails userDetails) {
                return userDetails;
            }

            if(authentication.getPrincipal() instanceof String username) {
                return sysUserDetailsService.loadUserByUsername(username);
            }
        }

        return null;
    }
}
