import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class TestFastDfs {
     
    public String conf_filename = "E:\\project\\SpringBoot-Demo\\src\\main\\resources\\fdfs_client.conf";
    //public String local_filename = "D:\\stsworkspace\\fastdfs-demo\\src\\main\\resources\\fdfs_client.conf";
     
    public String local_filename = "E:\\project\\SpringBoot-Demo\\src\\main\\resources\\test111.jpg";
 
    @Before
    public void setUp() throws Exception {
    }
 
    @After
    public void tearDown() throws Exception {
    }
 
    @Test
    public void testUpload() {
 
        try { 
            ClientGlobal.init(conf_filename);
 
            TrackerClient tracker = new TrackerClient(); 
            TrackerServer trackerServer = tracker.getConnection(); 
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
//          NameValuePair nvp = new NameValuePair("age", "18"); 
            NameValuePair nvp [] = new NameValuePair[]{
                    new NameValuePair("age", "18"), 
                    new NameValuePair("sex", "male") 
            }; 
            String fileIds[] = storageClient.upload_file(local_filename, "png", nvp);
             
            System.err.println(fileIds.length);
            System.err.println(Arrays.asList(fileIds));
            System.err.println("组名：" + fileIds[0]);
            System.err.println("路径: " + fileIds[1]);
 
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } catch (MyException e) { 
            e.printStackTrace(); 
        } 
    }
 
    @Test 
    public void testDownload() {
        try {
 
            ClientGlobal.init(conf_filename);
 
            TrackerClient tracker = new TrackerClient(); 
            TrackerServer trackerServer = tracker.getConnection(); 
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
            byte[] b = storageClient.download_file("group1", "M00/00/00/wKhkgV1LMyOAAbHUAACx3PRRH-8086.png");
            System.out.println(b); 
            IOUtils.write(b, new FileOutputStream("D:/"+UUID.randomUUID().toString()+".png"));
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }
     
    @Test 
    public void testGetFileInfo(){ 
        try { 
            ClientGlobal.init(conf_filename);
 
            TrackerClient tracker = new TrackerClient(); 
            TrackerServer trackerServer = tracker.getConnection(); 
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
            FileInfo fi = storageClient.get_file_info("group1", "M00/00/00/wKhkgV1LMyOAAbHUAACx3PRRH-8086.png");
            System.out.println(fi.getSourceIpAddr()); 
            System.out.println(fi.getFileSize()); 
            System.out.println(fi.getCreateTimestamp()); 
            System.out.println(fi.getCrc32()); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
     
    @Test 
    public void testGetFileMate(){ 
        try { 
            ClientGlobal.init(conf_filename);
 
            TrackerClient tracker = new TrackerClient(); 
            TrackerServer trackerServer = tracker.getConnection(); 
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, 
                    storageServer); 
            NameValuePair nvps [] = storageClient.get_metadata("group1", "M00/00/00/wKhkgV1LMyOAAbHUAACx3PRRH-8086.png");
            for(NameValuePair nvp : nvps){ 
                System.out.println(nvp.getName() + ":" + nvp.getValue()); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
     
    @Test 
    public void testDelete(){ 
        try { 
            ClientGlobal.init(conf_filename);
 
            TrackerClient tracker = new TrackerClient(); 
            TrackerServer trackerServer = tracker.getConnection(); 
            StorageServer storageServer = null;
 
            StorageClient storageClient = new StorageClient(trackerServer, 
                    storageServer); 
            int i = storageClient.delete_file("group1", "M00/00/00/wKhkgV1LMyOAAbHUAACx3PRRH-8086.png");
            System.out.println( i==0 ? "删除成功" : "删除失败:"+i); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }
}