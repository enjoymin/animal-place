package com.example.demo.model.disease;

public class DiseaseDTO {
    private String name;        
    private String symptoms;  
    private String imageUrl;   
    private String solution;    

 
    public DiseaseDTO(String name, String symptoms, String imageUrl) {
        this.name = name;
        this.symptoms = symptoms;
        this.imageUrl = imageUrl;
    }

 
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