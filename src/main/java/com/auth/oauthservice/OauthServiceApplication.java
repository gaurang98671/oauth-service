package com.auth.oauthservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import models.AuthenticateRequest;
import models.AuthenticateResponse;
@RestController
@SpringBootApplication
public class OauthServiceApplication {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired 
	private MyUserDetailService myUserdetailService;

	@Autowired
	private JwtUtils jwtUtils;

	
	@GetMapping(value = "/hello")
	public String hello()
	{
		return "Hello user";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createToken(@RequestBody AuthenticateRequest authenticateRequest) throws Exception
	{ 
		try
		{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticateRequest.getUserName(), authenticateRequest.getPassword()));
			
		}
		catch(BadCredentialsException e)
		{
			throw new Exception("Incorrect Username and Password", e);
		}
		final UserDetails userDetails= myUserdetailService.loadUserByUsername(authenticateRequest.getUserName());
		
		String jwt= jwtUtils.generateToken(userDetails) ;

		return ResponseEntity.ok(new AuthenticateResponse(jwt));
	}
	public static void main(String[] args) {
		SpringApplication.run(OauthServiceApplication.class, args);
	}

}
