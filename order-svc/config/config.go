package config

import (
	"fmt"
	"os"
	"path/filepath"

	"github.com/HanawuZ/book-store-backend/order-svc/config/auth"
	"github.com/HanawuZ/book-store-backend/order-svc/config/databases"
	"gopkg.in/yaml.v2"
)

type AppConfigFromFile struct {
	Databases map[string]databases.DatabaseConfig `yaml:"databases"`
	Security  auth.SecurityConfig                 `yaml:"security"`
}

//----------------------------------------------

type AppConfig struct {
	Database databases.IDatabase
	Auth     auth.JWTConfig
}

type IAppConfig interface {
	GetAuth() auth.JWTConfig
	GetDatabase() databases.IDatabase
}

func New() IAppConfig {

	currentDirectory, err := os.Getwd()
	if err != nil {
		panic(err)
	}

	parentDirectory := filepath.Dir(currentDirectory)

	// Change dire to {parentDirectory}/shared
	err = os.Chdir(parentDirectory + "/shared")
	if err != nil {
		panic(err)
	}

	// Read the YAML file
	data, err := os.ReadFile("config.yaml")
	if err != nil {
		message := fmt.Sprintf("Error reading YAML file: %v\n", err)
		panic(message)
	}

	// Parse the YAML data
	var config AppConfigFromFile
	err = yaml.Unmarshal(data, &config)
	if err != nil {
		message := fmt.Sprintf("Error parsing YAML data: %v\n", err)
		panic(message)
	}

	databaseConfig := config.Databases["order_svc_db"]

	return &AppConfig{
		Auth:     config.Security.JWT,
		Database: databases.New(databaseConfig),
	}
}

func (a *AppConfig) GetAuth() auth.JWTConfig {
	return a.Auth
}

func (a *AppConfig) GetDatabase() databases.IDatabase {
	return a.Database
}
