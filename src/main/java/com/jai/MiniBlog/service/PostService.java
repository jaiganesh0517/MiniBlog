package com.jai.MiniBlog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.jai.MiniBlog.model.Post;
import com.jai.MiniBlog.model.User;
import com.jai.MiniBlog.repo.PostRepository;
import com.jai.MiniBlog.repo.UserRepository;

@Service
public class PostService
{

	private UserRepository uRepo;
	private PostRepository pRepo;
	public PostService(UserRepository userR , PostRepository postR) {
		this.uRepo = userR;
		this.pRepo = postR;
	}
	
	public Post createPost(String title , String content , int userId) {
		Optional<User> ownerOpt = uRepo.findById(userId);
		User user = ownerOpt.get();
		Post newPost = new Post();
		newPost.setTitle(title);
		newPost.setContent(content);
		newPost.setOwner(user);
		newPost.setDateCreated(LocalDateTime.now());
		pRepo.save(newPost);
		return newPost;
	}
	
	public List<Post> viewFeed(){
		return pRepo.findByDeletedIsNull();
	}
	
	public Optional<Post> viewPostById(int postId){
		return pRepo.findByPostIdAndDeletedIsNull(postId);
	}
	
	public List<Post> viewSelfPosts(int userId){
		return pRepo.findByOwner_UserIdAndDeletedIsNull(userId);
	}
	
	public Post editPost(int postId , int userId , String title , String content) {
		Optional<Post> post = pRepo.findByPostIdAndDeletedIsNull(postId);
		Post uPost = post.get();
		int real = uPost.getOwner().getUserId();
		if(real == userId) {
			uPost.setTitle(title);
			uPost.setContent(content);
			uPost.setDateLastEdited(LocalDateTime.now());
		}else {
			return null;
		}
		pRepo.save(uPost);
		return uPost;
	}
	
	public Post deletePost(int postId , int userId) {
		Optional<Post> post = pRepo.findByPostIdAndDeletedIsNull(postId);
		Post uPost = post.get();
		int real = uPost.getOwner().getUserId();
		if(real == userId) {
			uPost.setDeleted(LocalDateTime.now());
		}else {
			return null;
		}
		pRepo.save(uPost);
		return uPost;
	}
	
}
