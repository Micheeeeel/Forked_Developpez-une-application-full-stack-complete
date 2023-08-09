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
    private boolean isFollowed;

    public SubjectDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.isFollowed = false;
    }
}
