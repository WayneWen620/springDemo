package com.example.demo.modules.upload.usecase;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional // File operations are not transactional in the DB sense, but for consistency with UseCase pattern.
public class FileUploadUseCase {

    public Map<String, Object> execute(MultipartFile file) throws Exception {
        Map<String, Object> result = new HashMap<>();
        if (file.isEmpty()) {
            result.put("success", false);
            result.put("message", "未選擇檔案");
            return result;
        }
        System.out.println("file name:" + file.getOriginalFilename());
        System.out.println("file type:" + file.getContentType());
        // 確認上傳資料夾存在
        File uploadDir = new File("C:/uploads"); // Hardcoded path, should be configurable/service
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
