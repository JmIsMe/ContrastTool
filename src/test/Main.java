package test;

import java.math.BigDecimal;
import java.util.List;

import contrast.Contrast;

/**
 * @Description:
 * @Date: Created in 17:27 2018/8/21
 * @Auth: LinJiangMing
 */
public class Main {

    public static void main(String[] arg) throws NoSuchFieldException, IllegalAccessException {
        //创建比较器
        Contrast c = new Contrast(Family.class);

        Family family1 = new Family();
        family1.setFamilyName("柯里昂(旧)家族");
        family1.setNumberOfPeople(200);
        family1.setAsset(new BigDecimal("60000"));
        Father oldFather = new Father();
        oldFather.setName("维托·唐·柯里昂");
        oldFather.setAge(58);
        family1.setFather(oldFather);

        Family family2 = new Family();
        family2.setFamilyName("柯里昂(新)家族");
        family2.setNumberOfPeople(100);
        family2.setAsset(new BigDecimal("45000.98"));
        Father newFather = new Father();
        newFather.setName("迈克·柯里昂");
        family2.setFather(newFather);

        //两个对象进行比较
        List<String> li = c.compare(family1, family2);
        li.forEach(l -> System.out.println(l));

    }

}
