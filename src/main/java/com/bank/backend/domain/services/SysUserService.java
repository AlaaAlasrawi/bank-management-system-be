package com.bank.backend.domain.services;

import com.bank.backend.application.exception.DuplicateResourceException;
import com.bank.backend.domain.model.SysUser;
import com.bank.backend.domain.providers.IdentityProvider;
import com.bank.backend.persistance.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SysUserService {
    private final SysUserRepository sysUserRepository;
    private final IdentityProvider identityProvider;

    public SysUser getProfile() {
        SysUser user = identityProvider.currentIdentity();
        Long id = user.getId();

        return sysUserRepository.getProfile(id);
    }

    public void updateProfile(SysUser sysUser) {
        Long id = identityProvider.currentIdentity().getId();
        validateUserInfo(sysUser);
        sysUserRepository.updateProfile(id, sysUser);
    }

    private void validateUserInfo(SysUser user) {
        if (sysUserRepository.isEmailAlreadyExists(user.getEmail())) {
            throw new DuplicateResourceException("email already exists");
        }
    }
}
