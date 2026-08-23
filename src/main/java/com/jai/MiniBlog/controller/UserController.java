package com.jai.MiniBlog.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jai.MiniBlog.model.User;
import com.jai.MiniBlog.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userServ;

	public UserController(UserService userServ) {
		super();
		this.userServ = userServ;
	}
	
	@PostMapping
	public User register(@RequestBody User user) {
		return userServ.register(user.getUserName(), user.getEmailId(), user.getPassword());
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		return userServ.login(user.getEmailId(), user.getPassword());
	}
}
