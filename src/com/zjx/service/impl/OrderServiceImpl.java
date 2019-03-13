package com.zjx.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zjx.dao.BaseDAO;
import com.zjx.entity.Order;
import com.zjx.entity.PageBean;
import com.zjx.service.OrderService;
import com.zjx.util.StringUtil;

@Service("orderService")
public class OrderServiceImpl implements OrderService{

	@Resource
	private BaseDAO<Order> baseDAO;
	
	@Override
	public void saveOrder(Order order) {
		// TODO Auto-generated method stub
		baseDAO.save(order);
	}

	@Override
	public List<Order> findOrder(Order s_order, PageBean pageBean) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("from Order");
		if(s_order!=null){
			if(s_order.getUser()!=null&&s_order.getUser().getId()!=0){
				hql.append(" and user.id=?");
				param.add(s_order.getUser().getId());
			}
			if(s_order.getUser()!=null && StringUtil.isNotEmpty(s_order.getUser().getUserName())){
				hql.append(" and user.userName like ?");
				param.add("%"+s_order.getUser().getUserName()+"%");
			}
			if(StringUtil.isNotEmpty(s_order.getOrderNo())){
				hql.append(" and orderNo like ?");
				param.add("%"+s_order.getOrderNo()+"%");
			}
		}
		hql.append(" order by createTime desc");
		if(pageBean!=null){
			return baseDAO.find(hql.toString().replaceFirst("and", "where"), param,pageBean);
		}else{
			return baseDAO.find(hql.toString().replaceFirst("and", "where"), param);
		}
	}

	@Override
	public void updateOrderStatus(int status, String orderNo) {
		// TODO Auto-generated method stub
		List<Object> param=new LinkedList<Object>();
		String hql="update Order set status=? where orderNo=?";
		param.add(status);
		param.add(orderNo);
		baseDAO.executeHql(hql, param);
	}

	@Override
	public Long getOrderCount(Order s_order) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("select count(*) from Order");
		if(s_order!=null){
			if(s_order.getUser()!=null&&s_order.getUser().getId()!=0){
				hql.append(" and user.id=?");
				param.add(s_order.getUser().getId());
			}
			if(s_order.getUser()!=null&&StringUtil.isNotEmpty(s_order.getUser().getUserName())){
				hql.append(" and user.userName like ?");
				param.add("%"+s_order.getUser().getUserName()+"%");
			}
			if(StringUtil.isNotEmpty(s_order.getOrderNo())){
				hql.append(" and orderNo like ?");
				param.add("%"+s_order.getOrderNo()+"%");
			}
		}
		return baseDAO.count(hql.toString().replaceFirst("and", "where"), param);
	}

	@Override
	public Order getOrderById(int id) {
		// TODO Auto-generated method stub
		return baseDAO.get(Order.class, id);
	}

}
