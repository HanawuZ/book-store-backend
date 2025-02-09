package databases

type Database struct {
	Username     string
	Password     string
	Host         string
	Port         string
	DatabaseName string
}

type IDatabase interface {
	Connect() error
	Migrate() error
}
