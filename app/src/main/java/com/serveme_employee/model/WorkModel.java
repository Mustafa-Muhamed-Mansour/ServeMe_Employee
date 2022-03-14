package com.serveme_employee.model;

public class WorkModel
{

    private String randomKey;
    private String id;
    private String image;


    public WorkModel()
    {
    }

    public WorkModel(String randomKey, String id, String image)
    {
        this.randomKey = randomKey;
        this.id = id;
        this.image = image;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }
}
