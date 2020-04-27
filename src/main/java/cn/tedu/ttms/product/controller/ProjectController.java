package cn.tedu.ttms.product.controller;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.ttms.common.web.JsonResult;
import cn.tedu.ttms.product.entity.Project;
import cn.tedu.ttms.product.service.ProjectService;

@Controller
@RequestMapping("/project")
public class ProjectController {
	private Logger log = Logger.getLogger(ProjectController.class.getName());
	
	@Autowired
	private ProjectService projectService;

	/**返回项目列表页面 */
	@RequestMapping("/listUI")
	public String listUI() {
		return "/product/project_list";
	}
	
	/**返回编辑列表页面 */
	@RequestMapping("/editUI")
	public String editUI() {
		return "/product/project_edit";
	}
	
	@RequestMapping("/doGetObjects")
	@ResponseBody
	public List<Project> doGetObjects(){
		List<Project> list=projectService.findObjects();
		return list;
	}
	
	@RequestMapping("/doGetPageObjects")
	@ResponseBody
	public JsonResult doGetPageObjects(String name,Integer valid,Integer pageCurrent) {
		Map<String, Object> map=projectService.findPageObjects(name,valid,pageCurrent);
		log.info("map="+map);
		return new JsonResult(map);
	}
	
	@RequestMapping("/doValidById")
	@ResponseBody
	public JsonResult doValidById(Integer valid,String ids) {
		projectService.validById(valid, ids);
		return new JsonResult(valid==1?"启用OK":"禁用OK");
	}
	/*{
	   * state:1,
	   * message:"ok"
	   * data:
	   * {
	     "list":[{id:1,name:"环球游",...},{...}] 
	     "pageObject":{pageCurrent:1,pageCount:2,...}
	     }
	     }
	   */
	/*
	 * @ExceptionHandler(ServiceException.class)
	 * 
	 * @ResponseBody public JsonResult handleServiceException(ServiceException e) {
	 * e.printStackTrace(); return new JsonResult(e);//state=0,message=e.getMessage
	 * }
	 */
	
	@RequestMapping("/doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(Project entity) {
		projectService.saveObject(entity);
		return new JsonResult("insert ok!");
	}
	
	@RequestMapping("/doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(Project entity) {
		projectService.updateObject(entity);
		return new JsonResult("更新 ok!");
	}
	
	@RequestMapping("/doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		Project entity=projectService.findObjectById(id);
		return new JsonResult(entity);
		
	}
}
