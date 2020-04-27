package cn.tedu.ttms.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.ttms.common.web.JsonResult;
import cn.tedu.ttms.product.entity.ProductType;
import cn.tedu.ttms.product.service.ProductTypeService;

@Controller
@RequestMapping("/type")
public class ProductTypeController {

	@Autowired
	private ProductTypeService productTypeService;
	
	@RequestMapping("/listUI")
	public String listUI() {
		return "/product/type_list";
	}
	
	@RequestMapping("/editUI")
	public String editUI() {
		return "/product/type_edit";
	}
	
	@RequestMapping("/doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		return new JsonResult(productTypeService.findObjects());
	}
	
	@RequestMapping("/doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer id) {
		productTypeService.deleteObjects(id);
		return new JsonResult("删除成功");
	}
	
	@RequestMapping("/doFindZtreeNodes")
	@ResponseBody
	public JsonResult doFindZtreeNodes() {
		return new JsonResult(productTypeService.findZtreeNodes());
	}

	@RequestMapping("/doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(ProductType entity) {
		productTypeService.saveObject(entity);
		return new JsonResult("保存成功");
	}
	
	@RequestMapping("/doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		return new JsonResult(productTypeService.findObjectById(id));
	}
}
