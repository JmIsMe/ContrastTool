package com.fanjia.mice.util.contrast;

import com.fanjia.mice.domain.persistentObject.ListId;
import com.fanjia.mice.util.contrast.annotation.ConIgnore;
import com.fanjia.mice.util.contrast.annotation.ContrastAnnotation;
import com.fanjia.mice.util.contrast.annotation.ContrastRecursive;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Description: 比较差异的工具类
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
            Annotation[] annotations = f.getAnnotations();
            for (Annotation a : annotations) {
                Contrast contrast = null;
                ContrastMsg contrastMsg = new ContrastMsg();
                if (a instanceof ContrastRecursive) {
                    Class<?> objClass = f.getType();
                    Type genericType = f.getGenericType();
                    if (genericType instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) genericType;
                        //得到泛型里的class类型对象
                        objClass = (Class<?>) pt.getActualTypeArguments()[0];
                    }
                    contrast = new Contrast(objClass);
                    contrastMsg.setMark(false);
                    contrastMsg.setField(f);
                    contrastMsg.setContrast(contrast);
                    msgList.add(contrastMsg);
                } else if (a instanceof ContrastAnnotation) {
                    ContrastAnnotation b = (ContrastAnnotation) a;
                    String userName = b.name();
                    if (userName.isEmpty()) {
                        userName = f.getName();
                    }
                    contrastMsg.setUserName(userName);
                    contrastMsg.setAllMsg(b);
                    contrastMsg.setField(f);
                    contrastMsg.setContrast(contrast);
                    msgList.add(contrastMsg);
                }
            }

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
                ContrastRecursive cr = field.getAnnotation(ContrastRecursive.class);
                if (beforeValue instanceof List && afterValue instanceof List) {
                    List afterList = (List) afterValue;
                    List beforeList = (List) beforeValue;

                    Map<Integer,Object> map1 = new HashMap<>();
                    Map<Integer,Object> map2 = new HashMap<>();

                    beforeList.forEach( b -> map1.put(((ListId)b).listIdGetter(),b));
                    afterList.forEach( a -> map2.put(((ListId)a).listIdGetter(),a));

                    Set setBefore = map1.keySet();
                    Set setAfter = map2.keySet();

                    Set<Integer> compareSet = andCollection(setBefore, setAfter);
                    if (!compareSet.isEmpty()){
                        for (Integer index : compareSet){
                            List li = contrastMsg.getContrast().compare(map1.get(index), map2.get(index));
                            if (li.size() != 0) {
                                ret.add(cr.changeMsg() + cr.name()  + " => ");
                                ret.addAll(li);
                            }
                        }
                    }
                    Set<Integer> removeSet = diffCollection(setBefore,  compareSet);
                    if (!removeSet.isEmpty()){
                        for (Integer index : removeSet){
                            ret.add(cr.delMsg()  + cr.name()  + " => ");
                            ret.addAll(contrastMsg.getContrast().compare(map1.get(index), null));
                        }
                    }
                    Set<Integer> addSet = diffCollection(setAfter,  compareSet);
                    if (!addSet.isEmpty()){
                        for (Integer index : addSet){
                            ret.add(cr.addMsg()  + cr.name()  + " => ");
                            ret.addAll(contrastMsg.getContrast().compare( null,map2.get(index)));
                        }
                    }

                } else {
                    List li = contrastMsg.getContrast().compare(beforeValue, afterValue);
                    if (li.size() != 0) {
                        ret.add(cr.changeMsg() + cr.name() + " => ");
                        ret.addAll(li);
                    }
                }
            }
            if (isEqual(beforeValue, afterValue) && contrastMsg.getMark()) {
                contrastMsg.setBeforeValue(beforeValue);
                contrastMsg.setAfterValue(afterValue);
                String str = contrastMsg.toString();
                if (!StringUtils.isEmpty(str)) {
                    ret.add(str);
                }
            }
        }
        return ret;
    }

    /**
     * 判断前后比较的对象是否需要输出报文
     */
    private Boolean isEqual(Object before, Object after) {
        Boolean ret = true;

        if (before != null && after != null) {
            if (before instanceof BigDecimal && after instanceof BigDecimal) {
                ret = ((BigDecimal) before).compareTo((BigDecimal) after) == 0 ? false : true;
                after = ((BigDecimal) after).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else if (before instanceof Date && after instanceof Date) {
                ret = !before.toString().equals(after.toString());
            } else {
                ret = !before.equals(after);
            }
        } else if (before == null && after == null) {
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



    private Set<Integer> andCollection(Set<Integer> setBefore, Set<Integer> setAfter){
        Set<Integer> compareSet = new HashSet<>();
        compareSet.addAll(setBefore);
        compareSet.retainAll(setAfter);
        return compareSet;
    }

    private Set<Integer> diffCollection(Set<Integer> setBefore, Set<Integer> setAfter){
        Set<Integer> result = new HashSet<>();
        result.addAll(setBefore);
        result.removeAll(setAfter);
        return result;
    }

    /**
     * 计算差异的类，如果是数字就是差值，如果是对象则是最新对象。
     * @param before 之前的类
     * @param after 之后的类
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object compareObject(Object before, Object after)  {

        if(before == null || after == null){
            return after;
        }
        Field[] fields = after.getClass().getDeclaredFields();
        Object ret = null;
        try {
            ret = after.getClass().newInstance();
            for(Field f : fields){
                f.setAccessible(true);
                ConIgnore sp =  f.getAnnotation(ConIgnore.class);
                if (sp != null) {
                    continue;
                }
                Object beforeValue = f.get(before);
                Object afterValue =  f.get(after);
                Object newValue;
                if (afterValue == null || beforeValue == null) {
                    newValue = afterValue;
                } else if (afterValue instanceof BigDecimal) {
                    newValue = ((BigDecimal) afterValue).subtract((BigDecimal) beforeValue);
                } else if (afterValue instanceof Integer) {
                    newValue = ((Integer) afterValue - (Integer) beforeValue);
                } else if (afterValue instanceof Long) {
                    newValue = (Long) afterValue - (Long) beforeValue;
                } else if (afterValue instanceof Short) {
                    newValue = (Short) afterValue - (Short) beforeValue;
                } else if (afterValue instanceof Double) {
                    newValue = (Double) afterValue - (Double) beforeValue;
                } else if (afterValue instanceof Byte) {
                    newValue = (Byte) afterValue - (Byte) beforeValue;
                }  else {
                    newValue = afterValue;
                }
                f.set(ret,newValue);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
