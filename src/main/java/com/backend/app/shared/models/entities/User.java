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
    private String id;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    @JsonProperty("firstName")
    private String firstName;
    
    @JsonProperty("lastName")
    @Nullable
    private String lastName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    @Nullable
    private String password;

    @JsonProperty("profilePicture")
    @Nullable
    private String profilePicture;

    @JsonProperty("email")
    @Nullable
    private String email;

    @JsonProperty("createdDate")
    @Column(name = "created_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Nullable
    private Date createdDate;
    
    @JsonProperty("accountNonExpired")
    private Boolean accountNonExpired;
    
    @JsonProperty("accountNonLocked")
    private Boolean accountNonLocked;

    @JsonProperty("credentialsNonExpired")
    @Nullable
    private Boolean credentialsNonExpired;
    
    @JsonProperty("providerId")
    @Nullable
    private String providerId;
    
    @JsonProperty("provider")
    @Nullable
    private String provider;

    @JsonProperty("enabled")
    private Boolean enabled;

    private Boolean isUsing2FA;

    private String secret;
}
