package handler

import (
	"encoding/json"
	"github.com/jinzhu/gorm"
	"net/http"

	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app/model"
)

// respondJSON makes the response with payload as json format
func respondJSON(w http.ResponseWriter, status int, payload interface{}) {
	response, err := json.Marshal(payload)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		w.Write([]byte(err.Error()))
		return
	}
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(status)
	w.Write([]byte(response))
}

// respondError makes the error response with payload as json format
func respondError(w http.ResponseWriter, code int, message string) {
	respondJSON(w, code, map[string]string{"error": message})
}

//Bring back the database into clean slate, very usefull for testing.
func DeleteAll(db *gorm.DB) error {

	//First
	if err := db.Unscoped().DropTableIfExists(&model.FuelPart{}).Error; err != nil {
		return err
	}

	if err := db.Unscoped().DropTableIfExists(&model.Tank{}).Error; err != nil {
		return err
	}

	return nil

}
