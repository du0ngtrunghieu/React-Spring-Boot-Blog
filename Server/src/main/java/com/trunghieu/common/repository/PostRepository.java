package com.trunghieu.common.repository;

import com.trunghieu.common.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 11/4/2020.
 * Class: PostRepository.java
 * By : Admin
 */
@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

}
