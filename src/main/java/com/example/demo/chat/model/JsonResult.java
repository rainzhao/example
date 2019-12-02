package com.example.demo.chat.model;

/**
 * @author zhaoyu
 * @date 2019-05-19
 */
public class JsonResult {
    private Integer code;

    private Object data;

    private String msg;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static JsonResult ok() {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(200);
        jsonResult.setMsg("成功");
        return jsonResult;
    }

    public static JsonResult errorMsg(String msg) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(200);
        jsonResult.setMsg(msg);
        return jsonResult;
    }

}
