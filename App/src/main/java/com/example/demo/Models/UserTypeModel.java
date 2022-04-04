package com.example.demo.Models;

public class UserTypeModel {
    private Integer id;
    private String name;
    private Integer rank;

    public UserTypeModel() {
    }

    public UserTypeModel(int id, String name, int rank) {
        this.id = id;
        this.name = name;
        this.rank = rank;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
