package com.backend.app.shared.models.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customer_addresses")
public class CustomerAddress {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "address")
    @JsonProperty("address")
    @Nullable
    private String address;

    @Column(name = "latitude")
    @JsonProperty("latitude")
    @Nullable
    private Double latitude;

    @Column(name = "longitude")
    @JsonProperty("longitude")
    @Nullable
    private Double longitude;

    @Column(name = "street")
    @JsonProperty("street")
    @Nullable
    private String street;

    @Column(name = "sub_district")
    @JsonProperty("subDistrict")
    @Nullable
    private String subDistrict;

    @Column(name = "district")
    @JsonProperty("district")
    @Nullable
    private String district;

    @Column(name = "province")
    @JsonProperty("province")
    @Nullable
    private String province;

    @Column(name = "country")
    @JsonProperty("country")
    @Nullable
    private String country;

    @Column(name = "zipcode")
    @JsonProperty("zipcode")
    @Nullable
    private String zipcode;

    @JsonProperty("isActive")
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @JsonProperty("createdDate")
    @Column(name = "created_date")
    private Date createdDate;

    @JsonProperty("createdBy")
    @Column(name = "created_by")
    private String createdBy;

    @JsonProperty("updatedDate")
    @Column(name = "updated_date")
    private Date updatedDate;

    @JsonProperty("updatedBy")
    @Column(name = "updated_by")
    private String updatedBy;
}
