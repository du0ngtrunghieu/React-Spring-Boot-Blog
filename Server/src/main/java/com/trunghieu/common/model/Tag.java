package com.trunghieu.common.model;

import com.trunghieu.common.model.audit.DateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created on 6/4/2020.
 * Class: Tag.java
 * By : Admin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "tags")
public class Tag extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "slug")
    private String slug;
    @Column(name = "description")
    private String description;
    @Column(name = "enabled")
    private boolean enabled;
}
