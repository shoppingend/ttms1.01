package cn.tedu.ttms.product.service;

import java.util.List;
import java.util.Map;

import cn.tedu.ttms.product.entity.Project;

public interface ProjectService {

	List<Project> findObjects();
	
	/**
	 * @param pageCurrent 当前页
	 * @return 获取当前页数据及分页信息	
	 */
	Map<String, Object> findPageObjects(String name,
			Integer valid,
			int pageCurrent);
	
	void validById(Integer valid,String ids);
	
	void saveObject(Project entity);
	
	void updateObject(Project entity);
	
	Project findObjectById(Integer id);
}
