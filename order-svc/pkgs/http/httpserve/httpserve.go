package httpserve

// BaseResponse is a generic response structure
type BaseResponse[T any] struct {
	Code    int    `json:"code"`
	Message string `json:"message"`
	Data    T      `json:"data"`
}

// NewBaseResponse creates a new BaseResponse instance
func NewBaseResponse[T any](code int, message string, data T) BaseResponse[T] {
	return BaseResponse[T]{
		Code:    code,
		Message: message,
		Data:    data,
	}
}
