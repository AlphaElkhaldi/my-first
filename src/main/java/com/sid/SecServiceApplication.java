package com.sid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sid.dto.RegistrationDto;
import com.sid.entities.AppRole;
import com.sid.entities.AppUser;
import com.sid.services.AccountService;
import com.sid.services.RoleService;
import com.sid.services.UserService;

@SpringBootApplication
public class SecServiceApplication {
   
	public static void main(String[] args) {
		SpringApplication.run(SecServiceApplication.class, args);
	}
	  @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	//@Bean
   //CommandLineRunner start(AccountService accountService) {
	 //  return args->{
//		   accountService.addNewRole(new AppRole(null,"USER"));
//		   accountService.addNewRole(new AppRole(null,"ADMIN"));
//		   accountService.addNewRole(new AppRole(null,"CUSTOMER-MANAGER"));
//		   accountService.addNewRole(new AppRole(null,"PRODUCT-MANAGER"));
//		   accountService.addNewRole(new AppRole(null,"BILLS-MANAGER"));
//		   
//		   accountService.addNewAppUser(new AppUser(null,"user1","1234",new ArrayList<>()));
//		   accountService.addNewAppUser(new AppUser(null,"user2","1234",new ArrayList<>()));
//		   accountService.addNewAppUser(new AppUser(null,"user3","1234",new ArrayList<>()));
//		   accountService.addNewAppUser(new AppUser(null,"user4","1234",new ArrayList<>()));
//		   accountService.addNewAppUser(new AppUser(null,"admin","1234",new ArrayList<>()));
//		   System.out.println("00000000000000000000");
//		   accountService.addRoleToUser("user1","USER");
//		   System.out.println("00000000000000000001"+accountService.getUserByUsername("user1").getPassword());
//		   accountService.addRoleToUser("admin","USER");
//		   System.out.println("00000000000000000002");
//		   accountService.addRoleToUser("admin","ADMIN");
//		   accountService.addRoleToUser("user2","CUSTOMER-MANAGER");
//		   accountService.addRoleToUser("user2","USER");
//		   accountService.addRoleToUser("user3","USER");
//		   accountService.addRoleToUser("user3","PRODUCT-MANAGER");
//		   accountService.addRoleToUser("user4","USER");
//		   accountService.addRoleToUser("user4","BILLS-MANAGER");
	  @Bean
	    CommandLineRunner run(UserService userService, RoleService roleService) {
	        return args -> {
	            AppRole roleAdmin = roleService.save(new AppRole("ADMIN"));
	            AppRole roleUser = roleService.save(new AppRole("USER"));

	            List<AppRole> roles1 = new ArrayList<>();
	            roles1.add(roleAdmin);
	            roles1.add(roleUser);

	            List<AppRole> roles2 = new ArrayList<>();
	            roles2.add(roleUser);

	            userService.create(new RegistrationDto("John Doe", "admin", "1234"), roles1);
	            userService.create(new RegistrationDto("Jane Doe", "user1", "1234"), roles2);
	        };
	    
//		   
		  
}
}	
		
