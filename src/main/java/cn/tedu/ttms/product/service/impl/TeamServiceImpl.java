package cn.tedu.ttms.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.common.web.PageObject;
import cn.tedu.ttms.product.dao.ProjectDao;
import cn.tedu.ttms.product.dao.TeamDao;
import cn.tedu.ttms.product.entity.Team;
import cn.tedu.ttms.product.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService{

	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public Map<String, Object> findPageObjects(String name, Integer pageCurrent) {
		int pageSize=8;
		int startIndex=(pageCurrent-1)*pageSize;
		List<Map<String, Object>> list= teamDao.findPageObjects(name,startIndex,pageSize);
		
		int rowCount=teamDao.getRowCount(name);
		System.out.println(name);
		PageObject pageObject=new PageObject();
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setStartIndex(startIndex);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list", list);
		map.put("pageObject", pageObject);
		return map;
	}
	
	@Override
	public List<Map<String, Object>> findPrjIdAndNames() {
		return projectDao.findPrjIdAndNames();
	}
	
	@Override
	public void saveObject(Team entity) {
		if(entity==null)
			throw new ServiceException("保存内容不能为空");
		int rows=teamDao.insertObject(entity);
		if(rows<1)
			throw new ServiceException("保存失败");
	}

	@Override
	public Team findObjectById(Integer id) {
		if(id==null)
			throw new ServiceException("id不能为空");
		Team entity= teamDao.findObjectById(id);
		if(entity==null)
			throw new ServiceException("对象不能为空");
		return entity;
	}

	@Override
	public void updateObject(Team entity) {
		if(entity==null)
			throw new ServiceException("保存内容不能为空");
		int rows=teamDao.updateObject(entity);
		if(rows<1)
			throw new ServiceException("保存失败");
	}

	@Override
	public void validById(Integer valid, String ids) {
		if(valid!=0&&valid!=1)
			throw new ServiceException("valid的值不合法,valid="+valid);
		if(StringUtils.isEmpty(ids))
			throw new ServiceException("ids的值不能为空");
		String[] idsArray=ids.split(",");
		int rows=teamDao.validById(valid, idsArray);
		if(rows==0)
			throw new ServiceException("修改失败");
	}
}
