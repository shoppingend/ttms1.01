package cn.tedu.ttms.attachment.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.ttms.attachment.entity.Attachment;
import cn.tedu.ttms.attachment.service.AttachmentService;
import cn.tedu.ttms.common.web.JsonResult;

@RequestMapping("/attachment")
@Controller
public class AttachmentController {

	@Autowired
	private AttachmentService attachmentservice;
	
	@RequestMapping("/attachmentUI")
	public String attschmentUI() {
		return "attachment/attachment";
	}
	
	@RequestMapping("/doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		return new JsonResult(attachmentservice.findObjects());
	}
	@RequestMapping("/doUpload")
	@ResponseBody
	public JsonResult doUpload(String title,MultipartFile mFile) {
		attachmentservice.uploadObject(title, mFile);
		return new JsonResult("上传成功");
	}
	
	@RequestMapping("/doDownload")
	@ResponseBody
	public byte[] doDownload(Integer id,HttpServletResponse response) throws IOException {
		Attachment entity=attachmentservice.findObjectById(id);
		//设置下载内容类型以及相应头
		response.setContentType("appliction/octet-stream");
    	String fileName=URLEncoder.encode(entity.getFileName(),"utf-8");
		response.setHeader("Content-disposition","attachment;filename="+fileName);
		
		Path path=Paths.get(entity.getFilePath());
		return Files.readAllBytes(path);
	}
}
