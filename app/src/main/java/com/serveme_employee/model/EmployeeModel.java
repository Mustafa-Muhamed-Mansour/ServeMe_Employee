package com.serveme_employee.model;

public class EmployeeModel
{
    private String randomKey;
    private String id;
//    private String jobID;
    private String image;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String job;
    private String gender;

    public EmployeeModel()
    {
    }

//    public EmployeeModel(String randomKey, String id, String jobID, String image, String firstName, String lastName, String phoneNumber, String email, String job, String gender)
//    {
//        this.randomKey = randomKey;
//        this.id = id;
//        this.jobID = jobID;
//        this.image = image;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.job = job;
//        this.gender = gender;
//    }


    public EmployeeModel(String randomKey, String id, String image, String firstName, String lastName, String phoneNumber, String email, String job, String gender)
    {
        this.randomKey = randomKey;
        this.id = id;
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.job = job;
        this.gender = gender;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public String getId() {
        return id;
    }

//    public String getJobID() {
//        return jobID;
//    }

    public String getImage() {
        return image;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getJob() {
        return job;
    }

    public String getGender() {
        return gender;
    }
}
