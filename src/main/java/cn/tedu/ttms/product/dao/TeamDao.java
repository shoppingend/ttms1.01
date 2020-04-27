package cn.tedu.ttms.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.tedu.ttms.product.entity.Team;

public interface TeamDao {

	/**
	 * @param name 查询时用户输入的项目名
	 * @param startIndex 分页查询时的起始位置
	 * @param pageSize 每页最多显示多少条记录
	 * @return 当前页数据
	 */
	List<Map<String, Object>> findPageObjects(
			@Param("name")String name,
			@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);
	
	/**
	 * @return 返回记录总数
	 */
	int getRowCount(@Param("name") String name);
	
	int insertObject(Team entity);
	
	Team findObjectById(Integer id);
	
	int updateObject(Team entity);
	
	int validById(
			@Param("valid") Integer valid,
			@Param("ids") String[] ids);
}
