package com.backend.app.shared.models.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.Date;


import jakarta.persistence.EnumType;
@Data
@Entity
@Table(name = "sale_orders")
public class SaleOrder  {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "order_no")
    private String orderNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    
    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "net_price")
    private Double netPrice;

    @Column(name = "shipping_address")
    @Nullable
    private String shippingAddress;

    @Column(name = "shipping_latitude")
    @Nullable
    private Double shippingLatitude;

    @Column(name = "shipping_longitude")
    @Nullable
    private Double shippingLongitude;

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
   
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "note", columnDefinition = "text")
    @Nullable
    private String note;
    
    @Column(name = "customer_firstname")
    private String customerFirstName;
    
    @Column(name = "customer_lastname")
    @Nullable
    private String customerLastName;

    @Column(name = "customer_phone_one")
    @Nullable
    private String customerPhoneOne;

    @Column(name = "customer_phone_two")
    @Nullable
    private String customerPhoneTwo;

}
