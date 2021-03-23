package handler

import (
	"encoding/json"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app/repository"
	"net/http"
	"strconv"

	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app/model"
	"github.com/gorilla/mux"
)

func GetAllFuelParts(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(repository, tankTitle, w, r)
	if tank == nil {
		return
	}

	fuel := []model.FuelPart{}
	if err := repository.Related(&tank, &fuel); err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, fuel)
}

func CreateFuelPart(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(repository, tankTitle, w, r)
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

	if err := repository.Save(&fuelPart); err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusCreated, fuelPart)
}

func GetFuelPart(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(repository, tankTitle, w, r)
	if tank == nil {
		return
	}

	id, _ := strconv.Atoi(vars["id"])
	fuelPart := getFuelPartOr404(repository, id, w, r)
	if fuelPart == nil {
		return
	}
	respondJSON(w, http.StatusOK, fuelPart)
}

func UpdateFuelPart(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(repository, tankTitle, w, r)
	if tank == nil {
		return
	}

	id, _ := strconv.Atoi(vars["id"])
	fuelPart := getFuelPartOr404(repository, id, w, r)
	if fuelPart == nil {
		return
	}

	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(&fuelPart); err != nil {
		respondError(w, http.StatusBadRequest, err.Error())
		return
	}
	defer r.Body.Close()

	if err := repository.Save(&fuelPart); err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, fuelPart)
}

func DeleteFuelPart(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(repository, tankTitle, w, r)
	if tank == nil {
		return
	}

	id, _ := strconv.Atoi(vars["id"])
	fuelPart := getFuelPartOr404(repository, id, w, r)
	if fuelPart == nil {
		return
	}

	if err := repository.Delete(&fuelPart); err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusNoContent, nil)
}

func CompleteFuelPart(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(repository, tankTitle, w, r)
	if tank == nil {
		return
	}

	id, _ := strconv.Atoi(vars["id"])
	fuelPart := getFuelPartOr404(repository, id, w, r)
	if fuelPart == nil {
		return
	}

	fuelPart.Complete()
	if err := repository.Save(&fuelPart); err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, fuelPart)
}

func UndoFuelPart(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	tankTitle := vars["title"]
	tank := getTankOr404(repository, tankTitle, w, r)
	if tank == nil {
		return
	}

	id, _ := strconv.Atoi(vars["id"])
	fuelPart := getFuelPartOr404(repository, id, w, r)
	if fuelPart == nil {
		return
	}

	fuelPart.Undo()
	if err := repository.Save(&fuelPart); err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, fuelPart)
}

// getFuelPartOr404 gets a fuelPart instance if exists, or respond the 404 error otherwise
func getFuelPartOr404(repository repository.Repository, id int, w http.ResponseWriter, r *http.Request) *model.FuelPart {
	fuelPart := model.FuelPart{}
	if err := repository.First(&fuelPart, id); err != nil {
		respondError(w, http.StatusNotFound, err.Error())
		return nil
	}
	return &fuelPart
}
