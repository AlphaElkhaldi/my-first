package com.sid.sec;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sid.entities.AppUser;

@Component
public class JwtTokenUtil {

	private final String jwtSecret="mysecret";
	private final String jwtIssuer="code";
	
	public String generateAccesToken(AppUser appUser) {
		Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
		List<String> authorities =new ArrayList<>();
		appUser.getAppRole().forEach(appRole->{
			authorities.add(appRole.getRoleName());
		});
		return JWT.create()
				.withSubject(String.format("%s,%s", appUser.getId(),appUser.getUsername()))
				.withIssuer(jwtIssuer)
				.withClaim("roles", authorities)
				.withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
				.sign(algorithm);
	}
	public String generateRefresgToken(AppUser appUser) {
		Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
		
		return JWT.create()
				.withSubject(String.format("%s,%s", appUser.getId(),appUser.getUsername()))
				.withIssuer(jwtIssuer)
				
				.withExpiresAt(new Date(System.currentTimeMillis()+60*60*10000))
				.sign(algorithm);
	}
	public boolean validate(String  token) {
		try {
			long expireAt=JWT.decode(token).getExpiresAt().getTime();
		     Calendar cal=Calendar.getInstance();
		     if(expireAt>=cal.getTime().getTime()) {
		    	 return true;
		     }
		}catch(IllegalArgumentException e){
		    	 System.out.println(String.format("JWT is invalide -{%s}",e.getMessage()));
		     }
		
	     return false;
	}
	public String getUsername(String token) {
		String subject=JWT.decode(token).getSubject();
		return subject.split(",")[1];
	}
}
