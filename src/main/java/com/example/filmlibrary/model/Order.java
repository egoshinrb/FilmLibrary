package com.example.filmlibrary.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "order_sequence", allocationSize = 1)
public class Order extends GenericModel {

    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "FK_ORDER_USER"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "film_id", 
            foreignKey = @ForeignKey(name = "FK_ORDER_FILM"))
    private Film film;

    @Column(name = "rent_date", nullable = false)
    private LocalDateTime rentDate;

    @Column(name = "rent_period", nullable = false)
    private Integer rentPeriod;

    @Column(name = "returned", nullable = false)
    private Boolean returned;

    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;


}

