package cn.tedu.ttms.attachment.dao;

import java.util.List;

import cn.tedu.ttms.attachment.entity.Attachment;

public interface AttachmentDao {

	List<Attachment> findObjects();
	
	int getRowCountByDigest(String fileDigest);
	
	int insertObject(Attachment entity);
	
	Attachment findObjectById(Integer id);
}
