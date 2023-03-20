package com.sid.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sid.dto.AppRoleDTO;
import com.sid.dto.AppUserDTO;
import com.sid.entities.AppRole;
import com.sid.entities.AppUser;


@Service
public class DTOMapper {
	public AppUserDTO fromAppUser(AppUser appUser) {
	AppUserDTO appUserDTO = new AppUserDTO();
	BeanUtils.copyProperties(appUser, appUserDTO);
	return appUserDTO;
	}
	public AppUser fromAppUserDTO(AppUserDTO appUserDTO) {
		AppUser appUser = new AppUser();
		BeanUtils.copyProperties(appUserDTO,appUser );
		return appUser;
		}
   public AppRoleDTO fromAppRole(AppRole appRole) {
	   AppRoleDTO appRoleDTO= new AppRoleDTO();
	   BeanUtils.copyProperties(appRole, appRoleDTO);
	   return appRoleDTO;
 	   
   }
   public AppRole fromAppRoleDTO(AppRoleDTO appRoleDTO) {
	   AppRole appRole =new AppRole();
	   BeanUtils.copyProperties(appRoleDTO, appRole);
	   return appRole;
			   
   }
}
