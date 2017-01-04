package com.image.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.util.FtpUtil;

public class ImageUploaderTest {
    @Test
    public void imageUpload() {
        // create a ftp session
        FTPClient ftpClient = new FTPClient();

        try {
            // connect ip and port
            ftpClient.connect("127.0.0.1", 21);
            // login to ftp server
            ftpClient.login("wilkins.liang", "100200");
            // select image to upload
            FileInputStream inputStream = new FileInputStream(new File(
                    "C:\\Users\\WEIQIANG LIANG\\Documents\\estore\\src\\webapp\\WEB-INF\\resources\\images\\2.png"));
            // excute upload
            ftpClient.changeWorkingDirectory("/images");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.storeFile("hello1.png", inputStream);
            // logout server
            ftpClient.logout();
            // terminate session
            ftpClient.disconnect();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void imageUploadViaUtil() throws FileNotFoundException {
        FileInputStream input = new FileInputStream(new File(
                "C:\\Users\\WEIQIANG LIANG\\Documents\\estore\\src\\webapp\\WEB-INF\\resources\\images\\back1.jpg"));
        FtpUtil.uploadFile("127.0.0.1", 21, "wilkins.liang", "100200",
                "/images", "/2015/12/22","back1.jpg",input);
    }
}
