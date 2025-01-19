package com.example.catalog_svc.app.grpc.cart;
import java.util.List;

import com.example.catalog_svc.app.cart.models.query.CartItem;
import com.example.catalog_svc.app.cart.services.GetCartService;
import com.example.catalog_svc.app.grpc.cart.GrpcCartServiceGrpc.GrpcCartServiceImplBase;
import com.example.catalog_svc.models.response.BaseResponse;

import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;
@GrpcService
public class GrpcCartServiceImpl  extends GrpcCartServiceImplBase {
    
  private GetCartService cartService;

  public GrpcCartServiceImpl(GetCartService cartService) {
    this.cartService = cartService;
  }

  @Override
  public void getCartItemByCustomerProto(CartItemRequestProto request, StreamObserver<CartItemResponseProto> responseObserver) {
    System.out.println("getCartItemByCustomer");
    String customerId = request.getCustomerId();
    if (customerId.isEmpty()) {
      responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT.asRuntimeException());
      return;
    }

    System.out.println("REQUEST: "+ customerId);
    BaseResponse<List<CartItem>> response = cartService.getCartItemByCustomer(customerId);
    if (response.getCode() != 2000) {
      responseObserver.onError(io.grpc.Status.INTERNAL.asRuntimeException());
      return;
    }

    List<CartItem> cartItems = response.getData();

    CartItemResponseProto.Builder builder = CartItemResponseProto.newBuilder();

    for (Integer i = 0; i < cartItems.size(); i++) {
      CartItem item = cartItems.get(i);

      CartItemProto.Builder itemProto = CartItemProto.newBuilder();

      itemProto.setBookId(item.getBookId());
      itemProto.setQuantity(item.getQuantity());
      itemProto.setCopiesAvailable(item.getCopiesAvailable());
      itemProto.setGenre(item.getGenre());
      itemProto.setIsbn(item.getIsbn());
      itemProto.setPrice(item.getPrice());

      // Format date to string with format 2001-04-05
      String formattedDate = item.getPublicationYear().toString();
      
      itemProto.setPublicationYear(formattedDate);
      itemProto.setTitle(item.getTitle());

      builder.addItems(itemProto);
    }

    CartItemResponseProto responseProto = builder.build();

    responseObserver.onNext(responseProto);
    responseObserver.onCompleted();
  }
    
}
