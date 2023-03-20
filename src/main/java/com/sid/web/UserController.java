package com.sid.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sid.entities.AppUser;
import com.sid.services.AccountService;
@RestController
@RequestMapping("/api")
public class UserController {
	private AccountService accountService;

	public UserController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}
	@GetMapping("/ping")
	public ResponseEntity<String> ping(){
		return ResponseEntity.ok().body("ok");
	}
	@GetMapping("/users")
	public ResponseEntity<List<AppUser>>getUsers(){
		return ResponseEntity.ok().body(accountService.listUser());
	}
	

	

}
