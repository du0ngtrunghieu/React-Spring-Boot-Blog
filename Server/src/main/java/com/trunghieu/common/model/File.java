package com.trunghieu.common.model;

import com.trunghieu.common.model.audit.DateAudit;
import lombok.Data;

import javax.persistence.*;

/**
 * Created on 14/6/2020.
 * Class: File.java
 * By : Admin
 */
@Entity
@Data
@Table(name = "files")
public class File  extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String nameFile;
    @Column(name = "url")
    private String urlFile;
    @Column(name = "type")
    private String typeFile;
    @Column(name = "size")
    private Long sizeFile;
}
