package com.serveme_employee.model;

public class MessageModel
{

    private String randomKey;
    private String idMessage;
    private String message;
    private String image;
    private String video;

    public MessageModel()
    {
    }

    public MessageModel(String randomKey, String idMessage, String message)
    {
        this.randomKey = randomKey;
        this.idMessage = idMessage;
        this.message = message;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public String getIdMessage() {
        return idMessage;
    }

    public String getMessage() {
        return message;
    }

    //    public MessageModel(String randomKeyMessage, String message)
//    {
//        this.message = message;
//    }
//
//    public MessageModel(String image)
//    {
//        this.image = image;
//    }
//
//
//    public String getRandomKey() {
//        return randomKey;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public String getVideo() {
//        return video;
//    }
}
