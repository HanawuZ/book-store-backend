// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: pingpong.proto
// Protobuf Java Version: 4.29.1

package com.example.catalog_svc.app.grpc.health.pingpong;

public interface PingOrBuilder extends
    // @@protoc_insertion_point(interface_extends:pingpong.Ping)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Field number 1-15 use 1 byte, while field 16th - 2047th use 2 bytes
   * So, the first 15 fields should be reserved for fields that are used oftenly
   * </pre>
   *
   * <code>int32 id = 1;</code>
   * @return The id.
   */
  int getId();

  /**
   * <code>string message = 2;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 2;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();
}