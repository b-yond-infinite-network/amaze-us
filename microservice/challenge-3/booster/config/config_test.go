package config

import (
	"reflect"
	"testing"
)

func TestGetConfig(t *testing.T) {
	tests := []struct {
		name string
		want *Config
	}{
		{
			name: "should be ok",
			want: &Config{
				DB: &DBConfig{
					Dialect:  "mysql",
					Username: "guest",
					Protocol: "tcp",
					Address:  "db:3306",
					Password: "Guest0000!",
					Name:     "todoapp",
					Charset:  "utf8",
				},
			},
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := GetConfig(); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("GetConfig() = %v, want %v", got, tt.want)
			}
		})
	}
}

func TestDBConfig_GetURI(t *testing.T) {
	type fields struct {
		Dialect  string
		Username string
		Protocol string
		Address  string
		Password string
		Name     string
		Charset  string
	}
	tests := []struct {
		name   string
		fields fields
		want   string
	}{
		{
			name: "build URI",
			want: "guest:Guest0000!@tcp(db:3306)/todoapp?charset=utf8&parseTime=True",
			fields: fields{
				Dialect:  "mysql",
				Protocol: "tcp",
				Address:  "db:3306",
				Username: "guest",
				Password: "Guest0000!",
				Name:     "todoapp",
				Charset:  "utf8",
			},
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			db := &DBConfig{
				Dialect:  tt.fields.Dialect,
				Username: tt.fields.Username,
				Protocol: tt.fields.Protocol,
				Address:  tt.fields.Address,
				Password: tt.fields.Password,
				Name:     tt.fields.Name,
				Charset:  tt.fields.Charset,
			}
			if got := db.GetURI(); got != tt.want {
				t.Errorf("DBConfig.GetURI() = %v, want %v", got, tt.want)
			}
		})
	}
}
