package com.bank.backend.persistance.repository;

import com.bank.backend.domain.model.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository {
    SysUser findByUsername(String username);

    SysUser save(SysUser user);

    boolean isUsernameAlreadyExists(String username);

    SysUser getProfile(Long id);

    void updateProfile(Long id, SysUser sysUser);

    void updatePassword(Long id, String encode);

    boolean isEmailAlreadyExists(String email);
}
