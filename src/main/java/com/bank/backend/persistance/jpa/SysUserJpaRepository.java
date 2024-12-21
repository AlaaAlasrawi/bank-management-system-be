package com.bank.backend.persistance.jpa;

import com.bank.backend.domain.model.SysUser;
import com.bank.backend.persistance.entity.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysUserJpaRepository extends JpaRepository<SysUserEntity,Long> {
    Optional<SysUserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
}
