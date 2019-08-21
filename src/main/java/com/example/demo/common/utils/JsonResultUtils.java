package com.example.demo.common.utils;

import com.example.demo.common.entity.JsonResultEntity;

public class JsonResultUtils {

    public static JsonResultEntity success(Object object) {
        JsonResultEntity jsonResult = new JsonResultEntity();
        jsonResult.setData(object);
        jsonResult.setCode(200);
        jsonResult.setMessage("success");
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
