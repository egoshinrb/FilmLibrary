package com.example.filmlibrary.source.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GenericDTO {
    private Long id;
    private LocalDateTime createdWhen;
    private String createdBy;
    private LocalDateTime deletedWhen;
    private String deletedBy;
    private boolean isDeleted;
}
