package com.jai.MiniBlog.controller;



import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jai.MiniBlog.model.Post;
import com.jai.MiniBlog.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
	private PostService postServ;

	public PostController(PostService postServ) {
		super();
		this.postServ = postServ;
	}
    @GetMapping
	public List<Post> viewFeed() {
        return postServ.viewFeed();
	}
    
    @GetMapping("/{id}")
    public ResponseEntity<Post> viewSinglePost(@PathVariable("id") int id) {
        Optional<Post> post = postServ.viewPostById(id);
        if(post.isPresent()) {
        	return ResponseEntity.ok(post.get());
        }
    	
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    @GetMapping("/me")
    public List<Post> viewSelfPosts(Authentication authentication){
    	int userId = (Integer)authentication.getPrincipal();
    	return postServ.viewSelfPosts(userId);
    }
    
    @PostMapping
    public Post createPost(@RequestBody Post post ,Authentication authentication ){
    	int userId =  (Integer)authentication.getPrincipal();
    	return postServ.createPost(post.getTitle() , post.getContent(),userId);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Post> editPost( @RequestBody Post post , @PathVariable("id") int id ,Authentication authentication ) {
    	int userId = (Integer)authentication.getPrincipal();
    	Post result = postServ.editPost(id, userId,post.getTitle(), post.getContent());
    	if(result == null) {
    		return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    		}
    	return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable("id") int id,Authentication authentication) {
    	int userId = (Integer)authentication.getPrincipal();
    	Post post = postServ.deletePost(id, userId);
    	if(post == null) {
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    	}
    	return ResponseEntity.ok(post);
    }

 }
