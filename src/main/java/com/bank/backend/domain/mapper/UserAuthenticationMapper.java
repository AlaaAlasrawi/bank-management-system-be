package com.bank.backend.domain.mapper;

import com.bank.backend.application.dtos.authentication.UserAuthenticationRequest;
import com.bank.backend.application.dtos.authentication.UserAuthenticationResponse;
import com.bank.backend.domain.model.UserAuthentication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuthenticationMapper {
    UserAuthentication requestToModel(UserAuthenticationRequest userAuthenticationRequest);
    UserAuthenticationResponse modelToResponse(UserAuthentication userAuthentication);
}
