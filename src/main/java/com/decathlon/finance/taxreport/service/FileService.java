package com.decathlon.finance.taxreport.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public interface FileService {

    public void downloadFile(HttpServletResponse response);

    public String fileUpload(MultipartFile file, String path) throws IOException;

}
