package com.trunghieu.common.repository;

import com.trunghieu.common.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 14/6/2020.
 * Class: FileRepository.java
 * By : Admin
 */
@Repository
public interface FileRepository extends JpaRepository<File,Long> {
}
