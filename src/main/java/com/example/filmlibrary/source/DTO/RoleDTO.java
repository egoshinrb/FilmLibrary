package com.example.filmlibrary.source.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoleDTO extends GenericDTO {
    private Long id;
    private String title;
    private String description;
}