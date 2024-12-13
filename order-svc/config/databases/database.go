package databases

type IDatabase interface {
	Connect()
	Migrate()
}
