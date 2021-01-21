package handler

import (
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app/model"
	"net/http"
)

func Ping(w http.ResponseWriter) {
	respondJSON(w, http.StatusOK, model.GetPing())
}
