package com.trunghieu.common.repository;

import com.trunghieu.common.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created on 10/4/2020.
 * Class: CategoryRepository.java
 * By : Admin
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
    Boolean existsBySlug(String slug);
    boolean existsById(Long id);
    List<Category> findAllByOrderByIdDesc();
    Page<Category> findALlByOrderByIdDesc(Pageable page);
}
