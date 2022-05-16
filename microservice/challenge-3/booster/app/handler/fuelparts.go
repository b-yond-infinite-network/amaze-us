package handler

import (
	"encoding/json"
	"net/http"
	"strconv"

	"example.com/booster/app/model"
	"github.com/gorilla/mux"
	"github.com/jinzhu/gorm"
)

func GetAllFuelParts(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(db, tankTitle, w, r)
	if tank == nil {
		return
	}

	fuel := []model.FuelPart{}
	if err := db.Model(&tank).Related(&fuel).Error; err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, fuel)
}

func CreateFuelPart(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(db, tankTitle, w, r)
	if tank == nil {
		return
	}

	fuelPart := model.FuelPart{TankID: tank.ID}

	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(&fuelPart); err != nil {
		respondError(w, http.StatusBadRequest, err.Error())
		return
	}
	defer r.Body.Close()

	if err := db.Save(&fuelPart).Error; err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusCreated, fuelPart)
}

func GetFuelPart(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(db, tankTitle, w, r)
	if tank == nil {
		return
	}

	id, _ := strconv.Atoi(vars["id"])
	fuelPart := getFuelPartOr404(db, id, w, r)
	if fuelPart == nil {
		return
	}
	respondJSON(w, http.StatusOK, fuelPart)
}

func UpdateFuelPart(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(db, tankTitle, w, r)
	if tank == nil {
		return
	}

	id, _ := strconv.Atoi(vars["id"])
	fuelPart := getFuelPartOr404(db, id, w, r)
	if fuelPart == nil {
		return
	}

	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(&fuelPart); err != nil {
		respondError(w, http.StatusBadRequest, err.Error())
		return
	}
	defer r.Body.Close()

	if err := db.Save(&fuelPart).Error; err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, fuelPart)
}

func DeleteFuelPart(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(db, tankTitle, w, r)
	if tank == nil {
		return
	}

	id, _ := strconv.Atoi(vars["id"])
	fuelPart := getFuelPartOr404(db, id, w, r)
	if fuelPart == nil {
		return
	}

	if err := db.Delete(&tank).Error; err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusNoContent, nil)
}

func CompleteFuelPart(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(db, tankTitle, w, r)
	if tank == nil {
		return
	}

	id, _ := strconv.Atoi(vars["id"])
	fuelPart := getFuelPartOr404(db, id, w, r)
	if fuelPart == nil {
		return
	}

	fuelPart.Complete()
	if err := db.Save(&fuelPart).Error; err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, fuelPart)
}

func UndoFuelPart(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(db, tankTitle, w, r)
	if tank == nil {
		return
	}

	id, _ := strconv.Atoi(vars["id"])
	fuelPart := getFuelPartOr404(db, id, w, r)
	if fuelPart == nil {
		return
	}

	fuelPart.Undo()
	if err := db.Save(&fuelPart).Error; err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, fuelPart)
}

// getFuelPartOr404 gets a fuelPart instance if exists, or respond the 404 error otherwise
func getFuelPartOr404(db *gorm.DB, id int, w http.ResponseWriter, r *http.Request) *model.FuelPart {
	fuelPart := model.FuelPart{}
	if err := db.First(&fuelPart, id).Error; err != nil {
		respondError(w, http.StatusNotFound, err.Error())
		return nil
	}
	return &fuelPart
}
