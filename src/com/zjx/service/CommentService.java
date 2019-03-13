package com.zjx.service;

import java.util.List;

import com.zjx.entity.Comment;
import com.zjx.entity.PageBean;

public interface CommentService {
	
	public List<Comment> findCommentList(Comment s_comment,PageBean pageBean);
	
	public Long getCommentCount(Comment s_comment);
	
	public void saveComment(Comment comment);
	
	public Comment getCommentById(int commentId);
	
	public void delete(Comment comment);
}
