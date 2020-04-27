package cn.tedu.ttms.product.service;

import java.util.List;
import java.util.Map;

import cn.tedu.ttms.product.entity.Team;

public interface TeamService {

	Map<String, Object>findPageObjects(String name,
			Integer pageCurrent);
	
	List<Map<String, Object>> findPrjIdAndNames();
	
	void saveObject(Team entity);
	
	Team findObjectById(Integer id);
	
	void updateObject(Team entity);
	
	void validById(Integer valid,String ids);
}
