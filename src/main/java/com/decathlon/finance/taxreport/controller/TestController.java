package com.decathlon.finance.taxreport.controller;

import com.decathlon.finance.taxreport.common.Result;
import com.decathlon.finance.taxreport.common.ResultEnum;
import com.decathlon.finance.taxreport.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
@Api(value = "TestController", description = "test operation")
public class TestController {

    @ResponseBody
    @RequestMapping(value = "/t", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "t", notes = "t", httpMethod = "GET")
    public Result test() throws Exception
    {
        throw new Exception();
    }
}
