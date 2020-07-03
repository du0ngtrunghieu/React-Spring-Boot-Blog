package com.trunghieu.common.model;

import com.trunghieu.common.model.audit.DateAudit;
import com.trunghieu.common.util.BlogENUM;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 6/4/2020.
 * Class: Role.java
 * By : Admin
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
@Data
public class Role extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<RoleHasPermission> roleHasPermissions = new ArrayList<>();
    @OneToMany(mappedBy = "role")
    private List<User> users = new ArrayList<>();
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "is_staff")
    private boolean isStaff = false;
    @Column(name = "is_administrator")
    private boolean isAdministrator = false;
}
