package auth

type SecurityConfig struct {
	JWT JWTConfig `yaml:"jwt"`
}

type JWTConfig struct {
	Secret string `yaml:"secret"`
	Issuer string `yaml:"issuer"`
}
