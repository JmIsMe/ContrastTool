package com.fanjia.mice.util.contrast;

import java.util.List;

/**
 * @Description:
 * @Date: Created in 14:05 2018/8/23
 * @Auth: LinJiangMing
 */
public class ContrastResult {
    //比较的差异的结果，如是数值则是差值，不是数值则是最新值
    private Object result;
    //日志
    private List<String> message;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

}
