package project.service;

import java.util.Map;

import org.junit.Test;

import cn.tedu.ttms.product.service.TeamService;

public class TestTeamService extends TestBase{
	
	@Test
	public void testFindPageObjects() {
		TeamService teamService=ctx.getBean("teamServiceImpl",TeamService.class);
		Map<String, Object> map=teamService.findPageObjects("环球", 1);
		System.out.println(map);	
	}
}
