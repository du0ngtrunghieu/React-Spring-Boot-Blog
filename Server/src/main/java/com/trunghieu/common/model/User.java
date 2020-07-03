package com.trunghieu.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trunghieu.common.model.audit.DateAudit;
import com.trunghieu.common.util.BlogENUM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 6/4/2020.
 * Class: User.java
 * By : Admin
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@Data
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "role_id" ,insertable = false,updatable = false)
    private Role role;
    @Column(name = "role_id")
    private Long roleId;

//    // nếu là nhân viên thì có thể login vào /admin
//    @Column(name = "is_staff")
//    private boolean isStaff = false;
//    @Column(name = "is_superuser")
//    private boolean isSuperUser = false;
//    @Column(name = "is_active")
//    private boolean isActive = false;
//    @OneToMany(mappedBy = "user")
//    private Set<UserHasRole> roles = new HashSet<>();
    private String description;
    private String avatar;
    private boolean verified = false;
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "file_id" ,insertable = false,updatable = false)
    private File file;
    @Column(name = "file_id")
    private Long fileId;
    @Enumerated(EnumType.STRING)
    private BlogENUM.STATUS status;
    public User() {

    }

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public boolean isStaff() {
        if(this.role != null){
            if(this.getRole().getName().equals(BlogENUM.RoleName.ROLE_ADMIN.getCode()) || this.getRole().getName().equals(BlogENUM.RoleName.ROLE_SUPERADMIN.getCode()))
                return true;
            else
                return false;
        }
        return false;
    }

    public boolean isSuperUser() {
        if(this.role != null){
            if(this.getRole().getName().equals(BlogENUM.RoleName.ROLE_SUPERADMIN.getCode()))
                return true;
            else
                return false;
        }
        return false;
    }

}
