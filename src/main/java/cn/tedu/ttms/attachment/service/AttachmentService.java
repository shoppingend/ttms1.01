package cn.tedu.ttms.attachment.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.tedu.ttms.attachment.entity.Attachment;

public interface AttachmentService {

	public List<Attachment> findObjects();
	
	public void uploadObject(String title,MultipartFile mFile);
	
	public Attachment findObjectById(Integer id);
}
