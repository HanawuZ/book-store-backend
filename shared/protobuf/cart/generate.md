## Go
```
protoc \
  --proto_path=shared/protobuf/cart \
  --go_out=order-svc/app/grpc/cart \
  --go_opt=paths=source_relative \
  --go-grpc_out=order-svc/app/grpc/cart \
  --go-grpc_opt=paths=source_relative \
  shared/protobuf/cart/*.proto
```

## Java
```
protoc \
  --proto_path=shared/protobuf/cart \
  --plugin=protoc-gen-grpc-java=E:/protoc-29.1-win64/bin/protoc-gen-grpc-java.exe \
  --java_out=catalog-svc/src/main/java \
  --grpc-java_out=catalog-svc/src/main/java \
  shared/protobuf/cart/cart.proto
```