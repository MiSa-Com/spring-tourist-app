package com.ms.tourist_app.domain.daos;

import com.ms.tourist_app.application.constants.StringBase;
import com.ms.tourist_app.domain.daos.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Nationalized
    @NotBlank(message = "must not null")
    @Column(name = "name")
    private String firstName;

    @Nationalized
    @NotBlank(message = "must not null")
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birthday")
    @NotBlank(message = "must not null")
    private LocalDate birthday;

    @Column(name = "address")
    @Nationalized
    private String address;

    @Column(name = "telephone")
    private String telephone;
    @Column(name = "email")
    @NotBlank(message = "must not null")
    private String email;
    @Column(name = "password")
    @NotBlank(message = "must not null")
    private String password;
}
