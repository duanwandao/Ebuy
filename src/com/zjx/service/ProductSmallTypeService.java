package com.zjx.service;

import java.util.List;

import com.zjx.entity.PageBean;
import com.zjx.entity.ProductSmallType;

public interface ProductSmallTypeService {
	
	public List<ProductSmallType> findAllSmallTypeList(ProductSmallType s_productSmallType,PageBean pageBean);
	
	public boolean existSmallTypeWithBigTypeId(int bigTypeId);
	
	public Long getProductSmallTypeCount(ProductSmallType s_productSmallType);
	
	public void saveProductSmallType(ProductSmallType productSmallType);
	
	public void delete(ProductSmallType productSmallType);
	
	public ProductSmallType getProductSmallTypeById(int id);

}
