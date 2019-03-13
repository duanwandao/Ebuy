package com.zjx.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zjx.dao.BaseDAO;
import com.zjx.entity.Comment;
import com.zjx.entity.PageBean;
import com.zjx.service.CommentService;
import com.zjx.util.StringUtil;


@Service("commentService")
public class CommentServiceImpl implements CommentService{

	@Resource
	private BaseDAO<Comment> baseDAO;
	
	@Override
	public List<Comment> findCommentList(Comment s_comment, PageBean pageBean) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("from Comment");
		if(s_comment!=null){
			if(StringUtil.isNotEmpty(s_comment.getContent())){
				hql.append(" and content like ?");
				param.add("%"+s_comment.getContent()+"%");
			}
		}
		hql.append(" order by createTime desc");
		if(pageBean!=null){
			return baseDAO.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		}else{
			return null;
		}
	}

	@Override
	public Long getCommentCount(Comment s_comment) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("select count(*) from Comment");
		if(s_comment!=null){
			if(StringUtil.isNotEmpty(s_comment.getContent())){
				hql.append(" and content like ?");
				param.add("%"+s_comment.getContent()+"%");
			}
		}
		return baseDAO.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public void saveComment(Comment comment) {
		baseDAO.merge(comment);
	}

	@Override
	public Comment getCommentById(int commentId) {
		return baseDAO.get(Comment.class, commentId);
	}

	@Override
	public void delete(Comment comment) {
		baseDAO.delete(comment);
	}


}
