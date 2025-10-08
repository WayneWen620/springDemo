package com.example.demo.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UploadController {



    @PostMapping("/uploadAttach")
    public Map<String,Object> upload(@RequestParam("attach") MultipartFile file) throws Exception {
        Map<String,Object> result=new HashMap<String,Object>();
        if (file.isEmpty()) {
            result.put("success", false);
            result.put("message", "未選擇檔案");
            return result;
        }
        System.out.println("file name:"+file.getOriginalFilename());
        System.out.println("file type:"+file.getContentType());
        // 確認上傳資料夾存在
        File uploadDir = new File("C:/uploads");
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // 儲存檔案
        File dest = new File(uploadDir, file.getOriginalFilename());
        file.transferTo(dest);

        // 回傳 JSON，只回傳必要資訊
        result.put("success", true);
        result.put("fileName", file.getOriginalFilename());
        result.put("fileSize", file.getSize());
        result.put("savePath", dest.getAbsolutePath());
        return result;
    }
}
