package com.resume.web.rest.util;

import com.resume.config.ApplicationProperties;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 上传工具类
 */
@Component
public class UploadUtils {
	private static Logger logger = LoggerFactory.getLogger(UploadUtils.class);

    private final ApplicationProperties applicationProperties;
    private static String fictitiousUrl;
    private static String documentUrl;
    private static String fileName;

    public UploadUtils(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
        fictitiousUrl = applicationProperties.getFilepath();  //虚拟路径
        fileName = applicationProperties.getAvatorpath();     //avator文件名
    }

    /**
     * 上传文件
     * @param myfile   文件流对象
     * @param fileName 存放文件夹名称
     * @return
     */
    public static String uploadDoc(MultipartFile myfile, String fileName){
    	if(!TypeUtils.isEmpty(myfile)){
            try{
                if(!myfile.isEmpty()){
                	logger.debug("上传文件长度: " + myfile.getSize());
                    logger.debug("上传文件类型: " + myfile.getContentType());
                    logger.debug("上传文件名称: " + myfile.getName());
                    logger.debug("上传文件原名: " + myfile.getOriginalFilename());
                	//截取得到文件的格式
                	String st = myfile.getOriginalFilename();
                	//获取图片名称
                	String realName = TypeUtils.getPicName(st);
                    //临时存放图片路径
                	StringBuffer tempurl=new StringBuffer();
                	//文件夹名称
                	tempurl.append(fileName.trim());
                	tempurl.append("/");

                	//向配置的虚拟路径存入图片
                    String realPath = fictitiousUrl+tempurl.toString();
                    //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
                    FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, realName));
                    //将文件全名传递到前台
                    return tempurl.toString()+realName;
                 }
    		}catch(Exception e){
    			logger.error(e.getMessage());
    			return "";
    		}
    	 }
    	return "";
    }

    public static String getFullPath(String filePath , String custom){
        String fullPath = filePath + custom;
        return fullPath.trim();
    }

    public static JSONObject start(MultipartFile myfile, String fileName) throws JSONException {
        String url = UploadUtils.uploadDoc(myfile, fileName);
        String fullUrl = documentUrl + url ;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url",fullUrl);
        return jsonObject;
    }
}
