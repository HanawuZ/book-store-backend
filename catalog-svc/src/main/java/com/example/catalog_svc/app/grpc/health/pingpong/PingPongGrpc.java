package com.example.catalog_svc.app.grpc.health.pingpong;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@jakarta.annotation.Generated(
    value = "by gRPC proto compiler (version 1.69.1)",
    comments = "Source: pingpong.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PingPongGrpc {

  private PingPongGrpc() {}

  public static final java.lang.String SERVICE_NAME = "pingpong.PingPong";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.catalog_svc.app.grpc.health.pingpong.Ping,
      com.example.catalog_svc.app.grpc.health.pingpong.Pong> getStartPingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StartPing",
      requestType = com.example.catalog_svc.app.grpc.health.pingpong.Ping.class,
      responseType = com.example.catalog_svc.app.grpc.health.pingpong.Pong.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.catalog_svc.app.grpc.health.pingpong.Ping,
      com.example.catalog_svc.app.grpc.health.pingpong.Pong> getStartPingMethod() {
    io.grpc.MethodDescriptor<com.example.catalog_svc.app.grpc.health.pingpong.Ping, com.example.catalog_svc.app.grpc.health.pingpong.Pong> getStartPingMethod;
    if ((getStartPingMethod = PingPongGrpc.getStartPingMethod) == null) {
      synchronized (PingPongGrpc.class) {
        if ((getStartPingMethod = PingPongGrpc.getStartPingMethod) == null) {
          PingPongGrpc.getStartPingMethod = getStartPingMethod =
              io.grpc.MethodDescriptor.<com.example.catalog_svc.app.grpc.health.pingpong.Ping, com.example.catalog_svc.app.grpc.health.pingpong.Pong>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StartPing"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.catalog_svc.app.grpc.health.pingpong.Ping.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.catalog_svc.app.grpc.health.pingpong.Pong.getDefaultInstance()))
              .setSchemaDescriptor(new PingPongMethodDescriptorSupplier("StartPing"))
              .build();
        }
      }
    }
    return getStartPingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PingPongStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PingPongStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PingPongStub>() {
        @java.lang.Override
        public PingPongStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PingPongStub(channel, callOptions);
        }
      };
    return PingPongStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PingPongBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PingPongBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PingPongBlockingStub>() {
        @java.lang.Override
        public PingPongBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PingPongBlockingStub(channel, callOptions);
        }
      };
    return PingPongBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PingPongFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PingPongFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PingPongFutureStub>() {
        @java.lang.Override
        public PingPongFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PingPongFutureStub(channel, callOptions);
        }
      };
    return PingPongFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * PingPongService has a method, which is StartPing
     * </pre>
     */
    default void startPing(com.example.catalog_svc.app.grpc.health.pingpong.Ping request,
        io.grpc.stub.StreamObserver<com.example.catalog_svc.app.grpc.health.pingpong.Pong> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStartPingMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PingPong.
   */
  public static abstract class PingPongImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PingPongGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PingPong.
   */
  public static final class PingPongStub
      extends io.grpc.stub.AbstractAsyncStub<PingPongStub> {
    private PingPongStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PingPongStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PingPongStub(channel, callOptions);
    }

    /**
     * <pre>
     * PingPongService has a method, which is StartPing
     * </pre>
     */
    public void startPing(com.example.catalog_svc.app.grpc.health.pingpong.Ping request,
        io.grpc.stub.StreamObserver<com.example.catalog_svc.app.grpc.health.pingpong.Pong> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStartPingMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PingPong.
   */
  public static final class PingPongBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PingPongBlockingStub> {
    private PingPongBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PingPongBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PingPongBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * PingPongService has a method, which is StartPing
     * </pre>
     */
    public com.example.catalog_svc.app.grpc.health.pingpong.Pong startPing(com.example.catalog_svc.app.grpc.health.pingpong.Ping request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStartPingMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PingPong.
   */
  public static final class PingPongFutureStub
      extends io.grpc.stub.AbstractFutureStub<PingPongFutureStub> {
    private PingPongFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PingPongFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PingPongFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * PingPongService has a method, which is StartPing
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.catalog_svc.app.grpc.health.pingpong.Pong> startPing(
        com.example.catalog_svc.app.grpc.health.pingpong.Ping request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStartPingMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_START_PING = 0;

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
        case METHODID_START_PING:
          serviceImpl.startPing((com.example.catalog_svc.app.grpc.health.pingpong.Ping) request,
              (io.grpc.stub.StreamObserver<com.example.catalog_svc.app.grpc.health.pingpong.Pong>) responseObserver);
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
          getStartPingMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.catalog_svc.app.grpc.health.pingpong.Ping,
              com.example.catalog_svc.app.grpc.health.pingpong.Pong>(
                service, METHODID_START_PING)))
        .build();
  }

  private static abstract class PingPongBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PingPongBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.catalog_svc.app.grpc.health.pingpong.PingPongProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PingPong");
    }
  }

  private static final class PingPongFileDescriptorSupplier
      extends PingPongBaseDescriptorSupplier {
    PingPongFileDescriptorSupplier() {}
  }

  private static final class PingPongMethodDescriptorSupplier
      extends PingPongBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PingPongMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (PingPongGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PingPongFileDescriptorSupplier())
              .addMethod(getStartPingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
