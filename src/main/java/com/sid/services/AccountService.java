package com.sid.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.sid.entities.AppRole;
import com.sid.entities.AppUser;
import com.sid.exceptions.AppRoleNotFoundException;
import com.sid.exceptions.AppUserNotFoundException;

public interface AccountService {
	AppUser addNewAppUser(AppUser appUser) ;
	AppRole addNewRole(AppRole appRole);
	void addRoleToUser(String username,String roleName) throws AppRoleNotFoundException, AppUserNotFoundException;
	AppUser getUserByUsername(String username) ;
	AppRole loadRoleByRoleName(String roleName) throws AppRoleNotFoundException;
	
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	List<AppUser> listUser();
	

}
