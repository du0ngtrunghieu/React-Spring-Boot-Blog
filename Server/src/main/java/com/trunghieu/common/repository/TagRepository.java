package com.trunghieu.common.repository;

import com.trunghieu.common.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 11/4/2020.
 * Class: TagRepository.java
 * By : Admin
 */
@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    boolean existsById(Long id);
    List<Tag> findAllByOrderByIdDesc();
    Page<Tag> findALlByOrderByIdDesc(Pageable page);
}
