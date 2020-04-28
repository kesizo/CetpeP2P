package com.kesizo.cetpe.front.controller.dtos;

public class RubricType {

    private long id;
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RubricType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }


}