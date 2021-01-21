package model

type Ping struct {
	Ping	string `json:"ping"`
}

const pong = "pong"

func GetPing() *Ping {
	return &Ping{Ping: pong}
}