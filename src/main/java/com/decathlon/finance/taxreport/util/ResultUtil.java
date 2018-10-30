package com.decathlon.finance.taxreport.util;

import com.decathlon.finance.taxreport.common.Result;
import com.decathlon.finance.taxreport.common.ResultEnum;

public class ResultUtil {
    public static Result success(Object object)
    {
        Result res = new Result();
        res.setCode(ResultEnum.SUCCESS.getCode());
        res.setData(ResultEnum.SUCCESS.getMsg());
        res.setMsg(object.toString());
        return  res;
    }

    public static Result success()
    {
        return success(null);
    }

    public static Result error(Integer code, String msg)
    {
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }
}
