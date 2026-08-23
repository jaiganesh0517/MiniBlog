package com.jai.MiniBlog.controller;



import java.util.List;
import java.util.Optional;

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
    public Optional<Post> viewSinglePost(@PathVariable("id") int id) {
    	return postServ.viewPostById(id);
    }
    
    @GetMapping("/me")
    public List<Post> viewSelfPosts(){
    	return postServ.viewSelfPosts(1);
    }
    
    @PostMapping
    public Post createPost(@RequestBody Post post ){
    	return postServ.createPost(post.getTitle() , post.getContent(),1);
    }
    
    @PutMapping("/{id}")
    public Post editPost( @RequestBody Post post , @PathVariable("id") int id ) {
    	return postServ.editPost(id, 1,post.getTitle(), post.getContent());
    }
    
    @DeleteMapping("/{id}")
    public Post deletePost(@PathVariable("id") int id) {
    	return postServ.deletePost(id, 1);
    }

 }
