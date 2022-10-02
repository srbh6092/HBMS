package com.srbh.hbms.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddRoomRequest {

    @NotBlank
    private String roomNo;

    @NotBlank
    private String roomType;

    @NotNull
    private double ratePerDay;

    private List<String> photos;

}