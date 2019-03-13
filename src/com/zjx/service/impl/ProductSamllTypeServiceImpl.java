package com.zjx.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zjx.dao.BaseDAO;
import com.zjx.entity.PageBean;
import com.zjx.entity.ProductSmallType;
import com.zjx.service.ProductSmallTypeService;
import com.zjx.util.StringUtil;

@Service("productSmallTypeService")
public class ProductSamllTypeServiceImpl implements ProductSmallTypeService{

	@Resource
	private BaseDAO<ProductSmallType> baseDAO;
	

	@Override
	public List<ProductSmallType> findAllSmallTypeList(ProductSmallType s_productSmallType,PageBean pageBean) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("from ProductSmallType");
		if(s_productSmallType!=null){
			if(s_productSmallType.getBigType()!=null&& s_productSmallType.getBigType().getId()!=0){
				hql.append(" and bigType.id=?");
				param.add(s_productSmallType.getBigType().getId());
			}
			if(s_productSmallType.getBigType()!=null && s_productSmallType.getBigType().getId()!=0){
				hql.append(" and bigType.id=?");
				param.add("%"+s_productSmallType.getBigType().getId()+"%");
			}
		}
		if(pageBean!=null){
			return baseDAO.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		}else{
			return baseDAO.find(hql.toString().replaceFirst("and", "where"), param);
		}
	}


	@Override
	public boolean existSmallTypeWithBigTypeId(int bigTypeId) {
		// TODO Auto-generated method stub
		String hql="from ProductSmallType where bigType.id="+bigTypeId;
		if(baseDAO.find(hql).size()>0){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public Long getProductSmallTypeCount(ProductSmallType s_productSmallType) {
		// TODO Auto-generated method stub
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("select count(*) from ProductSmallType");
		if(s_productSmallType!=null){
			if(StringUtil.isNotEmpty(s_productSmallType.getName())){
				hql.append(" and name like ?");
				param.add("%"+s_productSmallType.getName()+"%");
			}
		}
		return baseDAO.count(hql.toString().replaceFirst("and", "where"), param);
	}


	@Override
	public void saveProductSmallType(ProductSmallType productSmallType) {
		// TODO Auto-generated method stub
		baseDAO.merge(productSmallType);
	}


	@Override
	public void delete(ProductSmallType productSmallType) {
		// TODO Auto-generated method stub
		baseDAO.delete(productSmallType);
	}


	@Override
	public ProductSmallType getProductSmallTypeById(int id) {
		// TODO Auto-generated method stub
		return baseDAO.get(ProductSmallType.class, id);
	}

}
