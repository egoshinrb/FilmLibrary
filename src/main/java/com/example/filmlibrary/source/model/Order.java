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
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
          foreignKey = @ForeignKey(name = "FK_ORDER_USER_ID"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false,
          foreignKey = @ForeignKey(name = "FK_ORDER_FILM_ID"))
    private Film film;

    @Column(name = "rent_date", nullable = false)
    private LocalDate rentDate;

    @Column(name = "rent_period", nullable = false)
    private Byte rentPeriod;

    @Column(name = "is_purchase")
    private Boolean isPurchase;
}
