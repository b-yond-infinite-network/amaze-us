package config

import "fmt"

type Config struct {
	DB *DBConfig
}

type DBConfig struct {
	Dialect  string
	Protocol string
	Address  string
	Username string
	Password string
	Name     string
	Charset  string
}

// GetURI returns the URI databse [username[:password]@][protocol[(address)]]/dbname[?param1=value1&...&paramN=valueN]
func (db *DBConfig) GetURI() string {
	return fmt.Sprintf("%s:%s@%s(%s)/%s?charset=%s&parseTime=True",
		db.Username,
		db.Password,
		db.Protocol,
		db.Address,
		db.Name,
		db.Charset)
}

// GetConfig returns the configuration
func GetConfig() *Config {
	return &Config{
		DB: &DBConfig{
			Dialect:  "mysql",
			Protocol: "tcp",
			Address:  "db:3306",
			Username: "guest",
			Password: "Guest0000!",
			Name:     "todoapp",
			Charset:  "utf8",
		},
	}
}
