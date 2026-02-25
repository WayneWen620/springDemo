package com.example.demo.modules.upload.controller;

import com.example.demo.modules.upload.usecase.FileUploadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final FileUploadUseCase fileUploadUseCase;

    @PostMapping("/uploadAttach")
    public Map<String,Object> upload(@RequestParam("attach") MultipartFile file) throws Exception {
        return fileUploadUseCase.execute(file);
    }
}
