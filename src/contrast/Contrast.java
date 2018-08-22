package contrast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Date: Created in 16:16 2018/8/21
 * @Auth: LinJiangMing
 */

public class Contrast<T> {

    //比较的class
    private Class<T> clazz;
    //存放相关的数据
    private List<ContrastMsg> msgList;

    public Contrast(Class clazz) {
        this.clazz = clazz;
        init();
    }

    //初始化
    private void init() {
        msgList = new ArrayList<>();
        Field[] field = clazz.getDeclaredFields();

        for (Field f : field) {
            f.setAccessible(true);
            ContrastRecursive cv = f.getAnnotation(ContrastRecursive.class);
            Contrast contrast = null;
            ContrastMsg contrastMsg = new ContrastMsg();
            if (cv != null) {
                contrast = new Contrast(f.getType());
                contrastMsg.setMark(false);
            }
            ContrastAnnotation ca = f.getAnnotation(ContrastAnnotation.class);
            if (ca != null) {
                String userName = ca.name();
                if (userName.isEmpty()) {
                    userName = f.getName();
                }
                contrastMsg.setUserName(userName);
                contrastMsg.setAllMsg(ca);
            }
            contrastMsg.setField(f);
            contrastMsg.setContrast(contrast);
            msgList.add(contrastMsg);
        }

    }

    /**
     * 比较
     *
     * @param before
     *         改变前的对象
     * @param atfer
     *         改变后的对象
     */
    public List<String> compare(Object before, Object atfer) throws IllegalAccessException {
        List<String> ret = new ArrayList<>();
        for (ContrastMsg contrastMsg : msgList) {
            Field field = contrastMsg.getField();
            Object beforeValue = getFieldObject(field, before);
            Object afterValue = getFieldObject(field, atfer);
            if (!contrastMsg.getMark()) {
                ret.addAll(contrastMsg.getContrast().compare(beforeValue, afterValue));
            }
            if (isEqual(beforeValue,afterValue) && contrastMsg.getMark()) {
                contrastMsg.setBeforeValue(beforeValue);
                contrastMsg.setAfterValue(afterValue);
                ret.add(contrastMsg.toString());
            }
        }
        return ret;
    }

    /**
     * 判断前后比较的对象是否需要输出报文
     *
     * @return
     */
    private Boolean isEqual(Object before, Object after) {
        Boolean ret = true;

        if (before != null && after != null) {
            ret = !before.equals(after);
        } else if (before == null && after == null ) {
            ret = false;
        }
        return ret;
    }

    private Object getFieldObject(Field field, Object obj) throws IllegalAccessException {
        Object ret = null;
        if (field != null && obj != null) {
            ret = field.get(obj);
        }
        return ret;
    }



}
