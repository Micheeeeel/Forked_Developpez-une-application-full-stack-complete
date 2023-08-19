package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {

    private Long id;
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(max = 5000)
    private String description;
    private boolean isFollowed;

    public SubjectDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isFollowed = false;
    }
}
