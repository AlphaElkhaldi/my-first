package com.sid.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.sid.entities.AppRole;
import com.sid.entities.AppUser;
import com.sid.exceptions.AppRoleNotFoundException;
import com.sid.exceptions.AppUserNotFoundException;

import com.sid.repositories.AppRoleRepository;
import com.sid.repositories.AppUserRepository;

import lombok.AllArgsConstructor;
@Service @Transactional @AllArgsConstructor
public class AccountServiceImpl  implements AccountService,UserDetailsService{
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    
	@Override
	public AppUser addNewAppUser(AppUser appUser) {
		
		return appUserRepository.save(appUser);
	}

	@Override
	public AppRole addNewRole(AppRole appRole) {
	
		
		return appRoleRepository.save(appRole);
	}

	@Override
	public void addRoleToUser(String username, String roleName) throws AppRoleNotFoundException, AppUserNotFoundException {
		AppUser appUser=appUserRepository.findByUsername(username);
		AppRole appRole=appRoleRepository.findByRoleName(roleName);
		if(appRole==null)throw new AppRoleNotFoundException("Role not found");
		if(appUser==null) throw new AppUserNotFoundException("User not found");
		appUser.getAppRole().add(appRole);
		
		
	}

	@Override
	public AppUser getUserByUsername(String username)  {
		
		AppUser appUser = appUserRepository.findByUsername(username);
		
				return appUser;
	
	}

	@Override
	public List<AppUser> listUser() {
		// TODO Auto-generated method stub
		return  appUserRepository.findAll();
	}

	@Override
	public AppRole loadRoleByRoleName(String roleName) throws AppRoleNotFoundException {
		AppRole appRole=appRoleRepository.findByRoleName(roleName);
		if(appRole==null)throw new AppRoleNotFoundException("Role not found");
		return appRole;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		AppUser appUser=getUserByUsername(username);
		if(appUser==null) {
			throw new UsernameNotFoundException(String.format("User '%s' notfound", username));
		}
		else {
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			appUser.getAppRole().forEach(role->{
				authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			});
			User user = new User(appUser.getUsername(),appUser.getPassword(),authorities);
			return user;
		}
		
	}

}
