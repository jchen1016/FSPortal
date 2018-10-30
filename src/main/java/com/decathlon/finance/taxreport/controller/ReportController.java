package com.decathlon.finance.taxreport.controller;


import com.decathlon.finance.taxreport.service.ReportService;
import com.decathlon.finance.taxreport.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequestMapping(value = "/report")
@Api(value = "ReportController", description = "report operation")
public class ReportController {

    @Autowired
    private ReportService reportService;


    @RequestMapping(value = "/pdf/subledger", method = RequestMethod.GET)
    @ApiOperation(value = "exportToPDF", notes = "generateReport", httpMethod = "GET")
    public HttpEntity<byte[]> downlaodSubledgerPdf(@RequestParam String glFileName,
                                                   @RequestParam String balanceFileName,
                                                   @RequestParam String companyType) throws JRException, IOException {
        //File ff = new File(Constants.SRC_FOLDER_GL +"Test.xlsx");
        File glFile = new File(Constants.SRC_FOLDER_GL+"\\"+glFileName);
        File blFile = new File(Constants.SRC_FOLDER_BL+"\\"+balanceFileName);
        FileInputStream blIs = new FileInputStream(blFile);
        FileInputStream glIs = new FileInputStream(glFile);
        return reportService.generatePdf(glFile.getName(), blIs , glIs, Constants.REPORT_TYPE_SUBLEDGER, companyType, null);
    }

    @RequestMapping(value = "/pdf/voucher", method = RequestMethod.GET)
    @ApiOperation(value = "exportToPDF", notes = "generateReport", httpMethod = "GET")
    public Object downlaodVoucherPdf(@RequestParam String fileName,
                                     @RequestParam String companyType,
                                     @RequestParam String builderName) throws JRException, IOException {

        //File ff = new File(Constants.SRC_FOLDER_GL +"Test.xlsx");
        File ff = new File(Constants.SRC_FOLDER_GL+"\\"+fileName);
        FileInputStream is = new FileInputStream(ff);
        return reportService.generatePdf(ff.getName(), null , is, Constants.REPORT_TYPE_VOUCHER, companyType,builderName);
    }
}
