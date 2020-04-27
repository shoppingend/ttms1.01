package cn.tedu.ttms.attachment.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.ttms.attachment.dao.AttachmentDao;
import cn.tedu.ttms.attachment.entity.Attachment;
import cn.tedu.ttms.attachment.service.AttachmentService;
import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.common.util.DateUtil;

@Service
public class AttachmentServiceImpl implements AttachmentService{

	@Autowired
	private AttachmentDao attachmentDao;
	
	@Override
	public List<Attachment> findObjects() {
		return attachmentDao.findObjects();
	}
	
	@Override
	public void uploadObject(String title, MultipartFile mFile) {
		// 1.验证参数有效性
		if(StringUtils.isEmpty(title))
			throw new ServiceException("title不能为空");
		if(mFile==null)
			throw new ServiceException("请选择上传文件");
		if(mFile.isEmpty())
			throw new ServiceException("不能上传空文件");
		
		// 2.判定文件是否已上传（根据摘要信息）
		// 2.1）根据mFile内容生成摘要信息（MD5）
		String digest=null;
		byte[] bytes=null;
		try {
			bytes = mFile.getBytes();
			digest=DigestUtils.md5DigestAsHex(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("文件摘要创建失败");
		}
		// 2.2）根据摘要信息进行数据库查询
		int count=attachmentDao.getRowCountByDigest(digest);
		// 2.3）根据查询结果判断文件是否已上传
		if(count>0)
			throw new ServiceException("文件已经上传，不能再上传了");
		// 3.假如文件不在则上传文件
//		SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
//		String dateDir=sdf.format(new Date());
		String dateDir=DateUtil.format(new Date());
		String baseDir="d:/programUpload/";
		File uploadDir=new File(baseDir+dateDir);
		if(!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		String srcFileName=mFile.getOriginalFilename();
		String destFileName=UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(srcFileName);
		File dest=new File(uploadDir, destFileName);
		try {
			mFile.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException("文件上传失败");
		}
		// 4.将文件信息保存至数据库
		Attachment entity =new Attachment();
		entity.setTitle(title);
		entity.setFileName(mFile.getOriginalFilename());
		entity.setContentType(mFile.getContentType());
		entity.setFilePath(dest.getAbsolutePath());
		entity.setFileDigest(digest);
		entity.setAthType(1);
		entity.setBelongId(1);
		int rows=attachmentDao.insertObject(entity);
		// 5.验证保存结果
		if(rows<1)
			throw new ServiceException("insert error");
	}

	@Override
	public Attachment findObjectById(Integer id) {
		if(id==null)
			throw new ServiceException("id的值不能为空");
		Attachment entity=attachmentDao.findObjectById(id);
		if(entity==null)
			throw new ServiceException("没找到对应记录");
		return entity;
	}
}
