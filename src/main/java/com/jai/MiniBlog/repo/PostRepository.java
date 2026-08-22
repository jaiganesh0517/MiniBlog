package com.jai.MiniBlog.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jai.MiniBlog.model.Post;

public interface PostRepository extends JpaRepository<Post,Integer>
{
  
	 List<Post> findByDeletedIsNull();
	 Optional<Post> findByPostIdAndDeletedIsNull(int postId);
	 List<Post> findByOwner_UserIdAndDeletedIsNull(int userId);
}
