package com.sid.entities;

import java.util.ArrayList;
import java.util.Collection;




import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AppUser {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long  id;
	private String name;
	private String username;
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<AppRole> appRole=new ArrayList<>();

	public AppUser(String name, String username, String password, Collection<AppRole> appRole) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.appRole = appRole;
	}
	
	

}
