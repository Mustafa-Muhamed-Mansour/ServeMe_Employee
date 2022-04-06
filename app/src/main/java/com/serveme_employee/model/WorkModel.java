package com.serveme_employee.model;

public class WorkModel
{

    private String randomKey;
    private String id;
    private String image;
    private String title;


    public WorkModel()
    {
    }

    public WorkModel(String randomKey, String id, String image, String title)
    {
        this.randomKey = randomKey;
        this.id = id;
        this.image = image;
        this.title = title;
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

    public String getTitle() {
        return title;
    }
}
