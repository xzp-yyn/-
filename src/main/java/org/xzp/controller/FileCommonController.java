package org.xzp.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xzp.common.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/11 11:31
 * @Version 1.0
 */
@RestController
@RequestMapping("/common")
@Api(tags = "文件上传下载相关接口")
public class FileCommonController {

    @Value("${ruiji.dishimg}")
    private String imgpath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String imgname = uuid + suffix;
        File dir = new File(imgpath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(imgpath+imgname));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(imgname);
    }


    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws Exception {
        //输入流，读取图片
        FileInputStream inputStream = new FileInputStream(new File(imgpath+name));
        int len=0;
        byte[] bytes=new byte[1024];
        response.setContentType("image/jpeg");
        ServletOutputStream outputStream = response.getOutputStream();
        while ((len=inputStream.read(bytes))!=-1){
            outputStream.write(bytes);
            outputStream.flush();
        }
        inputStream.close();
        outputStream.close();
    }

}
