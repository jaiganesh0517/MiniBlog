package com.jai.MiniBlog.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jai.MiniBlog.model.User;

public interface UserRepository extends JpaRepository<User,Integer>
{

	

}
