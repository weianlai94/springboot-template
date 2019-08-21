package com.example.demo.common.utils;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class AbstractOssClientUtil {
	public static final Logger logger = LoggerFactory.getLogger(AbstractOssClientUtil.class);
	// endpoint
	private final static String endpoint = "https://oss-cn-beijing.aliyuncs.com";
	// accessKey
	private static String accessKeyId = "xxxxx";
	private static String accessKeySecret = "xxxxxxx";
	// 空间
	private static String bucketName = "xxxxx";
	// 文件存储目录
	// private String filedir = "";
	private final static String URL = "https://xxxxx.oss-cn-beijing.aliyuncs.com/";

	// private final static String URL1="https://img.xxxx.com/";

	private OSSClient ossClient;

	public AbstractOssClientUtil() {
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}

	public  static String putObjectForBase64(InputStream inputStream,String dir, String fileName) throws FileNotFoundException {
		// 初始化OSSClient
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();
		// 必须设置ContentLength
		String fileNameWithDir;
		if(null == dir) {
			fileNameWithDir = fileName;
		}else{
			fileNameWithDir = dir + fileName;
		}
		/*ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));*/
		// 上传Object.  KEY 目前直接用file.getName()
		PutObjectResult result = client.putObject(bucketName, fileNameWithDir,inputStream);
		String code=result.getETag();
		//生成的URL  可以访问的URL
		String returnUrl= URL + fileNameWithDir;
		System.out.println("resultUri  --->"+returnUrl);
		// 打印ETag
		System.out.println(result.getETag());
		return returnUrl;
	}

    /**
	 * 初始化
	 */
	public void init() {
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}

	/**
	 * 销毁
	 */
	public void destory() {
		ossClient.shutdown();
	}
	
	/***
     * 简单存入 对应  bucket 的文件     文件是按照bucketName 存放的，例如传入"fshop" 则上传的文件在 fshop 目录下， 依次类推， 目前bucketName 
     * 无需继续建立： 这里需要注意的是，存入规则需要开发者自定，例如是userIdMOD 5 还是其他方式，最好HASH 打散， 然后信息保存到数据库记录 
     * 特别要注意的是，中文的兼容性， 上传以后，中文是否做个 alias 映射 比如 '教学视频.flv'--》aliasName--jxsp20160612.flv realName 和aliasName 关系维护在数据库中  另外访问路径啥的也都写进去、
     * @param  dir 文件目录， 例如 ："item/"
     * @param   fileName  上传文件名
     * @param  fileLength 文件大小
     * **/
    public  static String putObject(InputStream inputStream,String dir, String fileName, Long fileLength) throws FileNotFoundException {
        // 初始化OSSClient
        OSSClient client = new OSSClient(endpoint,  accessKeyId,  accessKeySecret);
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        meta.setContentLength(fileLength);


//        String fileNameWithDir;
//        if(null == dir) {
//        	fileNameWithDir = fileName;
//        }else{
//        	fileNameWithDir = dir + fileName;
//        }
        String name = "QRcode/" + fileName;
        // 上传Object.  KEY 目前直接用file.getName()
        PutObjectResult result = client.putObject(bucketName, name, inputStream, meta);
        //生成的URL  可以访问的URL
        String returnUrl= URL + name;

        return returnUrl;

    }

	/**
	 * 上传图片
	 * 
	 * @param url
	 */
	public void uploadImg2Oss(String url, String oldUrl) throws Exception {
		File fileOnServer = new File(url);
		FileInputStream fin;
		try {
			fin = new FileInputStream(fileOnServer);
			String[] split = url.split("/");
			this.uploadFile2OSS(fin, split[split.length - 1], oldUrl);
		} catch (FileNotFoundException e) {
			throw new Exception("图片上传失败");
		}
	}

	public String uploadImg2Oss(File file, String oldUrl) throws Exception {
		String originalFilename = file.getName();
		// String substring =
		// originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
		Random random = new Random();
		String name = getFileName(originalFilename);
		try {
			InputStream inputStream = new FileInputStream(file);
			String nameResult = uploadFile2OSS(inputStream, name, oldUrl);
			return nameResult;
		} catch (Exception e) {
			throw new Exception("图片上传失败");
		}
	}

	public String uploadImg2Oss(MultipartFile file, String oldUrl) throws Exception {
		if (file.getSize() > 10 * 1024 * 1024) {
			throw new Exception("上传图片大小不能超过10M！");
		}
		String originalFilename = file.getOriginalFilename();
		// String substring =
		// originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
		Random random = new Random();
		String name = getFileName(originalFilename);
		try {
			InputStream inputStream = file.getInputStream();
			String nameResult = uploadFile2OSS(inputStream, name, oldUrl);
			return nameResult;
		} catch (Exception e) {
			throw new Exception("图片上传失败");
		}
	}

	/**
	 * 获得图片路径
	 * 
	 * @param fileUrl
	 * @return
	 */
	public String getImgUrl(String fileUrl) {
		System.out.println(fileUrl);
		if (!StringUtils.isEmpty(fileUrl)) {
			String[] split = fileUrl.split("/");
			return this.getUrl(split[split.length - 1]);
		}
		return null;
	}

	/**
	 * 上传到OSS服务器 如果同名文件会覆盖服务器上的
	 * 
	 * @param instream
	 *            文件流
	 * @param fileName
	 *            文件名称 包括后缀名
	 * @return 出错返回"" ,唯一MD5数字签名
	 */
	public String uploadFile2OSS(InputStream instream, String fileName, String oldUrl) {
		String ret = "";
		String returnUrl = "";
		try {
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(instream.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			if (oldUrl == null) {
				fileName = fileName;
			} else {
				fileName = oldUrl.substring(oldUrl.lastIndexOf('/') + 1);
			}

			// 上传文件
			PutObjectResult putResult = ossClient.putObject(bucketName, "LD/" + fileName, instream, objectMetadata);

			ret = putResult.getETag();
			returnUrl = URL + "LD/" + fileName;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (instream != null) {
					instream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnUrl;
	}

	/**
	 * Description: 判断OSS服务文件上传时文件的contentType
	 * 
	 * @param FilenameExtension
	 *            文件后缀
	 * @return String
	 */
	public static String getcontentType(String filenameExtension) {
		if (filenameExtension.equalsIgnoreCase("bmp")) {
			return "image/bmp";
		}
		if (filenameExtension.equalsIgnoreCase("gif")) {
			return "image/gif";
		}
		if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
				|| filenameExtension.equalsIgnoreCase("png")) {
			return "image/jpeg";
		}
		if (filenameExtension.equalsIgnoreCase("html")) {
			return "text/html";
		}
		if (filenameExtension.equalsIgnoreCase("txt")) {
			return "text/plain";
		}
		if (filenameExtension.equalsIgnoreCase("vsd")) {
			return "application/vnd.visio";
		}
		if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
			return "application/vnd.ms-powerpoint";
		}
		if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
			return "application/msword";
		}
		if (filenameExtension.equalsIgnoreCase("xml")) {
			return "text/xml";
		}
		return "image/jpeg";
	}

	/**
	 * 获得url链接
	 * 
	 * @param key
	 * @return
	 */
	public String getUrl(String key) {
		// 设置URL过期时间为10年 3600l* 1000*24*365*10

		Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
		// 生成URL
		URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
		if (url != null) {
			return url.toString();
		}
		return null;
	}

	/**
	 * 通过原始名称获取随机名称的图片名
	 *
	 * @param filieName
	 * @return
	 */
	public static String getFileName(String fileName) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String name = simpleDateFormat.format(date);
		return name + getSixNumStr() + "_" + fileName;
	}

	/**
	 * 获取六位数字随机数
	 *
	 * @return
	 */
	public static String getSixNumStr() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	/**
	 * 将MultipartFile 转化为File
	 * @param picture
	 * @return
	 */
	private File changeMultipartFileToFile(MultipartFile picture) {
		String fileName = picture.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		File file = null;
		try {
			// 将图片转为File类型
			file = File.createTempFile("file", fileType);
			picture.transferTo(file);
			file.deleteOnExit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	/**
	 * 将oss中的图片进行相应的处理 （当宽为0时，将图片以高为标准进行等比例缩放    当高为0时，将图片以宽为标准进行等比例缩放 均为0时，则不做处理    均不为0时，则进行强制转换）
	 * @param url 图片
	 * @param width 预计图片的宽
	 * @param height 预计图片的高
	 * @return
	 */
	private static String changeSize(String url, Integer width, Integer height) {
		if (width == 0 && height == 0) {

		} else if (width == 0 && height != 0) {
			url = url + "?x-oss-process=image/resize,h_" + height;
		} else if (height == 0 && width != 0) {
			url = url + "?x-oss-process=image/resize,w_" + width;
		} else {
			url = url + "?x-oss-process=image/resize,m_fixed,h_" + height + ",w_" + width;
		}
		return url;

	}

}
