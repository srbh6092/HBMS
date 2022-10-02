package com.srbh.hbms.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @NotNull
    private Date bookedFrom;

    @NotNull
    private Date bookedTo;

    @NotNull
    private int noOfAdults;

    @NotNull
    private int noOfChildren;

    @OneToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Payment> payment;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToOne
    private Hotel hotel;

}