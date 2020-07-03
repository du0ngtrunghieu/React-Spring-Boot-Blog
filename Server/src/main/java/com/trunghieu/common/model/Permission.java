package com.trunghieu.common.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trunghieu.common.model.audit.DateAudit;
import com.trunghieu.common.util.BlogENUM;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "permissions")

public class Permission extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "permission_type_id", updatable = false, insertable = false)
    private PermissionType permissionType;
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "module_id", updatable = false, insertable = false)
    private ModuleType moduleType;

    @Column(name = "permission_type_id")
    private Long permissionTypeId;
    @Column(name = "module_id")
    private Long moduleId;
    @Column(name = "active")
    private boolean isActive;

    @OneToMany(mappedBy = "permission")
    private List<RoleHasPermission> roleHasPermissions = new ArrayList<>();


}
