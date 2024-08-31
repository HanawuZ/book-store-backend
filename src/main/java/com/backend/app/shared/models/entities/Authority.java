package com.backend.app.shared.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "authorities")
public class Authority implements GrantedAuthority {
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("authority")
    public String authority;
}
