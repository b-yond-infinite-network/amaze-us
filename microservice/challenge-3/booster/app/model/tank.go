package model

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/mysql"
)

type Tank struct {
	gorm.Model
	Title    string `gorm:"unique" json:"title"`
	Archived bool   `json:"archived"`
	Fuel    []FuelPart `gorm:"ForeignKey:TankID" json:"fuel"`
}

func (p *Tank) Archive() {
	p.Archived = true
}

func (p *Tank) Restore() {
	p.Archived = false
}

// DBMigrate will create and migrate the tables, and then make the some relationships if necessary
func DBMigrate(db *gorm.DB) *gorm.DB {
	db.AutoMigrate(&Tank{}, &FuelPart{})
	db.Model(&FuelPart{}).AddForeignKey("tank_id", "tanks(id)", "CASCADE", "CASCADE")
	return db
}
