package com.jai.MiniBlog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jai.MiniBlog.model.Post;

public interface PostRepository extends JpaRepository<Post,Integer>
{
  
	
	
}
