package com.jai.MiniBlog.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jai.MiniBlog.model.User;

public interface UserRepository extends JpaRepository<User,Integer>
{
  
	Optional<User> findByEmailId(String emailId);
	

}
