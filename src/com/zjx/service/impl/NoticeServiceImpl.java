package com.zjx.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zjx.dao.BaseDAO;
import com.zjx.entity.Notice;
import com.zjx.entity.PageBean;
import com.zjx.service.NoticeService;
import com.zjx.util.StringUtil;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{

	@Resource
	private BaseDAO <Notice> baseDAO;
	
	@Override
	public List<Notice> findNoticeList(Notice s_notice, PageBean pageBean) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("from Notice");
		if(s_notice!=null){
			if(StringUtil.isNotEmpty(s_notice.getTitle())){
				hql.append(" and title like ?");
				param.add("%"+s_notice.getTitle()+"%");
			}
		}
		if(pageBean!=null){
			return baseDAO.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		}else{
			return null;
		}
	}

	@Override
	public Notice getNoticeById(int noticeId) {
		return baseDAO.get(Notice.class, noticeId);
	}

	@Override
	public Long getNoticeCount(Notice s_notice) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("select count(*) from Notice");
		if(s_notice!=null){
			if(StringUtil.isNotEmpty(s_notice.getTitle())){
				hql.append(" and title like ?");
				param.add("%"+s_notice.getTitle()+"%");
			}
		}
		return baseDAO.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public void saveNotice(Notice notice) {
		// TODO Auto-generated method stub
		baseDAO.merge(notice);
	}

	@Override
	public void delete(Notice notice) {
		// TODO Auto-generated method stub
		baseDAO.delete(notice);
	}
}
