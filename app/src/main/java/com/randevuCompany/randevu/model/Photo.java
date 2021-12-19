package com.randevuCompany.randevu.model;

public class Photo {
    private String photo;

    public Photo() {
    }

    public Photo(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photo='" + photo + '\'' +
                '}';
    }
}
