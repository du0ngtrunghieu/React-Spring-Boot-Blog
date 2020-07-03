package com.trunghieu.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trunghieu.common.model.audit.AuditAware;
import com.trunghieu.common.model.audit.AuditLogListener;
import com.trunghieu.common.model.audit.DateAudit;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 6/4/2020.
 * Class: Category.java
 * By : Admin
 */

@Entity
@Data
@Table(name = "categories")
@EntityListeners(value = {AuditLogListener.class})
public class Category extends DateAudit implements Serializable, AuditAware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "description")
    private String description;
    @Column(name = "slug")
    private String slug;
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id", updatable = false, insertable = false)
    private Category category;

    @Column(name = "parent_id")
    private Long parent_id;

    @OneToMany(mappedBy = "category")
    private List<Category> subCategories = new ArrayList<>();

}
