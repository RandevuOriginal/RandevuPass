package com.randevuCompany.randevu.model;

import java.util.List;

public class User {
    private String id;
    private String age;
    private String description;
    private String email;
    private String gender;
    private String name;
    private String orientation;
    private List<Photo> photos;

    public User() {
    }

    public User(String id,String age, String description, String email, String gender, String name, String orientation, List<Photo> photos) {
        this.age = age;
        this.description = description;
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.orientation = orientation;
        this.photos = photos;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "age='" + age + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", name='" + name + '\'' +
                ", orientation='" + orientation + '\'' +
                ", photos=" + photos +
                '}';
    }
}
