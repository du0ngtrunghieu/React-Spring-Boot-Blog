package com.trunghieu.common.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trunghieu.common.model.audit.DateAudit;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role_has_permission")
public class RoleHasPermission extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "permission_id", insertable = false, updatable = false)
    private Permission permission;

    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "permission_id")
    private Long permissionId;
}
