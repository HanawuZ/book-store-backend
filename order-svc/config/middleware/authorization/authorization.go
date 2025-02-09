package authorization

import (
	"net/http"

	"github.com/HanawuZ/book-store-backend/order-svc/config"
	"github.com/HanawuZ/book-store-backend/order-svc/pkgs/http/httpserve"
	jwtware "github.com/gofiber/contrib/jwt"
	"github.com/gofiber/fiber/v2"
)

type AuthorizationMiddleware struct {
	Secret string
	Issuer string
}

type IAuthorizationMiddleware interface {
	AuthorizationCustomerToken() fiber.Handler
}

func New(authConfig config.AuthConfig) IAuthorizationMiddleware {
	if authConfig.Secret == "" {
		panic("secret is required")
	}

	if authConfig.Issuer == "" {
		panic("issuer is required")
	}

	return &AuthorizationMiddleware{
		Secret: authConfig.Secret,
		Issuer: authConfig.Issuer,
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
