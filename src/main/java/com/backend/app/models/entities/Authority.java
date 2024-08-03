package com.backend.app.models.entities;

import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("authority")
    public String authority;
}
