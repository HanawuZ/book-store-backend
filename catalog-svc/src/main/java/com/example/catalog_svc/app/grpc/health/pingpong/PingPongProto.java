// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: pingpong.proto
// Protobuf Java Version: 4.29.1

package com.example.catalog_svc.app.grpc.health.pingpong;

public final class PingPongProto {
  private PingPongProto() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 1,
      /* suffix= */ "",
      PingPongProto.class.getName());
  }
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pingpong_Ping_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_pingpong_Ping_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_pingpong_Pong_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_pingpong_Pong_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016pingpong.proto\022\010pingpong\"#\n\004Ping\022\n\n\002id" +
      "\030\001 \001(\005\022\017\n\007message\030\002 \001(\t\"#\n\004Pong\022\n\n\002id\030\001 " +
      "\001(\005\022\017\n\007message\030\002 \001(\t27\n\010PingPong\022+\n\tStar" +
      "tPing\022\016.pingpong.Ping\032\016.pingpong.PongBO\n" +
      "0com.example.catalog_svc.app.grpc.health" +
      ".pingpongB\rPingPongProtoP\001Z\n./pingpongb\006" +
      "proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_pingpong_Ping_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_pingpong_Ping_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_pingpong_Ping_descriptor,
        new java.lang.String[] { "Id", "Message", });
    internal_static_pingpong_Pong_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_pingpong_Pong_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_pingpong_Pong_descriptor,
        new java.lang.String[] { "Id", "Message", });
    descriptor.resolveAllFeaturesImmutable();
  }

  // @@protoc_insertion_point(outer_class_scope)
}