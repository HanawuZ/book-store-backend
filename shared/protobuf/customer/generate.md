## C#

```
protoc --proto_path=shared/protobuf/customer --grpc_out=user-svc/UserService/Apps/Customer/Grpc/GrpcCustomerServer  --csharp_out=user-svc/UserService/Apps/Customer/Grpc/GrpcCustomerServer --csharp_opt=base_namespace=UserService.Apps.Customer.Grpc.GrpcCustomerServer --plugin=protoc-gen-grpc=C:/Users/hanaw/.nuget/packages/grpc.tools/2.69.0/tools/windows_x64/grpc_csharp_plugin.exe   shared/protobuf/customer/customer.proto
```

## Go
```
protoc \
  --proto_path=shared/protobuf \
  --go_out=order-svc/app/grpc \
  --go_opt=paths=source_relative \
  --go-grpc_out=order-svc/app/grpc \
  --go-grpc_opt=paths=source_relative \
  shared/protobuf/customer/customer.proto
```