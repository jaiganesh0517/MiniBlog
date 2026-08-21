package com.jai.MiniBlog.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Post
{
   @Id
   @GeneratedValue
   private int postId;
   private String title;
   private String content;
   @ManyToOne
   @JoinColumn(name ="owner_id")
   private User owner;
   private LocalDateTime dateCreated;
   private LocalDateTime dateLastEdited;
   private LocalDateTime deleted;
   
   public Post() {
	super();
	
}
   public int getPostId() {
	return postId;
   }
   public void setPostId(int postId) {
	this.postId = postId;
   }
   public String getTitle() {
	return title;
   }
   public void setTitle(String title) {
	this.title = title;
   }
   public String getContent() {
	return content;
   }
   public void setContent(String content) {
	this.content = content;
   }
   public User getOwner() {
	return owner;
   }
   public void setOwner(User owner) {
	this.owner = owner;
   }
   public LocalDateTime getDateCreated() {
	return dateCreated;
   }
   public void setDateCreated(LocalDateTime dateCreated) {
	this.dateCreated = dateCreated;
   }
   public LocalDateTime getDateLastEdited() {
	return dateLastEdited;
   }
   public void setDateLastEdited(LocalDateTime dateLastEdited) {
	this.dateLastEdited = dateLastEdited;
   }
   public LocalDateTime getDeleted() {
	return deleted;
   }
   public void setDeleted(LocalDateTime deleted) {
	this.deleted = deleted;
   }
   @Override
   public String toString() {
	return "Post [postId=" + postId + ", title=" + title + ", content=" + content + ", owner=" + owner
			+ ", dateCreated=" + dateCreated + ", dateLastEdited=" + dateLastEdited + ", deleted=" + deleted + "]";
   }
   
}
