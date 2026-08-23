package com.jai.MiniBlog.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jai.MiniBlog.model.User;
import com.jai.MiniBlog.repo.UserRepository;

@Service
public class UserService 
{
 
	private UserRepository userRepo;
	private PasswordEncoder passEnco;
	public UserService(UserRepository userRepo, PasswordEncoder passEnco) {
		super();
		this.userRepo = userRepo;
		this.passEnco = passEnco;
	}
	
	public User register(String userName , String emailId , String password) {
		User user = new User();
		user.setEmailId(emailId);
		user.setUserName(userName);
		String hashPass = passEnco.encode(password);
		user.setPassword(hashPass);
		user.setRole("user");
		userRepo.save(user);
		return user;
	}
	
	public String login(String emailId , String password) {
		Optional<User> user = userRepo.findByEmailId(emailId);
		User excistingUser = user.get();
		boolean match = passEnco.matches(password, excistingUser.getPassword());
		if(match) {
			return excistingUser.getUserName();
		}else {
			return null;
		}
	}
}
