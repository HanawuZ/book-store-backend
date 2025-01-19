package com.example.catalog_svc.app.grpc.cart;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@jakarta.annotation.Generated(
    value = "by gRPC proto compiler (version 1.69.1)",
    comments = "Source: cart/cart.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class GrpcCartServiceGrpc {

  private GrpcCartServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "cart.GrpcCartService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.catalog_svc.app.grpc.cart.CartItemRequestProto,
      com.example.catalog_svc.app.grpc.cart.CartItemResponseProto> getGetCartItemByCustomerProtoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCartItemByCustomerProto",
      requestType = com.example.catalog_svc.app.grpc.cart.CartItemRequestProto.class,
      responseType = com.example.catalog_svc.app.grpc.cart.CartItemResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.catalog_svc.app.grpc.cart.CartItemRequestProto,
      com.example.catalog_svc.app.grpc.cart.CartItemResponseProto> getGetCartItemByCustomerProtoMethod() {
    io.grpc.MethodDescriptor<com.example.catalog_svc.app.grpc.cart.CartItemRequestProto, com.example.catalog_svc.app.grpc.cart.CartItemResponseProto> getGetCartItemByCustomerProtoMethod;
    if ((getGetCartItemByCustomerProtoMethod = GrpcCartServiceGrpc.getGetCartItemByCustomerProtoMethod) == null) {
      synchronized (GrpcCartServiceGrpc.class) {
        if ((getGetCartItemByCustomerProtoMethod = GrpcCartServiceGrpc.getGetCartItemByCustomerProtoMethod) == null) {
          GrpcCartServiceGrpc.getGetCartItemByCustomerProtoMethod = getGetCartItemByCustomerProtoMethod =
              io.grpc.MethodDescriptor.<com.example.catalog_svc.app.grpc.cart.CartItemRequestProto, com.example.catalog_svc.app.grpc.cart.CartItemResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCartItemByCustomerProto"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.catalog_svc.app.grpc.cart.CartItemRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.catalog_svc.app.grpc.cart.CartItemResponseProto.getDefaultInstance()))
              .setSchemaDescriptor(new GrpcCartServiceMethodDescriptorSupplier("GetCartItemByCustomerProto"))
              .build();
        }
      }
    }
    return getGetCartItemByCustomerProtoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GrpcCartServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GrpcCartServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GrpcCartServiceStub>() {
        @java.lang.Override
        public GrpcCartServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GrpcCartServiceStub(channel, callOptions);
        }
      };
    return GrpcCartServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GrpcCartServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GrpcCartServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GrpcCartServiceBlockingStub>() {
        @java.lang.Override
        public GrpcCartServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GrpcCartServiceBlockingStub(channel, callOptions);
        }
      };
    return GrpcCartServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GrpcCartServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GrpcCartServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GrpcCartServiceFutureStub>() {
        @java.lang.Override
        public GrpcCartServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GrpcCartServiceFutureStub(channel, callOptions);
        }
      };
    return GrpcCartServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getCartItemByCustomerProto(com.example.catalog_svc.app.grpc.cart.CartItemRequestProto request,
        io.grpc.stub.StreamObserver<com.example.catalog_svc.app.grpc.cart.CartItemResponseProto> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCartItemByCustomerProtoMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service GrpcCartService.
   */
  public static abstract class GrpcCartServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return GrpcCartServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service GrpcCartService.
   */
  public static final class GrpcCartServiceStub
      extends io.grpc.stub.AbstractAsyncStub<GrpcCartServiceStub> {
    private GrpcCartServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GrpcCartServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GrpcCartServiceStub(channel, callOptions);
    }

    /**
     */
    public void getCartItemByCustomerProto(com.example.catalog_svc.app.grpc.cart.CartItemRequestProto request,
        io.grpc.stub.StreamObserver<com.example.catalog_svc.app.grpc.cart.CartItemResponseProto> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCartItemByCustomerProtoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service GrpcCartService.
   */
  public static final class GrpcCartServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<GrpcCartServiceBlockingStub> {
    private GrpcCartServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GrpcCartServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GrpcCartServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.catalog_svc.app.grpc.cart.CartItemResponseProto getCartItemByCustomerProto(com.example.catalog_svc.app.grpc.cart.CartItemRequestProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCartItemByCustomerProtoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service GrpcCartService.
   */
  public static final class GrpcCartServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<GrpcCartServiceFutureStub> {
    private GrpcCartServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GrpcCartServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GrpcCartServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.catalog_svc.app.grpc.cart.CartItemResponseProto> getCartItemByCustomerProto(
        com.example.catalog_svc.app.grpc.cart.CartItemRequestProto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCartItemByCustomerProtoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CART_ITEM_BY_CUSTOMER_PROTO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CART_ITEM_BY_CUSTOMER_PROTO:
          serviceImpl.getCartItemByCustomerProto((com.example.catalog_svc.app.grpc.cart.CartItemRequestProto) request,
              (io.grpc.stub.StreamObserver<com.example.catalog_svc.app.grpc.cart.CartItemResponseProto>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetCartItemByCustomerProtoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.catalog_svc.app.grpc.cart.CartItemRequestProto,
              com.example.catalog_svc.app.grpc.cart.CartItemResponseProto>(
                service, METHODID_GET_CART_ITEM_BY_CUSTOMER_PROTO)))
        .build();
  }

  private static abstract class GrpcCartServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GrpcCartServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.catalog_svc.app.grpc.cart.CartProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("GrpcCartService");
    }
  }

  private static final class GrpcCartServiceFileDescriptorSupplier
      extends GrpcCartServiceBaseDescriptorSupplier {
    GrpcCartServiceFileDescriptorSupplier() {}
  }

  private static final class GrpcCartServiceMethodDescriptorSupplier
      extends GrpcCartServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    GrpcCartServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GrpcCartServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GrpcCartServiceFileDescriptorSupplier())
              .addMethod(getGetCartItemByCustomerProtoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
