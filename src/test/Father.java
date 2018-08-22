package test;


import contrast.ContrastAnnotation;

/**
 * @Description:
 * @Date: Created in 13:20 2018/8/22
 * @Auth: LinJiangMing
 */
public class Father {
    @ContrastAnnotation(name = "教父名称",frontMsg ="由",middleMsg = "传给",backMsg = "因为大儿子山提诺·桑尼·柯里昂已经没了")
    private String name;
    @ContrastAnnotation(name = "教父的年龄",frontMsg = "由",middleMsg = "换成了",nullMsg = "未知",backMsg = "岁数")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
