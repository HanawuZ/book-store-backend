package middlewares

import (
	"fmt"
	"net/http"

	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/http/httpserve"
	jwtware "github.com/gofiber/contrib/jwt"
	"github.com/gofiber/fiber/v2"
)

type AuthorizationMiddleware struct {
	Secret string
}

type IAuthorizationMiddleware interface {
	AuthorizationCustomerToken() fiber.Handler
}

func New(secret string) IAuthorizationMiddleware {
	if secret == "" {
		panic("secret is required")
	}

	fmt.Println("SECRET: " + secret)

	return &AuthorizationMiddleware{
		Secret: secret,
	}
}

func (a *AuthorizationMiddleware) AuthorizationCustomerToken() fiber.Handler {
	return jwtware.New(jwtware.Config{
		SuccessHandler: a.authSuccess,
		ErrorHandler:   a.authError,
		SigningKey:     jwtware.SigningKey{Key: []byte(a.Secret)},
	})
}

func (a *AuthorizationMiddleware) authSuccess(c *fiber.Ctx) error {
	c.Next()
	return nil
}

func (a *AuthorizationMiddleware) authError(c *fiber.Ctx, err error) error {
	c.Status(http.StatusUnauthorized).
		JSON(httpserve.NewBaseResponse(http.StatusUnauthorized, err.Error(), ""))
	return nil
}
