package cn.wxrwcz.beans;

public class WxrResult<T> {

    public final static Integer CODE_SUCCESS = 0;
    public final static Integer CODE_FAIL = 1;
    public final static String MSG_SUCCESS = "操作成功";
    public final static String MSG_FAIL = "操作失败";

    private Integer code;

    private String msg;

    private T data;

    public static <T> WxrResult<T> build(Integer status, String msg, T data) {
        return new WxrResult<T>(status, msg, data);
    }

    public static <T> WxrResult<T> ok(T data) {
        return new WxrResult<T>(data);
    }

    public static <T> WxrResult<T> ok() {
        return new WxrResult<T>(CODE_SUCCESS, MSG_SUCCESS, null);
    }

    public static <T> WxrResult<T> fail()
    {
        return new WxrResult<T>(CODE_FAIL, MSG_FAIL, null);
    }
    public static <T> WxrResult<T> fail(T data)
    {
        return new WxrResult<T>(CODE_FAIL, MSG_FAIL, data);
    }
    public static <T> WxrResult<T> fail(String msg)
    {
        return new WxrResult<T>(CODE_FAIL, msg, null);
    }


    public WxrResult() {

    }

    public static <T> WxrResult<T> build(Integer status, String msg) {
        return new WxrResult<T>(status, msg, null);
    }

    public static <T> WxrResult<T> getResult(T t) {
        WxrResult<T> wxrResult = new WxrResult<>(t);
        return wxrResult;
    }

    public WxrResult(Integer status, String msg, T data) {
        this.code = status;
        this.msg = msg;
        this.data = data;
    }

    public WxrResult(T data) {
        this.code = 0;
        this.msg = MSG_SUCCESS;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
