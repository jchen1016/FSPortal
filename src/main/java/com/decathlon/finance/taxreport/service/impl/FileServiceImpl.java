package com.decathlon.finance.taxreport.service.impl;

import com.decathlon.finance.taxreport.config.CustomConfigUtils;
import com.decathlon.finance.taxreport.service.FileService;
import com.decathlon.finance.taxreport.util.Constants;
import com.decathlon.finance.taxreport.util.ExcelUtil;
import com.decathlon.finance.taxreport.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service(value = "fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    private CustomConfigUtils config;

    @Override
    public String fileUpload(MultipartFile file, String path, String targetFileName, boolean needConverExcel) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        File dest = new File(path + fileName);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdirs();
        } else {
            FileUtils.delAllFile(dest.getParentFile().getPath());
        }
        file.transferTo(dest); //保存文件
        if(needConverExcel)
        {
            ConvertToAvailableExcel(path,fileName,targetFileName);
            fileName = targetFileName;
        }
        return fileName;
    }

    @Override
    public void downloadFile(HttpServletResponse response) {
        String headers[] = {"Fiscal NO", "Fiscal Name", "Store No", "Store Name"};
        ExcelUtil eU = new ExcelUtil();
        eU.exportMainDataExcel("companyInfo", headers, null, response);
    }

    private void ConvertToAvailableExcel(String filePath, String originalfileName,String targetFileName) throws IOException {
        String [] cmd={"wscript",config.getPath()+Constants.VBS_CONVERT,filePath,originalfileName,targetFileName};
        Runtime.getRuntime().exec(cmd);
    }
}
