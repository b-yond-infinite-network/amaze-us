package app

import (
	"example.com/booster/app/handler"
	"example.com/booster/app/model"
	"example.com/booster/config"
	"fmt"
	"github.com/gorilla/mux"
	"github.com/jinzhu/gorm"
	"log"
	"net/http"
)

// App has router and db instances
type App struct {
	Router *mux.Router
	DB     *gorm.DB
}

// Initialize initializes the app with predefined configuration
func (a *App) Initialize(config *config.Config) {
	dbURI := fmt.Sprintf("%s:%s@(%s:%s)/%s?charset=%s&parseTime=True",
		config.DB.Username,
		config.DB.Password,
		config.DB.Host,
		config.DB.Port,
		config.DB.Name,
		config.DB.Charset)

	db, err := gorm.Open(config.DB.Dialect, dbURI)
	//db, err := gorm.Open(config.DB.Dialect, "guest:Guest0000!@(mysql-db:3306)/todoapp?charset=utf8&parseTime=true")

	if err != nil {
		log.Fatal("Could not connect database url -" + dbURI)
	} else {
		log.Println("Connected successfully to database.")
	}

	a.DB = model.DBMigrate(db)
	a.Router = mux.NewRouter()
	a.setRouters()
}

// setRouters sets the all required routers
func (a *App) setRouters() {
	// Routing for handling the tank
	a.Get("/tanks", a.GetAllTanks)
	a.Post("/tanks", a.CreateTank)
	a.Get("/tanks/{title}", a.GetTank)
	a.Put("/tanks/{title}", a.UpdateTank)
	a.Delete("/tanks/{title}", a.DeleteTank)
	a.Put("/tanks/{title}/archive", a.ArchiveTank)
	a.Delete("/tanks/{title}/archive", a.RestoreTank)

	// Routing for handling the fuel
	a.Get("/tanks/{title}/fuel", a.GetAllFuelParts)
	a.Post("/tanks/{title}/fuel", a.CreateFuelPart)
	a.Get("/tanks/{title}/fuel/{id:[0-9]+}", a.GetFuelPart)
	a.Put("/tanks/{title}/fuel/{id:[0-9]+}", a.UpdateFuelPart)
	a.Delete("/tanks/{title}/fuel/{id:[0-9]+}", a.DeleteFuelPart)
	a.Put("/tanks/{title}/fuel/{id:[0-9]+}/complete", a.CompleteFuelPart)
	a.Delete("/tanks/{title}/fuel/{id:[0-9]+}/complete", a.UndoFuelPart)
}

// Get wraps the router for GET method
func (a *App) Get(path string, f func(w http.ResponseWriter, r *http.Request)) {
	a.Router.HandleFunc(path, f).Methods("GET")
}

// Post wraps the router for POST method
func (a *App) Post(path string, f func(w http.ResponseWriter, r *http.Request)) {
	a.Router.HandleFunc(path, f).Methods("POST")
}

// Put wraps the router for PUT method
func (a *App) Put(path string, f func(w http.ResponseWriter, r *http.Request)) {
	a.Router.HandleFunc(path, f).Methods("PUT")
}

// Delete wraps the router for DELETE method
func (a *App) Delete(path string, f func(w http.ResponseWriter, r *http.Request)) {
	a.Router.HandleFunc(path, f).Methods("DELETE")
}

/*
** Tanks Handlers
 */
func (a *App) GetAllTanks(w http.ResponseWriter, r *http.Request) {
	handler.GetAllTanks(a.DB, w, r)
}

func (a *App) CreateTank(w http.ResponseWriter, r *http.Request) {
	handler.CreateTank(a.DB, w, r)
}

func (a *App) GetTank(w http.ResponseWriter, r *http.Request) {
	handler.GetTank(a.DB, w, r)
}

func (a *App) UpdateTank(w http.ResponseWriter, r *http.Request) {
	handler.UpdateTank(a.DB, w, r)
}

func (a *App) DeleteTank(w http.ResponseWriter, r *http.Request) {
	handler.DeleteTank(a.DB, w, r)
}

func (a *App) ArchiveTank(w http.ResponseWriter, r *http.Request) {
	handler.ArchiveTank(a.DB, w, r)
}

func (a *App) RestoreTank(w http.ResponseWriter, r *http.Request) {
	handler.RestoreTank(a.DB, w, r)
}

/*
** Fuel Handlers
 */
func (a *App) GetAllFuelParts(w http.ResponseWriter, r *http.Request) {
	handler.GetAllFuelParts(a.DB, w, r)
}

func (a *App) CreateFuelPart(w http.ResponseWriter, r *http.Request) {
	handler.CreateFuelPart(a.DB, w, r)
}

func (a *App) GetFuelPart(w http.ResponseWriter, r *http.Request) {
	handler.GetFuelPart(a.DB, w, r)
}

func (a *App) UpdateFuelPart(w http.ResponseWriter, r *http.Request) {
	handler.UpdateFuelPart(a.DB, w, r)
}

func (a *App) DeleteFuelPart(w http.ResponseWriter, r *http.Request) {
	handler.DeleteFuelPart(a.DB, w, r)
}

func (a *App) CompleteFuelPart(w http.ResponseWriter, r *http.Request) {
	handler.CompleteFuelPart(a.DB, w, r)
}

func (a *App) UndoFuelPart(w http.ResponseWriter, r *http.Request) {
	handler.UndoFuelPart(a.DB, w, r)
}

// Run the app on it's router
func (a *App) Run(host string) {
	log.Fatal(http.ListenAndServe(host, a.Router))
}
