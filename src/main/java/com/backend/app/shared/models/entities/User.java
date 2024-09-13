package com.backend.app.shared.models.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    private String id;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    @Column(name = "username")
    private String username;

    @Nullable
    @Column(name = "password")
    private String password;

    @Nullable
    @Column(name = "profile_picture")
    private String profilePicture;

    @Nullable
    @Column(name = "email")
    private String email;

    @Column(name = "created_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Nullable
    private Date createdDate;
    
    @Nullable
    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;
    
    @Nullable
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @Nullable
    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;
    
    @Nullable
    private String providerId;
    
    @Nullable
    private String provider;

    private Boolean enabled;

    @Column(name = "is_using_2fa")
    private Boolean isUsing2FA;

    @Column(name = "secret")
    private String secret;
}
