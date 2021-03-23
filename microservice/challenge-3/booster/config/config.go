package config

import (
	"github.com/spf13/viper"
	"strings"
)

type Config struct {
	Port int
	DB   *DBConfig
}

type DBConfig struct {
	Host     string
	Port     int
	Username string
	Password string
	Name     string
	Charset  string
}

// Loads configuration using Viper from booster.yml file or env variables
func initViper() error {
	appName := "booster"

	viper.AddConfigPath(".")
	viper.SetConfigName(appName)
	viper.SetConfigType("yml")

	viper.SetEnvPrefix(appName)
	viper.SetEnvKeyReplacer(strings.NewReplacer(".", "_")) // By default '.' is the name separator for nested configs
	viper.AutomaticEnv()

	if err := viper.ReadInConfig(); err != nil {
		return err
	}

	return nil
}

func GetConfig() (*Config, error) {
	if err := initViper(); err != nil {
		return nil, err
	}

	var config Config

	if err := viper.Unmarshal(&config); err != nil {
		return nil, err
	}

	return &config, nil
}
