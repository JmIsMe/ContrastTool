package test;

import java.math.BigDecimal;

import contrast.ContrastAnnotation;
import contrast.ContrastRecursive;

/**
 * @Description:
 * @Date: Created in 16:36 2018/8/21
 * @Auth: LinJiangMing
 */
public class Family {
    @ContrastAnnotation(name = "家族名称", frontMsg = "从", middleMsg = "修改为", nullMsg = "无")
    private String familyName;
    @ContrastAnnotation(name = "家族人数", frontMsg = "从", middleMsg = "变成了", nullMsg = "无")
    private Integer numberOfPeople;
    @ContrastAnnotation(name = "家族财产", frontMsg = "从", middleMsg = "变成了", nullMsg = "无")
    private BigDecimal asset;
    @ContrastRecursive
    private Father father;

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public BigDecimal getAsset() {
        return asset;
    }

    public void setAsset(BigDecimal asset) {
        this.asset = asset;
    }

    public Father getFather() {
        return father;
    }

    public void setFather(Father father) {
        this.father = father;
    }


}
