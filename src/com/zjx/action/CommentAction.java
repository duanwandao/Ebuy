package com.zjx.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zjx.entity.Comment;
import com.zjx.entity.PageBean;
import com.zjx.service.CommentService;
import com.zjx.util.PageUtil;
import com.zjx.util.ResponseUtil;
import com.zjx.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class CommentAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	
	@Resource
	private CommentService commentService;
	
	private List<Comment> commentList;
	private String page;
	private String rows;
	private Long total;
	private String pageCode;
	
	private Comment s_comment;
	
	private Comment comment;
	
	private int commentId;
	private String ids;

	
	
	public List<Comment> getCommentList() {
		return commentList;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getPageCode() {
		return pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}

	public Comment getS_comment() {
		return s_comment;
	}

	public void setS_comment(Comment s_comment) {
		this.s_comment = s_comment;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}

	public String list()throws Exception{
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page), 3);
		commentList=commentService.findCommentList(s_comment, pageBean);
		total=commentService.getCommentCount(s_comment);
		pageCode=PageUtil.genPaginationNotParam(request.getContextPath()+"/comment_list.action", total, Integer.parseInt(page), 3);
		return SUCCESS;
	}
	
	public String save()throws Exception{
		if(comment.getCreateTime()==null){
			comment.setCreateTime(new Date());
		}
		commentService.saveComment(comment);
		return "save";
	}
	
	public String listComment()throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		List<Comment> commentList=commentService.findCommentList(s_comment, pageBean);
		long total=commentService.getCommentCount(s_comment);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray rows=JSONArray.fromObject(commentList, jsonConfig);
		JSONObject result=new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String loadCommentById()throws Exception{
		Comment comment=commentService.getCommentById(commentId);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONObject result=JSONObject.fromObject(comment, jsonConfig);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String replay()throws Exception{
		comment.setReplyTime(new Date());
		commentService.saveComment(comment);
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String delete()throws Exception{
		JSONObject result=new JSONObject();
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			Comment comment=commentService.getCommentById(Integer.parseInt(idsStr[i]));
			commentService.delete(comment);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
}
