package com.trunghieu.common.repository;

import com.trunghieu.common.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 15/6/2020.
 * Class: ConfirmationTokenRepository.java
 * By : Admin
 */
@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {
    ConfirmationToken findByConfirmationToken(String token);
}
