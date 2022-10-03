package com.srbh.hbms.model.entity;

import com.srbh.hbms.model.enums.PaymentStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @NotNull
    @OneToOne
    private Transaction transaction;

}