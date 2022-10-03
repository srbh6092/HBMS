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
@Table( name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @NotNull
    private double amount;

    public PaymentStatus validate() {
        try{
            transferAmount();
            return  PaymentStatus.SUCCESSFUL;
        } catch (Exception e) {
            System.out.println("Error in transaction: "+e);
            return PaymentStatus.FAILED;
        }
    }

    private void transferAmount() throws Exception {
        //Transaction through Payment Service Gateway
    }

}
