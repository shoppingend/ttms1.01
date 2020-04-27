package cn.tedu.ttms.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.common.vo.Node;
import cn.tedu.ttms.product.dao.ProductTypeDao;
import cn.tedu.ttms.product.entity.ProductType;
import cn.tedu.ttms.product.service.ProductTypeService;

@Service
public class ProductTypeServiceImpl implements ProductTypeService{

	@Autowired
	private ProductTypeDao productTypeDao;

	@Override
	public List<Map<String, Object>> findObjects() {
		return productTypeDao.findObjects();
	}

	@Override
	public void deleteObjects(Integer id) {
		//判断id的时效性
		if(id==null||id<=0)
			throw new ServiceException("id的值无效，id="+id);
		int count =productTypeDao.hasChildren(id);
		if(count>0) {
			throw new ServiceException("有子元素不能删除");
		}else {
			int rows=productTypeDao.deleteObjects(id);
			if(rows<1)
				throw new ServiceException("删除失败");
		}
	}

	@Override
	public List<Node> findZtreeNodes() {
		return productTypeDao.findZtreeNodes();
	}

	@Override
	public void saveObject(ProductType entity) {
//		System.out.println("insert.before.entity"+entity);
		if(entity==null)
			throw new ServiceException("保存不能为空");
		int rows=productTypeDao.insertObject(entity);
		if(rows<1)
			throw new ServiceException("数据保存失败");
//		System.out.println("insert.after.entity"+entity);
	}

	@Override
	public List<Map<String, Object>> findObjectById(Integer id) {
		return productTypeDao.findObjectById(id);
	}
}
