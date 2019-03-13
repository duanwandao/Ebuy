package com.zjx.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zjx.entity.PageBean;
import com.zjx.entity.ProductBigType;
import com.zjx.service.ProductBigTypeService;
import com.zjx.service.ProductSmallTypeService;
import com.zjx.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class ProductBigTypeAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private ProductBigTypeService productBigTypeService;
	
	@Resource
	private ProductSmallTypeService productSmallTypeService;
	
	private String page;
	private String rows;
	private ProductBigType s_productBigType;
	
	private String ids;
	private ProductBigType productBigType;
	
	
	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public ProductBigType getProductBigType() {
		return productBigType;
	}
	public void setProductBigType(ProductBigType productBigType) {
		this.productBigType = productBigType;
	}
	public ProductBigType getS_productBigType() {
		return s_productBigType;
	}
	public void setS_productBigType(ProductBigType s_productBigType) {
		this.s_productBigType = s_productBigType;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}


	public String comboList()throws Exception{
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("id", "");
		jsonObject.put("name", "��ѡ��...");
		jsonArray.add(jsonObject);
		List<ProductBigType> productBigTypeList=productBigTypeService.findAllBigTypeList();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"productList","smallTypeList"});
		JSONArray rows=JSONArray.fromObject(productBigTypeList, jsonConfig);
		jsonArray.addAll(rows);
		ResponseUtil.write(ServletActionContext.getResponse(), jsonArray);
		return null;
	}
	
	public String list()throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		List<ProductBigType> productBigTypeList=productBigTypeService.findProductBigTypeList(s_productBigType, pageBean);
		long total=productBigTypeService.getProductBigTypeCount(s_productBigType);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"productList","smallTypeList"});
		JSONArray rows=JSONArray.fromObject(productBigTypeList, jsonConfig);
		JSONObject result=new JSONObject();
		result.put("rows", rows);
		result.put("total", total);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String save()throws Exception{
		JSONObject result=new JSONObject();
		productBigTypeService.saveProductBigType(productBigType);
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String delete()throws Exception{
		JSONObject result=new JSONObject();
		String idsStr[]=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			if(productSmallTypeService.existSmallTypeWithBigTypeId(Integer.parseInt(idsStr[i]))){
				result.put("exist", "��Ʒ���������ƷС��");
			}else{
				ProductBigType productBigType=productBigTypeService.getProductBigTypeById(Integer.parseInt(idsStr[i]));
				productBigTypeService.delete(productBigType);
			}
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
}
