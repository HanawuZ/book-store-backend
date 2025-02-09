### C#
protoc \
  --proto_path=shared/protobuf \
  --grpc_out=user-svc/UserService/Apps/HealthCheck/PingPong \
  --csharp_out=user-svc/UserService/Apps/HealthCheck/PingPong \
  --csharp_opt=base_namespace=UserService.Apps.HealthCheck.PingPong\
  --plugin=protoc-gen-grpc=C:/Users/hanaw/.nuget/packages/grpc.tools/2.69.0/tools/windows_x64/grpc_csharp_plugin.exe \
  shared/protobuf/pingpong.proto


protoc    --proto_path=shared/protobuf/customer/address    --grpc_out=user-svc/UserService/Apps/CustomerAddresses/Grpc/GrpcCustomerAddressServer  --csharp_out=user-svc/UserService/Apps/CustomerAddresses/Grpc/GrpcCustomerAddressServer  --csharp_opt=base_namespace=UserService.Apps.CustomerAddresses.Grpc.GrpcCustomerAddressServer --plugin=protoc-gen-grpc=C:/Users/hanaw/.nuget/packages/grpc.tools/2.69.0/tools/windows_x64/grpc_csharp_plugin.exe    shared/protobuf/customer/address/customer-address.proto

## Go
### Customer Address
protoc \
  --proto_path=shared/protobuf \
  --go_out=order-svc/app/grpc \
  --go_opt=paths=source_relative \
  --go-grpc_out=order-svc/app/grpc \
  --go-grpc_opt=paths=source_relative \
  shared/protobuf/customer/address/customer-address.proto
