package com.example.filmlibrary.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleDTO extends GenericDTO {
    private Long id;
    private String title;
    private String description;
}