package com.example.common;

import com.example.common.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Result {
    private String code;
    private String message;
    private Object data;

    private Result(Object data) {
        this.data = data;
    }

    public Result() {
    }

    public static Result success() {
        Result tResult = new Result();
        tResult.setCode(ResultCodeEnum.SUCCESS.code);
        tResult.setmessage(ResultCodeEnum.SUCCESS.message);
        return tResult;
    }

    public static Result success(Object data) {
        Result tResult = new Result (data);
        tResult.setCode(ResultCodeEnum.SUCCESS.code);
        tResult.setmessage(ResultCodeEnum.SUCCESS.message);
        return tResult;
    }

    public static Result error() {
        Result tResult = new Result();
        tResult.setCode(ResultCodeEnum.SYSTEM_ERROR.code);
        tResult.setmessage(ResultCodeEnum.SYSTEM_ERROR.message);
        return tResult;
    }

    public static Result error(String code, String message) {
        Result tResult = new Result();
        tResult.setCode(code);
        tResult.setmessage(message);
        return tResult;
    }

    public static Result error(ResultCodeEnum resultCodeEnum) {
        Result tResult = new Result();
        tResult.setCode(resultCodeEnum.code);
        tResult.setmessage(resultCodeEnum.message);
        return tResult;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
