package com.trunghieu.common.model;

import com.trunghieu.common.model.audit.DateAudit;
import com.trunghieu.common.util.BlogENUM;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "permission_types")
public class PermissionType extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private BlogENUM.PermissionName name;
    @OneToMany(mappedBy = "permissionType")
    private List<Permission> permissions = new ArrayList<>();

}
