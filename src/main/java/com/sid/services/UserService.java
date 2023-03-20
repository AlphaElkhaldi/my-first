package com.sid.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sid.dto.RegistrationDto;
import com.sid.entities.AppRole;
import com.sid.entities.AppUser;
import com.sid.repositories.AppUserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        } else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getAppRole().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            });

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }

    public AppUser getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<AppUser> getAll() {
        return userRepository.findAll();
    }

    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }

    public AppUser create(RegistrationDto request) {
        List<AppRole> roles = new ArrayList<>();
        try {
            roles.add(roleService.getByName("USER"));
        } catch (Exception ex) {}

        return create(request, roles);
    }

    public AppUser create(RegistrationDto request, List<AppRole> roles) {
        AppUser user = new AppUser(request.name(), request.username(), passwordEncoder.encode(request.password()), roles);
        save(user);
        return user;
    }

}