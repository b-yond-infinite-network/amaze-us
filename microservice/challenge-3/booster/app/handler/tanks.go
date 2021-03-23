package handler

import (
	"encoding/json"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app/repository"
	"net/http"

	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app/model"
	"github.com/gorilla/mux"
)

func GetAllTanks(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	tanks := []model.Tank{}

	if err := repository.Find(&tanks); err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}

	respondJSON(w, http.StatusOK, tanks)
}

func CreateTank(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	tank := model.Tank{}

	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(&tank); err != nil {
		respondError(w, http.StatusBadRequest, err.Error())
		return
	}
	defer r.Body.Close()

	if err := repository.Save(&tank); err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusCreated, tank)
}

func GetTank(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	title := vars["title"]
	tank := getTankOr404(repository, title, w, r)
	if tank == nil {
		return
	}
	respondJSON(w, http.StatusOK, tank)
}

func UpdateTank(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	title := vars["title"]
	tank := getTankOr404(repository, title, w, r)
	if tank == nil {
		return
	}

	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(&tank); err != nil {
		respondError(w, http.StatusBadRequest, err.Error())
		return
	}
	defer r.Body.Close()

	if err := repository.Save(&tank); err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, tank)
}

func DeleteTank(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	title := vars["title"]
	tank := getTankOr404(repository, title, w, r)
	if tank == nil {
		return
	}
	if err := repository.Delete(&tank); err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusNoContent, nil)
}

func ArchiveTank(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	title := vars["title"]
	tank := getTankOr404(repository, title, w, r)
	if tank == nil {
		return
	}
	tank.Archive()
	if err := repository.Save(&tank); err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, tank)
}

func RestoreTank(repository repository.Repository, w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)

	title := vars["title"]
	tank := getTankOr404(repository, title, w, r)
	if tank == nil {
		return
	}
	tank.Restore()
	if err := repository.Save(&tank); err != nil {
		respondError(w, http.StatusInternalServerError, err.Error())
		return
	}
	respondJSON(w, http.StatusOK, tank)
}

// getTankOr404 gets a tank instance if exists, or respond the 404 error otherwise
func getTankOr404(repository repository.Repository, title string, w http.ResponseWriter, r *http.Request) *model.Tank {
	tank := model.Tank{}
	if err := repository.First(&tank, model.Tank{Title: title}); err != nil {
		respondError(w, http.StatusNotFound, err.Error())
		return nil
	}
	return &tank
}
