package jwt

import (
	"fmt"

	"github.com/gofiber/fiber/v2"
	"github.com/golang-jwt/jwt/v5"
)

func GetCustomerId(c *fiber.Ctx) (string, error) {
	if c.Query("customer_id") != "" {
		return c.Query("customer_id"), nil
	}
	user, exists := c.Locals("user").(*jwt.Token)
	if !exists {
		return "", fmt.Errorf("failed to get user from locals or invalid type")
	}
	claims, exists := user.Claims.(jwt.MapClaims)
	if !exists {
		return "", fmt.Errorf("failed to extract claims from token")
	}

	// Get "customer_id" from claims
	customerID, exists := claims["customer_id"].(string)
	if !exists || customerID == "" {
		return "", fmt.Errorf("customer_id is missing or not a string in claims")
	}

	return customerID, nil
}
