package com.sid.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sid.dto.LoginDto;
import com.sid.dto.RegistrationDto;
import com.sid.entities.AppUser;
import com.sid.sec.JwtTokenUtil;
import com.sid.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.username(), request.password());
       
        Authentication authentication = authenticationManager.authenticate(token);

        org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        AppUser user = userService.getByUsername(userDetails.getUsername());

        String accessToken = jwtTokenUtil.generateAccesToken(user);
        String refreshToken = jwtTokenUtil.generateRefresgToken(user);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        return ResponseEntity.ok().body(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String refreshToken = authorizationHeader.substring("Bearer ".length());
            if (jwtTokenUtil.validate(refreshToken)) {
                org.springframework.security.core.userdetails.User userDetails = 
                		(org.springframework.security.core.userdetails.User) userService.loadUserByUsername(jwtTokenUtil.getUsername(refreshToken));
                AppUser user = userService.getByUsername(userDetails.getUsername());

                String accessToken = jwtTokenUtil.generateAccesToken(user);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);

                return ResponseEntity.ok().body(tokens);
            }
        }

        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("register")
    public AppUser register(@RequestBody RegistrationDto request) {
        return userService.create(request);
    }

}