package com.tx.chatroom.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.dmframe.annotation.Controller;
import com.dmframe.annotation.UrlPattern;

@Controller
public class Upload {

	@UrlPattern(urlPath="/upload.do")
	public void upload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		String uploadPath=req.getServletContext().getRealPath("upload");
		String tempPath=System.getProperty("java.io.tmpdir");
		
		DiskFileItemFactory f = new DiskFileItemFactory();//磁盘对象	
		f.setRepository(new File(tempPath)); //设置临时目录
		f.setSizeThreshold(1024*1024); //1M的缓冲区,文件大于1M则保存到临时目录
		ServletFileUpload upload = new ServletFileUpload(f);//声明解析request的对象
		upload.setHeaderEncoding("UTF-8"); //处理文件名中文
		upload.setFileSizeMax(1024 * 1024 * 5);// 设置每个文件最大为5M
//		upload.setSizeMax(1024 * 1024 * 10);// 一共最多能上传10M
		
		try {
			List<FileItem> items = upload.parseRequest(req);
			for (FileItem fileItem : items) {
				if (fileItem.isFormField()) {
					String ds = fileItem.getString("UTF-8");// 处理中文
					System.err.println("说明是:" + ds);
				} else {
					String ss = fileItem.getName();
					ss = ss.substring(ss.lastIndexOf("\\") + 1);// 解析文件名
					FileUtils.copyInputStreamToFile( // 直接使用commons.io.FileUtils
							fileItem.getInputStream(), new File(uploadPath + "/" + ss));
					out.println("上传成功，地址为：<br/>");
					out.println(req.getContextPath() +"/upload/"+ss);
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 得到所有的文件
		
	}

}
