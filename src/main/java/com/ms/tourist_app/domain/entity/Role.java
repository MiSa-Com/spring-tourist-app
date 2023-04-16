package com.ms.tourist_app.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = AppStr.Role.tableRole)
public class Role extends BaseEntity {
    @Column(name = AppStr.Role.name)
    private String name;

    @ManyToMany
    @JoinTable(name = AppStr.Role.joinTableUserRole)
    @JsonIgnore
    private List<User> users;

    public Role(String name) {
        this.name = name;
    }
}
