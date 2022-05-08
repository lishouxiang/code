package com.concurrent;


import lombok.Getter;

public enum CountryEnum {

    ONE(1,"齐"), TWO(2,"楚"), THREE(3,"燕"), FOUR(4,"赵"), FIVE(5,"魏"), SIX(6,"韩");

    @Getter  private Integer resCode;
    @Getter private String resMessage;

    CountryEnum(Integer resCode, String resMessage) {
        this.resCode = resCode;
        this.resMessage = resMessage;
    }

    public static CountryEnum getCountryElement(int index){

        CountryEnum [] countryEnumArr =CountryEnum.values();
        for(CountryEnum element: countryEnumArr){
                if(index == element.getResCode()){
                    return element;
                }
        }
        return null;
    }
}
