package com.decathlon.finance.taxreport.controller;

import com.decathlon.finance.taxreport.model.FiscalInfo;
import com.decathlon.finance.taxreport.service.FiscalInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/fiscals")
public class FiscalInfoController {

    @Autowired
    private FiscalInfoService fiscalInfoService;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "add fiscal info", notes = "add fiscal info", httpMethod = "POST")
    public int addUser(@RequestBody FiscalInfo fi) {
        return fiscalInfoService.addFiscalInfo(fi);
    }

    @ResponseBody
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "get all fiscal info", notes = "get all fiscal info", httpMethod = "GET")
    public List<FiscalInfo> getAllFiscalInfo() {
        return fiscalInfoService.getAllFiscalInfo();
    }

}
