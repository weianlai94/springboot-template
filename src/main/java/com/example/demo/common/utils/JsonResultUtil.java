package com.example.demo.common.utils;

import com.example.demo.common.enums.MessageEnum;


public class JsonResultUtil {

    public static JsonResultEntity success(Object object) {
        JsonResultEntity jsonResult = new JsonResultEntity();
        jsonResult.setData(object);
        jsonResult.setCode(MessageEnum.STATUS_OK.getCode());
        jsonResult.setMessage(MessageEnum.STATUS_OK.getMessage());
        return jsonResult;
    }

    public static JsonResultEntity success(Object object, Integer code, String message) {
        JsonResultEntity jsonResult = new JsonResultEntity();
        jsonResult.setData(object);
        jsonResult.setCode(code);
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public static JsonResultEntity success() {
        return success(null);
    }

    public static JsonResultEntity error(Integer code, String message) {
        JsonResultEntity jsonResult = new JsonResultEntity();
        jsonResult.setCode(code);
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public static JsonResultEntity success(Integer code, String message) {
        JsonResultEntity jsonResult = new JsonResultEntity();
        jsonResult.setCode(code);
        jsonResult.setMessage(message);
        return jsonResult;
    }

}
