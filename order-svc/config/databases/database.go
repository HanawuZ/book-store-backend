package databases

type DatabaseConfig struct {
	Username     string `yaml:"username"`
	Password     string `yaml:"password"`
	Host         string `yaml:"host"`
	Port         string `yaml:"port"`
	DatabaseName string `yaml:"database_name"`
}

type IDatabase interface {
	Connect()
	Migrate()
}
