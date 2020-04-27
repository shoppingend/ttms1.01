package cn.tedu.ttms.product.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.ttms.common.vo.Node;
import cn.tedu.ttms.product.entity.ProductType;

public interface ProductTypeDao {

	List<Map<String, Object>> findObjects();
	
	int hasChildren(Integer id);
	
	int deleteObjects(Integer id);
	
	List<Node> findZtreeNodes();
	
	int insertObject(ProductType entity);
	
	List<Map<String, Object>> findObjectById(Integer id);
}
