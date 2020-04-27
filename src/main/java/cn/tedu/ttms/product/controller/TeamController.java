package cn.tedu.ttms.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.ttms.common.web.JsonResult;
import cn.tedu.ttms.product.entity.Team;
import cn.tedu.ttms.product.service.TeamService;

@Controller
@RequestMapping("/team")
public class TeamController {
	
	@Autowired
	private TeamService teamService;

	@RequestMapping("/listUI")
	public String listUI() {
		return "/product/team_list";
	}
	
	@RequestMapping("/editUI")
	public String editUI() {
		return "/product/team_edit";
	}
	
	@RequestMapping("/doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String name,Integer pageCurrent) {
		return new JsonResult(teamService.findPageObjects(name, pageCurrent));
	}
	
	@RequestMapping("/doFindPrjIdAndNames")
	@ResponseBody
	public JsonResult doFindPrjIdAndNames() {
		return new JsonResult(teamService.findPrjIdAndNames());
	}
	
	@RequestMapping("/doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(Team entity) {
		teamService.saveObject(entity);
		return new JsonResult("保存成功");
	}
	
	@RequestMapping("/doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		return new JsonResult(teamService.findObjectById(id));
	}
	
	@RequestMapping("/doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(Team entity) {
		teamService.updateObject(entity);
		return new JsonResult("修改成功");
	}
	
	@RequestMapping("/doValidById")
	@ResponseBody
	public JsonResult doValidById(Integer valid,String ids) {
		teamService.validById(valid, ids);
		return new JsonResult(valid==1?"启用ok":"禁用ok");
	}
}
