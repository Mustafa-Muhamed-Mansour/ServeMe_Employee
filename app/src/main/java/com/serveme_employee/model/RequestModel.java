package com.serveme_employee.model;

public class RequestModel
{
    private String User_Image;
    private String User_Name;
    private String User_Phone;
    private String User_Email;
    private String User_Gender;
    private String User_ID;
    private String User_Random_Key;

    public RequestModel()
    {
    }

    public RequestModel(String user_Image, String user_Name, String user_Phone, String user_Email, String user_Gender, String user_ID, String user_Random_Key)
    {
        User_Image = user_Image;
        User_Name = user_Name;
        User_Phone = user_Phone;
        User_Email = user_Email;
        User_Gender = user_Gender;
        User_ID = user_ID;
        User_Random_Key = user_Random_Key;
    }

    public String getUser_Image() {
        return User_Image;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public String getUser_Phone() {
        return User_Phone;
    }

    public String getUser_Email() {
        return User_Email;
    }

    public String getUser_Gender() {
        return User_Gender;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public String getUser_Random_Key() {
        return User_Random_Key;
    }
}
