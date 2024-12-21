package com.bank.backend.persistance.adapter;


import com.bank.backend.domain.mapper.SysUserMapper;
import com.bank.backend.domain.model.SysUser;
import com.bank.backend.persistance.jpa.SysUserJpaRepository;
import com.bank.backend.persistance.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SysUserAdapter implements SysUserRepository {
    private final SysUserJpaRepository sysUserJpaRepository;
    private final SysUserMapper sysUserMapper;

    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.entityToModel(sysUserJpaRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found")
        ));
    }

    @Override
    public SysUser save(SysUser user) {
        return sysUserMapper.entityToModel(sysUserJpaRepository.save(sysUserMapper.modelToEntity(user)));
    }

    @Override
    public boolean isUsernameAlreadyExists(String username) {
        return sysUserJpaRepository.existsByUsername(username);
    }

    @Override
    public SysUser getProfile(Long id) {
        return sysUserMapper.entityToModel(sysUserJpaRepository.findById(id).orElseThrow(
                ()->new RuntimeException("user not found")
        ));
    }
}
