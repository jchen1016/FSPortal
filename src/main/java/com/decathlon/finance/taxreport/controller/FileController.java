package com.decathlon.finance.taxreport.controller;

import com.decathlon.finance.taxreport.config.CustomConfigUtils;
import com.decathlon.finance.taxreport.service.FileService;
import com.decathlon.finance.taxreport.service.HttpRequestService;
import com.decathlon.finance.taxreport.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/file")
@Api(value = "FileController", description = "file operation")
public class FileController {

    @Autowired
    private CustomConfigUtils config;

    @Autowired
    private FileService fileService;

    @Autowired
    private HttpRequestService httpRequestService;

//    @RequestMapping(value = "glFileUpload", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "glFileUpload", notes = "glFileUpload", httpMethod = "POST")
//    public String glFileUpload(@RequestParam("fileName") MultipartFile file) throws IOException {
//        return fileService.fileUpload(file,Constants.SRC_FOLDER_GL);
//    }

    @RequestMapping(value = "getRequest", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "getRequest", notes = "getRequest", httpMethod = "GET")
    public String glFileUpload(@RequestParam String country) throws IOException {
        //String url = "https://api-eu.subsidia.org/search-api/v1/people/search?country="+country+"&fields=ldap_uid&range=startPage-endPage";
        String url = " https://api-eu.decathlon.net//search-api/v1/people/search?country=CN";
        HttpMethod method =HttpMethod.GET;
        return httpRequestService.client(url,method,null);
    }


    @CrossOrigin
    @RequestMapping(value = "balanceFileUpload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "balanceFileUpload", notes = "balanceFileUpload", httpMethod = "POST")
    public String balanceFileUpload(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        return fileService.fileUpload(file,config.getPath()+Constants.SRC_FOLDER_BL,Constants.FILE_BL,true);
    }

    @CrossOrigin
    @RequestMapping(value = "gLFileUpload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "gLFileUpload", notes = "gLFileUpload", httpMethod = "POST")
    public String gLFileUpload(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        return fileService.fileUpload(file,config.getPath()+Constants.SRC_FOLDER_GL,Constants.FILE_GL,true);
    }


    @RequestMapping(value = "download", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "download", notes = "download excel templete", httpMethod = "GET")
    public String fileDownload() throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        fileService.downloadFile(response);
        return "successfully";
    }


    @CrossOrigin
    @RequestMapping(value = "exfeFileUpload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "exfeFileUpload", notes = "exfeFileUpload", httpMethod = "POST")
    public String exfeFileUpload(@RequestParam("file") MultipartFile file,@RequestParam String profile) throws IOException, InterruptedException {
        return fileService.fileUpload(file,config.getPath()+Constants.SRC_EXACTINENTRY_FOLDER+profile+"/",null,false);
    }
}
