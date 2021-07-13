package com.example.demo.optionaltest;

import java.util.List;

/**
 * @author zhaoyu
 * @date 2020/1/2
 */
public class EarthModel {

    private List<PersonModel> personModels;

    private TeaModel tea;


    public List<PersonModel> getPersonModels() {
        return personModels;
    }

    public void setPersonModels(List<PersonModel> personModels) {
        this.personModels = personModels;
    }

    public TeaModel getTea() {
        return tea;
    }

    public void setTea(TeaModel tea) {
        this.tea = tea;
    }
}
