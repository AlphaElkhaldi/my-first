package com.sid.dto;

import java.util.ArrayList;
import java.util.Collection;


import com.sid.entities.AppRole;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor
public class AppUserDTO {
	
	private  Long  id;
	private String username;
	private String password;
	private Collection<AppRole> appRole=new ArrayList<>();
	

}
