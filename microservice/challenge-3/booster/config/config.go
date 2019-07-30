package config

type Config struct {
	DB *DBConfig
}

type DBConfig struct {
	Dialect  string
	ServerAdd  string
	Username string
	Password string
	Name     string
	Charset  string
}

func GetConfig() *Config {
	return &Config{
		DB: &DBConfig{
			Dialect:  "mysql",
			ServerAdd: "tcp(mysql)",
			Username: "guest",
			Password: "Guest0000!",
			Name:     "booster",
			Charset:  "utf8",
		},
	}
}
