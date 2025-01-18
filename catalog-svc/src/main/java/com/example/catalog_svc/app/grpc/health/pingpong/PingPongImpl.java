package com.example.catalog_svc.app.grpc.health.pingpong;

import com.example.catalog_svc.app.grpc.health.pingpong.PingPongGrpc.PingPongImplBase;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PingPongImpl extends PingPongImplBase{
  
  @Override
  public void startPing(Ping ping, StreamObserver<Pong> responseObserver) {
    long startTime = System.currentTimeMillis();
    System.out.printf("start time %d\n", startTime);
    Pong pong = Pong.newBuilder()
        .setId(ping.getId())
        .setMessage("Received " + ping.getMessage())
        .build();
    responseObserver.onNext(pong);
    responseObserver.onCompleted();
    long endTime = System.currentTimeMillis();
    System.out.printf("end time %d\n", endTime);
    System.out.println("Ping Received in " + (endTime - startTime) + " ms");
  }
 }
