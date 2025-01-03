package com.bank.backend.application.controllers;

import com.bank.backend.application.dtos.register.RegisterRequest;
import com.bank.backend.domain.mapper.RegisterMapper;
import com.bank.backend.domain.mapper.SysUserMapper;
import com.bank.backend.domain.model.SysUser;
import com.bank.backend.domain.services.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class SysUserController {
    private final SysUserService sysUserService;
    private final RegisterMapper registerMapper;
    @GetMapping
    public ResponseEntity<SysUser> getProfile() {
        return ResponseEntity.ok(sysUserService.getProfile());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public void updateProfile(@RequestBody RegisterRequest request) {
        sysUserService.updateProfile(registerMapper.requestToModel(request));
    }
}


