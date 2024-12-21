package com.bank.backend.domain.mapper;

import com.bank.backend.domain.model.SysUser;
import com.bank.backend.persistance.entity.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SysUserMapper {
    SysUserEntity modelToEntity(SysUser sysUser);
    SysUser entityToModel(SysUserEntity entity);

}
