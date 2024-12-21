package com.bank.backend.application.controllers;

import com.bank.backend.domain.model.SysUser;
import com.bank.backend.domain.services.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class SysUserController {
    private final SysUserService sysUserService;
    @GetMapping
    public ResponseEntity<SysUser> getProfile() {
        return ResponseEntity.ok(sysUserService.getProfile());
    }
}


