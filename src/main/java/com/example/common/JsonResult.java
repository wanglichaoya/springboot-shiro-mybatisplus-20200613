package com.example.common;

import cn.hutool.json.JSON;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2020/6/12
 * Time: 下午 03:49
 * Description:
 */
@Data
public class JsonResult implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public static JsonResult succ(Object data) {
        JsonResult jsonResult = getJsonResult();
        jsonResult.setCode(200);
        jsonResult.setData(data);
        jsonResult.setMsg("操作成功！");
        return jsonResult;

    }

    private static JsonResult getJsonResult() {
        return new JsonResult();
    }

    public static JsonResult succ(String msg,Object data) {
        JsonResult jsonResult = getJsonResult();
        jsonResult.setCode(200);
        jsonResult.setData(data);
        jsonResult.setMsg(msg);
        return jsonResult;

    }

    public static JsonResult fail(String msg){
        JsonResult jsonResult = getJsonResult();
        jsonResult.setMsg(msg);
        jsonResult.setData(null);
        jsonResult.setCode(-1);
        return getJsonResult(jsonResult);
    }

    private static JsonResult getJsonResult(JsonResult jsonResult) {
        return jsonResult;
    }

    public static JsonResult fail(String msg,Data data,int code){
        JsonResult jsonResult = getJsonResult();
        jsonResult.setMsg(msg);
        jsonResult.setData(null);
        jsonResult.setCode(code);
        return jsonResult;
    }


}
