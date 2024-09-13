package com.backend.app.shared.models.entities;

import java.util.Date;


import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.FetchType;

@Data
@Entity
@Table(name = "customer_addresses")
public class CustomerAddress {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "address")
    @Nullable
    private String address;

    @Column(name = "latitude")
    @Nullable
    private Double latitude;

    @Column(name = "longitude")
    @Nullable
    private Double longitude;

    @Column(name = "street")
    @Nullable
    private String street;

    @Column(name = "sub_district")
    @Nullable
    private String subDistrict;

    @Column(name = "district")
    @Nullable
    private String district;

    @Column(name = "province")
    @Nullable
    private String province;

    @Column(name = "country")
    @Nullable
    private String country;

    @Column(name = "zipcode")
    @Nullable
    private String zipcode;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;
}
