package com.trunghieu.common.model;

import com.trunghieu.common.model.audit.DateAudit;
import com.trunghieu.common.model.audit.UserDateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "posts")
public class Post extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name="slug")
    private String slug;
    @Column(name="summary")
    private String summary;
    @Column(name = "published")
    private Boolean published;
    @Column(name = "content")
    private String content;
    @Column(name = "thumbnail")
    private String thumbnail;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_to_categories",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @Column(name = "count_views")
    private int countView =0;
}
