package com.zjx.service;

import java.util.List;

import com.zjx.entity.PageBean;
import com.zjx.entity.ProductBigType;

public interface ProductBigTypeService {
	
	public List<ProductBigType> findAllBigTypeList();
	
	public List<ProductBigType> findProductBigTypeList(ProductBigType s_productBigType,PageBean pageBean);
	
	public long getProductBigTypeCount(ProductBigType s_productBigType);
	
	public void saveProductBigType(ProductBigType productBigType);
	
	public void delete(ProductBigType productBigType);
	
	public ProductBigType getProductBigTypeById(int id);
	
	
}
