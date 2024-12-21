package com.bank.backend.domain.mapper;

import com.bank.backend.application.dtos.register.RegisterRequest;
import com.bank.backend.application.dtos.register.RegisterResponse;
import com.bank.backend.domain.model.SysUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    SysUser requestToModel(RegisterRequest registerRequest);

    RegisterResponse modelToResponse(SysUser sysUser);
}
