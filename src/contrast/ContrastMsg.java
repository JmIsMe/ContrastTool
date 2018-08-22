package contrast;

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
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(userName);
        sb.append(" ");
        sb.append(frontMsg);
        sb.append(" ");
        isNull(sb,beforeValue);
        sb.append(" ");
        sb.append(middleMsg);
        sb.append(" ");
        isNull(sb,afterValue);
        sb.append(" ");
        sb.append(backMsg);
        return sb.toString();
    }

    private void isNull(StringBuilder sb, Object obj){
        if (obj != null){
            sb.append(obj);
        }else{
            sb.append(nullMsg);
        }
    }
}
