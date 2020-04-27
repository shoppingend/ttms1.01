package project.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.ttms.common.web.PageObject;
import cn.tedu.ttms.product.entity.Project;
import cn.tedu.ttms.product.service.ProjectService;

public class TestProjectService extends TestBase{
	
	@Test
	public void testFindObject() {
		ProjectService projectService = ctx.getBean("projectServiceImpl", ProjectService.class);
		List<Project> list = projectService.findObjects();
		Assert.assertNotEquals(0, list.size());
		System.out.println(list);
	}
	
	@Test
	public void testFindPageObjects() {
		ProjectService projectService=ctx.getBean("projectServiceImpl", ProjectService.class);
		Map<String, Object> map=projectService.findPageObjects("环球",1,1);
		List<Project> list=(List<Project>) map.get("list");
		PageObject pageObject=(PageObject) map.get("pageObject");
		
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1, pageObject.getPageCount());
	}
	
	@Test
	public void testvalidById() {
		ProjectService projectService=ctx.getBean("projectServiceImpl",ProjectService.class);
		Integer valid=1;
		String ids="1,3,4";
		projectService.validById(valid, ids);
	}
	
	@Test
	public void testsaveObject() {
		ProjectService projectService=ctx.getBean("projectServiceImpl",ProjectService.class);
		Project entity=new Project();
		entity.setName("东欧游");
		entity.setCode("tt-20171016-CN-BJ");
		entity.setBeginDate(new Date());
		entity.setEndDate(new Date());
		entity.setValid(1);
		entity.setNote("东欧游.....");
		projectService.saveObject(entity);
	}
	
	@Test
	public void testupdateObject() {
		ProjectService projectService=ctx.getBean("projectServiceImpl",ProjectService.class);
		Project entity= projectService.findObjectById(12);
		Assert.assertNotEquals(null, entity);
		entity.setName("越南游");
		projectService.updateObject(entity);
	}
}
