package com.bank.backend.domain.services;

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
}
