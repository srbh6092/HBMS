package com.srbh.hbms.model.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NewPaymentRequest {

    @NotNull
    private double amount;

}