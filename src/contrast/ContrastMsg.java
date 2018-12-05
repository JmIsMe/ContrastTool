package com.fanjia.mice.util.contrast;

import com.fanjia.mice.util.contrast.annotation.ContrastAnnotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;


/**
 * @Description:
 * @Date: Created in 17:55 2018/8/21
 * @Auth: LinJiangMing
 */
public class ContrastMsg {

    //属性
    private Field field;
    //自定义输出名称
    private String userName;
    //报文
    private String frontMsg;
    private String middleMsg;
    private String backMsg;
    private String addMsg;
    private String delMsg;
    //代替null名称
    private String nullMsg;
    //之前的值
    private Object beforeValue;
    //现在的值
    private Object afterValue;
    //递归的比较器
    private Contrast contrast;
    //是否需要比较标志
    private Boolean mark = true;

    public ContrastMsg() {

    }

    public String getNullMsg() {
        return nullMsg;
    }

    public void setNullMsg(String nullMsg) {
        this.nullMsg = nullMsg;
    }

    public Boolean getMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }

    public Contrast getContrast() {
        return contrast;
    }

    public void setContrast(Contrast contrast) {
        this.contrast = contrast;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBeforeValue(Object beforeValue) {
        this.beforeValue = beforeValue;
    }

    public void setAfterValue(Object afterValue) {
        this.afterValue = afterValue;
    }

    public void setFrontMsg(String frontMsg) {
        this.frontMsg = frontMsg;
    }

    public void setMiddleMsg(String middleMsg) {
        this.middleMsg = middleMsg;
    }

    public void setBackMsg(String backMsg) {
        this.backMsg = backMsg;
    }

    public void setAllMsg(ContrastAnnotation ca) {
        frontMsg = ca.frontMsg();
        middleMsg = ca.middleMsg();
        backMsg = ca.backMsg();
        nullMsg = ca.nullMsg();
        addMsg = ca.addMsg();
        delMsg = ca.delMsg();
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        switch (getState()){
            case 1:  sb.append(addMsg).append(userName).append(" ").append(afterValue).append(" ").append(backMsg);
                break;
            case 2: sb.append(delMsg).append(userName).append(" ").append(beforeValue).append(" ").append(backMsg);
                break;
            case 3:
                sb.append(userName)
                        .append(" ")
                        .append(frontMsg)
                        .append(" ")
                        .append(beforeValue)
                        .append(" ")
                        .append(middleMsg)
                        .append(" ")
                        .append(afterValue)
                        .append(" ")
                        .append(backMsg);
                break;
            default:
                break;

        }

        return sb.toString();
    }

    private void isNull(StringBuilder sb, Object obj) {
        if (obj != null) {
            sb.append(obj);
        } else {
            sb.append(nullMsg);
        }
    }

    private boolean objStrIsEmptys(Object val){
        if (val == null || val.toString().equals("")){
            return true;
        }
        return false;
    }
    private boolean objStrNotEmptys(Object val){
        if (val != null && !val.toString().equals("")){
            return true;
        }
        return false;
    }

    private int getState(){
        int flag = 0;
        if (objStrIsEmptys(beforeValue) && objStrNotEmptys(afterValue)) {
            if (objStrNotEmptys(nullMsg)){
                beforeValue = nullMsg;
                flag = 3;
            }else {
                flag = 1;
            }

        } else if (objStrIsEmptys(afterValue) && objStrNotEmptys(beforeValue))  {
            if (objStrNotEmptys(nullMsg)){
                afterValue = nullMsg;
                flag = 3;
            }else {
                flag = 2;
            }
        } else if (objStrNotEmptys(beforeValue) && objStrNotEmptys(afterValue)){
            flag = 3;
        }else{
            flag = 4;
        }
        return flag;
    }
}
