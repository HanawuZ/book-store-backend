package com.backend.app.shared.models.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_address_id")
    private CustomerAddress customerAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    
    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "net_amount")
    private Double netAmount;
   
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "note")
    @Nullable
    private String note;
}
