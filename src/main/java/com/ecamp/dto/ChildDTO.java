package com.ecamp.dto;

public class ChildDTO {
    public Long id;
    public String name;
    public String school;
    public Long parentId;
    public String dateOfBirth;
    public String medicalInfo;
    public String email;

    public ChildDTO(Long id, String name, String school, Long parentId, 
                    String dateOfBirth, String medicalInfo, String email) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.parentId = parentId;
        this.dateOfBirth = dateOfBirth;
        this.medicalInfo = medicalInfo;
        this.email = email;
    }
}
