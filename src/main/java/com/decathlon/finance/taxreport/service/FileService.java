package com.decathlon.finance.taxreport.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public interface FileService {

    void downloadFile(HttpServletResponse response);

    String fileUpload(MultipartFile file, String path,String targetFileName, boolean needConverExcel) throws IOException, InterruptedException;

}
