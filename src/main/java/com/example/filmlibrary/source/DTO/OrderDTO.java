package com.example.filmlibrary.source.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO extends GenericDTO{
    private Long filmId;
    private Long userId;
    private LocalDateTime rentDate;
    private Integer rentPeriod;
    private Boolean purchase;
}
