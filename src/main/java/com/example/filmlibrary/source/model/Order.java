package com.example.filmlibrary.source.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(name = "default_generator", sequenceName = "order_sequence", allocationSize = 1)
public class Order extends GenericModel {
    @Column(name = "user_id", nullable = false) // TODO добавить констрейнты
    private Long userId;

    @Column(name = "film_id", nullable = false)
    private Long filmId;

    @Column(name = "rent_date", nullable = false)
    private LocalDate rentDate;

    @Column(name = "rent_period", nullable = false)
    private Byte rentPeriod;

    @Column(name = "is_purchase")
    private Boolean isPurchase;
}
