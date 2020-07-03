package com.trunghieu.common.model;

import com.trunghieu.common.model.audit.DateAudit;
import com.trunghieu.common.util.BlogENUM;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/6/2020.
 * Class: ModuleType.java
 * By : Admin
 */
@Data
@Entity
@Table(name = "module_types")
public class ModuleType extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private BlogENUM.ModuleName name;
    @OneToMany(mappedBy = "moduleType")
    private List<Permission> permissions = new ArrayList<>();
}
