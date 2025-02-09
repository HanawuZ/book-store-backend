package config

import (
	"log"
	"os"

	"github.com/joho/godotenv"
)

type DatabaseConfig struct {
	Username     string
	Password     string
	Host         string
	Port         string
	DatabaseName string
}

type AuthConfig struct {
	Secret string
	Issuer string
}

type AppConfig struct {
	Database DatabaseConfig
	Auth     AuthConfig
}

func LoadConfig() (*AppConfig, error) {
	err := godotenv.Load()
	if err != nil {
		log.Fatal("Error loading .env file")
		return nil, err
	}

	database := DatabaseConfig{
		Username:     os.Getenv("DB_USERNAME"),
		Password:     os.Getenv("DB_PASSWORD"),
		Host:         os.Getenv("DB_HOST"),
		Port:         os.Getenv("DB_PORT"),
		DatabaseName: os.Getenv("DB_DATABASE_NAME"),
	}

	auth := AuthConfig{
		Secret: os.Getenv("JWT_SECRET"),
		Issuer: os.Getenv("JWT_ISSUER"),
	}

	return &AppConfig{
		Database: database,
		Auth:     auth,
	}, nil
}

/*
// package loader

// import (
// 	"fmt"
// 	"os"
// 	"path/filepath"

// 	"github.com/HanawuZ/book-store-backend/order-svc/config/databases"
// 	"gopkg.in/yaml.v2"
// )

// type AppConfigFromFile struct {
// 	Databases map[string]databases.DatabaseConfig `yaml:"databases"`
// 	Security  SecurityConfigFromFile              `yaml:"security"`
// }

// type SecurityConfigFromFile struct {
// 	JWT JWTConfigFromFile `yaml:"jwt"`
// }

// type JWTConfigFromFile struct {
// 	Secret string `yaml:"secret"`
// 	Issuer string `yaml:"issuer"`
// }

// func LoadConfig() (*AppConfigFromFile, error) {

// 	// Current directory is './order-svc/config/loader'
// 	currentDirectory, err := os.Getwd()
// 	if err != nil {
// 		return nil, err
// 	}

// 	// Current directory is './order-svc/config'
// 	configDirectory := filepath.Dir(currentDirectory)

// 	// Current directory is './order-svc'
// 	appDirectory := filepath.Dir(configDirectory)

// 	// Current directory is './'
// 	rootDirectory := filepath.Dir(appDirectory)

// 	// Change dir to {rootDirectory}/shared
// 	err = os.Chdir(rootDirectory + "/shared")
// 	if err != nil {
// 		return nil, err
// 	}

// 	// Read the YAML file
// 	data, err := os.ReadFile("config.yaml")
// 	if err != nil {
// 		return nil, fmt.Errorf("Error reading YAML file: %v\n", err)
// 	}

// 	// Parse the YAML data
// 	var config AppConfigFromFile
// 	err = yaml.Unmarshal(data, &config)
// 	if err != nil {
// 		return nil, fmt.Errorf("Error parsing YAML data: %v\n", err)
// 	}

// 	return &config, nil
// }
*/

//----------------------------------------------

// type AppConfig struct {
// 	Database databases.IGormDatabase
// 	Auth     auth.JWTConfig
// }

// type IAppConfig interface {
// 	GetAuth() auth.JWTConfig
// 	GetDatabase() databases.IGormDatabase
// }

// func New() IAppConfig {

// 	currentDirectory, err := os.Getwd()
// 	if err != nil {
// 		panic(err)
// 	}

// 	parentDirectory := filepath.Dir(currentDirectory)

// 	// Change dire to {parentDirectory}/shared
// 	err = os.Chdir(parentDirectory + "/shared")
// 	if err != nil {
// 		panic(err)
// 	}

// 	// Read the YAML file
// 	data, err := os.ReadFile("config.yaml")
// 	if err != nil {
// 		message := fmt.Sprintf("Error reading YAML file: %v\n", err)
// 		panic(message)
// 	}

// 	// Parse the YAML data
// 	var config AppConfigFromFile
// 	err = yaml.Unmarshal(data, &config)
// 	if err != nil {
// 		message := fmt.Sprintf("Error parsing YAML data: %v\n", err)
// 		panic(message)
// 	}

// 	databaseConfig := config.Databases["order_svc_db"]

// 	return &AppConfig{
// 		Auth:     config.Security.JWT,
// 		Database: databases.New(databaseConfig),
// 	}
// }

// func (a *AppConfig) GetAuth() auth.JWTConfig {
// 	return a.Auth
// }

// func (a *AppConfig) GetDatabase() databases.IGormDatabase {
// 	return a.Database
// }
