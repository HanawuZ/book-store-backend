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
    @JsonProperty("id")    
    @Column(name = "id")
    private String id;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    @JsonProperty("username")
    @Column(name = "username")
    private String username;

    @JsonProperty("password")
    @Nullable
    @Column(name = "password")
    private String password;

    @JsonProperty("profilePicture")
    @Nullable
    @Column(name = "profile_picture")
    private String profilePicture;

    @JsonProperty("email")
    @Nullable
    @Column(name = "email")
    private String email;

    @JsonProperty("createdDate")
    @Column(name = "created_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Nullable
    private Date createdDate;
    
    @JsonProperty("accountNonExpired")
    @Nullable
    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;
    
    @JsonProperty("accountNonLocked")
    @Nullable
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @JsonProperty("credentialsNonExpired")
    @Nullable
    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;
    
    @JsonProperty("providerId")
    @Nullable
    private String providerId;
    
    @JsonProperty("provider")
    @Nullable
    private String provider;

    @JsonProperty("enabled")
    private Boolean enabled;

    @JsonProperty("isUsing2FA")
    @Column(name = "is_using_2fa")
    private Boolean isUsing2FA;

    @JsonProperty("secret")
    @Column(name = "secret")
    private String secret;
}
