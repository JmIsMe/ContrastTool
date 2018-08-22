事实上，是产品要求公司业务每次操作都要有修改明细，即改了什么东西，不可能一个一个去比较，所以做了这个比较两个对象不同输出不同日志的小工具。
原理就是注解加反射而已。

### 1、在bean中加注解，比如在测试用例中的

```
 @ContrastAnnotation(name = "家族名称", frontMsg = "从", middleMsg = "修改为", nullMsg = "无")
 private String familyName;
 @ContrastAnnotation(name = "家族人数", frontMsg = "从", middleMsg = "变成了", nullMsg = "无")
 private Integer numberOfPeople;
 @ContrastAnnotation(name = "家族财产", frontMsg = "从", middleMsg = "变成了", nullMsg = "无")
 private BigDecimal asset;
```

### 2、创建比较器进行比较

比如：
```
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
```
输出为：
```
家族名称 从 柯里昂(旧)家族 修改为 柯里昂(新)家族 
家族人数 从 200 变成了 100 
家族财产 从 60000 变成了 45000.98 
教父名称 由 维托·唐·柯里昂 传给 迈克·柯里昂 因为大儿子山提诺·桑尼·柯里昂已经没了
教父的年龄 由 58 换成了 未知 岁数
``` 

