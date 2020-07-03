package com.trunghieu.common.model;


import com.trunghieu.common.model.audit.DateAudit;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Comment extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;
    @Column(name = "enabled")
    private Boolean enabled;
    private Long post_id;
    private Long user_id;
    private String user_ip;
}
