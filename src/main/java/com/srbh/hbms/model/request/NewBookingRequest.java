package com.srbh.hbms.model.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NewBookingRequest {

    @NotNull
    private Date bookedFrom;

    @NotNull
    private Date bookedTo;

    @NotNull
    private int noOfAdults;

    @NotNull
    private int noOfChildren;

    @NotNull
    private List<Integer> roomIDs;

}