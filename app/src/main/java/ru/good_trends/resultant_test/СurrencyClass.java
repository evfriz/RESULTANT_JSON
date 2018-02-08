package ru.good_trends.resultant_test;

/**
 * Created by Evgeniy on 08.02.2018.
 */

public class СurrencyClass {
    public String name;
    public Integer volume;
    public Double amount;


    public void SetName(String name){
        this.name = name;
    }
    public String GetName(){
        return this.name;
    }

    public void SetVolume(Integer volume){
        this.volume = volume;
    }
    public Integer GetVolume(){
        return this.volume;
    }

    public void SetAmount(Double amount){
        this.amount = amount;
    }
    public Double GetAmount(){
        return this.amount;
    }


}