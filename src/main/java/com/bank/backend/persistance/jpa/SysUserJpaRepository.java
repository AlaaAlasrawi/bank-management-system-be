package com.bank.backend.persistance.jpa;

import com.bank.backend.domain.model.SysUser;
import com.bank.backend.persistance.entity.SysUserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysUserJpaRepository extends JpaRepository<SysUserEntity, Long> {
    Optional<SysUserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);


    @Modifying
    @Transactional
    @Query(value = "UPDATE sys_user u SET " +
            "u.email = COALESCE(:#{#sysUserEntity.email}, u.email), " +
            "u.full_name = COALESCE(:#{#sysUserEntity.fullName}, u.full_name), " +
            "u.phone_number = COALESCE(:#{#sysUserEntity.phoneNumber}, u.phone_number), " +
            "u.date_of_birth = COALESCE(:#{#sysUserEntity.dateOfBirth}, u.date_of_birth), " +
            "u.address = COALESCE(:#{#sysUserEntity.address}, u.address), " +
            "u.updated_at = COALESCE(:#{#sysUserEntity.updatedAt}, u.updated_at) " +
            "WHERE u.id = :id", nativeQuery = true)
    void updateProfileById(@Param("id") Long id, @Param("sysUserEntity") SysUserEntity sysUserEntity);



    @Modifying
    @Transactional
    @Query(value = "UPDATE sys_user SET password = :password WHERE id = :id", nativeQuery = true)
    void updatePassword(@Param("id") Long id, @Param("password") String password);

}
