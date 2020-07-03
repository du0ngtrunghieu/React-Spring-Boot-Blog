package com.trunghieu.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created on 15/6/2020.
 * Class: ConfirmationToken.java
 * By : Admin
 */
@Entity
@Table(name = "confirmation_tokens")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private long tokenId;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    private Long user_id;
    public ConfirmationToken() {}

    public ConfirmationToken(User user) {
        this.user_id = user.getId();
        createdDate = new Date();
        expiryDate = calculateExpiryDate(10);
        confirmationToken = UUID.randomUUID().toString();
    }

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
