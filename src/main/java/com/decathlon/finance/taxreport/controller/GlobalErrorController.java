package com.decathlon.finance.taxreport.controller;

import com.decathlon.finance.taxreport.common.Result;
import com.decathlon.finance.taxreport.common.ResultEnum;
import com.decathlon.finance.taxreport.util.ResultUtil;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/error")
public class GlobalErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    @ResponseBody
    public Result doHandleError()
    {
        return ResultUtil.error(ResultEnum.UNKNOW_ERROR.getCode(),
                ResultEnum.UNKNOW_ERROR.getMsg());
    }
}
