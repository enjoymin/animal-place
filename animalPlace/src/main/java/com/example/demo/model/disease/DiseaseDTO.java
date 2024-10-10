package com.example.demo.model.disease;

public class DiseaseDTO {
    private String name;        // 질병 이름
    private String symptoms;    // 증상
    private String imageUrl;    // 이미지 URL
    private String solution;    // 해결책

    // 생성자
    public DiseaseDTO(String name, String symptoms, String imageUrl) {
        this.name = name;
        this.symptoms = symptoms;
        this.imageUrl = imageUrl;
    }

    // Getter 및 Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}