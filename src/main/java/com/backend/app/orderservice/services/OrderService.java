package com.backend.app.orderservice.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.app.orderservice.models.CreateOrderRequest;
import com.backend.app.orderservice.models.query.CartItemQueryResult;
import com.backend.app.orderservice.repositories.CustomerAddressRepository;
import com.backend.app.orderservice.repositories.CustomerRepository;
import com.backend.app.orderservice.repositories.SaleItemRepository;
import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.models.entities.Customer;
import com.backend.app.shared.models.entities.CustomerAddress;
import com.backend.app.shared.models.entities.OrderStatus;
import com.backend.app.shared.models.entities.SaleItem;
import com.backend.app.shared.models.entities.SaleOrder;

@Service
public class OrderService {
    
    private CustomerAddressRepository customerAddressRepository;
    private SaleItemRepository saleItemRepository;
    private CustomerRepository customerRepository;
    
    @Autowired
    public OrderService(
        CustomerAddressRepository customerAddressRepository, 
        SaleItemRepository saleItemRepository,
        CustomerRepository customerRepository
    ) {
        this.customerAddressRepository = customerAddressRepository;
        this.saleItemRepository = saleItemRepository;
        this.customerRepository = customerRepository;
    }

    public BaseResponse<String> createOrder(CreateOrderRequest request) {
        try {
            Optional<CustomerAddress> customerAddressResult = customerAddressRepository.getCustomerAddressById(request.getCustomerAddresssId());
            if (customerAddressResult.isEmpty()) {
                return new BaseResponse<>(4000, "Customer address not found", null);
            }
            CustomerAddress customerAddress = customerAddressResult.get();

            Optional<Customer> customerResult = customerRepository.getCustomerById(request.getCustomerId());
            if (customerResult.isEmpty()) {
                return new BaseResponse<>(4000, "Customer not found", null);
            }
            Customer customer = customerResult.get();

            List<CartItemQueryResult> cartItems = saleItemRepository.getCartItemByCustomerId(request.getCustomerId());
            if (cartItems.isEmpty()) {
                return new BaseResponse<>(4000, "Cart item not found", null);
            }

            Integer totalQuantity = 0;
            Double totalPrice = 0.0;
            for (CartItemQueryResult cartItem : cartItems) {
                totalQuantity += cartItem.getQuantity();
                totalPrice += cartItem.getQuantity() * cartItem.getPrice();
            } 

            Date now = new Date();
            SaleOrder order = new SaleOrder();
            order.setId(UUID.randomUUID().toString());
            order.setOrderStatus(OrderStatus.AWAITING_PAYMENT);
            order.setTotalQuantity(totalQuantity);
            order.setTotalPrice(totalPrice);
            order.setNetPrice(totalPrice);
            order.setShippingAddress(customerAddress.getAddress());
            order.setShippingLatitude(customerAddress.getLatitude());
            order.setShippingLongitude(customerAddress.getLongitude());
            order.setStreet(customerAddress.getStreet());
            order.setSubDistrict(customerAddress.getSubDistrict());
            order.setDistrict(customerAddress.getDistrict());
            order.setProvince(customerAddress.getProvince());
            order.setZipcode(customerAddress.getZipcode());
            order.setCountry(customerAddress.getCountry()); 
            order.setCreatedDate(now);
            order.setUpdatedDate(now);
            order.setNote(request.getNote());
            order.setCustomerFirstName(customer.getFirstName());
            order.setCustomerLastName(customer.getLastName());
            order.setCustomerPhoneOne(customer.getPhoneOne());
            order.setCustomerPhoneTwo(customer.getPhoneTwo());

            List<SaleItem> saleItems = new ArrayList<>();
            for (CartItemQueryResult cartItem : cartItems) {
                SaleItem item = new SaleItem();
                item.setId(UUID.randomUUID().toString());
                item.setQuantity(cartItem.getQuantity());
                item.setPrice(cartItem.getPrice());
                item.setGenre(cartItem.getGenre());
                item.setIsbn(cartItem.getIsbn());
                item.setTitle(cartItem.getTitle());
                item.setPublicationYear(cartItem.getPublicationYear().toLocalDate());
                item.setPublisherName(cartItem.getPublisherName());
                item.setCreatedDate(now);
                item.setCreatedBy("system");
                item.setUpdatedDate(now);
                item.setUpdatedBy("system");
                item.setSaleOrder(order);
                saleItems.add(item);
            }

            Boolean completed = saleItemRepository.createSaleOrder(order, saleItems, request.getCustomerId());
            if (completed.equals(false)) {
                return new BaseResponse<>(4000, "Failed to create order", null);
            }
            return new BaseResponse<>(2001, "Order created successfully", order.getId());
        } catch (Exception exception) {
            exception.printStackTrace();
            String error = String.format("Internal Server Error: %s", exception.getMessage());
            return new BaseResponse<>(5000, error, null);
        }
    }
}
