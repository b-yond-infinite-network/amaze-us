package config

type Config struct {
	DB *DBConfig
}

type DBConfig struct {
	Dialect  string
	Host     string
	Port     string
	Username string
	Password string
	Name     string
	Charset  string
}

func GetConfig() *Config {
	return &Config{
		DB: &DBConfig{
			Dialect:  "mysql",
			Host:     "mysql-db",
			Port:     "3306",
			Username: "guest",
			Password: "Guest0000!",
			Name:     "todoapp",
			Charset:  "utf8",
		},
	}
}
