package handler

import (
	"encoding/json"
	"net/http"

	"github.com/gorilla/mux"
	"github.com/jinzhu/gorm"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app/model"
)

func GetAllTanks(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	tanks := []model.Tank{}
	db.Find(&tanks)
	respondJSON(w, http.StatusOK, tanks)
}

func CreateTank(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	tank := model.Tank{}

	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(&tank); err != nil {
		respondError(w, http.StatusBadRequest, err.Error())
		return
	}
	defer r.Body.Close()

	if err := db.Save(&tank).Error; err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusCreated, tank)
}

func GetTank(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	title := vars["title"]
	tank := getTankOr404(db, title, w, r)
	if tank == nil {
		return
	}
	respondJSON(w, http.StatusOK, tank)
}

func UpdateTank(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	title := vars["title"]
	tank := getTankOr404(db, title, w, r)
	if tank == nil {
		return
	}

	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(&tank); err != nil {
		respondError(w, http.StatusBadRequest, err.Error())
		return
	}
	defer r.Body.Close()

	if err := db.Save(&tank).Error; err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, tank)
}

func DeleteTank(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	title := vars["title"]
	tank := getTankOr404(db, title, w, r)
	if tank == nil {
		return
	}
	if err := db.Delete(&tank).Error; err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusNoContent, nil)
}

func ArchiveTank(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	title := vars["title"]
	tank := getTankOr404(db, title, w, r)
	if tank == nil {
		return
	}
	tank.Archive()
	if err := db.Save(&tank).Error; err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, tank)
}

func RestoreTank(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	title := vars["title"]
	tank := getTankOr404(db, title, w, r)
	if tank == nil {
		return
	}
	tank.Restore()
	if err := db.Save(&tank).Error; err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, tank)
}

// getTankOr404 gets a tank instance if exists, or respond the 404 error otherwise
func getTankOr404(db *gorm.DB, title string, w http.ResponseWriter, r *http.Request) *model.Tank {
	tank := model.Tank{}
	if err := db.First(&tank, model.Tank{Title: title}).Error; err != nil {
		respondError(w, http.StatusNotFound, err.Error())
		return nil
	}
	return &tank
}
