package com.srbh.hbms.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "photo", uniqueConstraints = {
        @UniqueConstraint(columnNames = "photoURL")
})
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photoId;

    @NotBlank
    @URL(message = "Enter a valid URL")
    private String photoURL;

}
