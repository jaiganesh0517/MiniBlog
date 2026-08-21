package com.jai.MiniBlog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User 
{
	@Id
	@GeneratedValue
   private int userId;
	@Column(unique = true , nullable = false)
   private String userName;
	@Column(unique = true , nullable = false)
   private String emailId;
   private String password;
   private String role;
   
   public User() {
	super();
   }
   public int getUserId() {
	return userId;
   }
   public void setUserId(int userId) {
	this.userId = userId;
   }
   public String getUserName() {
	return userName;
   }
   public void setUserName(String userName) {
	this.userName = userName;
   }
   public String getEmailId() {
	return emailId;
   }
   public void setEmailId(String emailId) {
	this.emailId = emailId;
   }
   public String getPassword() {
	return password;
   }
   public void setPassword(String password) {
	this.password = password;
   }
   public String getRole() {
	return role;
   }
   public void setRole(String role) {
	this.role = role;
   }
   @Override
   public String toString() {
	return "User [userId=" + userId + ", userName=" + userName + ", emailId=" + emailId + ", password=" + password
			+ ", role=" + role + "]";
   }
   
}
