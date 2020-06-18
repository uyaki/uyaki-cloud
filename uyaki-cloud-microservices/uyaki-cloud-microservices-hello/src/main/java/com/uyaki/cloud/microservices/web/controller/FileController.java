package com.uyaki.cloud.microservices.web.controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author uyaki
 * @date 2019-08-22 09:21
 */
@RestController
public class FileController {
    @PostMapping("/file/upload")
    public String fileUpload(@RequestParam(value="file")MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        File fileToSave = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileCopyUtils.copy(bytes,fileToSave);
        return fileToSave.getAbsolutePath();
    }
}
