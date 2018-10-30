package com.decathlon.finance.taxreport.service.impl;

import com.decathlon.finance.taxreport.service.FileService;
import com.decathlon.finance.taxreport.util.Constants;
import com.decathlon.finance.taxreport.util.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Service(value = "fileService")
public class FileServiceImpl implements FileService {

    //删除文件夹
//param folderPath 文件夹完整绝对路径

    private void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public String fileUpload(MultipartFile file, String path) throws IOException {

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
            delAllFile(dest.getParentFile().getPath());
        }
        file.transferTo(dest); //保存文件
        return dest.getName();
    }

    @Override
    public void downloadFile(HttpServletResponse response) {
        String headers[] = {"Fiscal NO", "Fiscal Name", "Store No", "Store Name"};
        ExcelUtil eU = new ExcelUtil();
        eU.exportMainDataExcel("companyInfo", headers, null, response);
    }
}
