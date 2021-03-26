package repository

import (
	"fmt"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app/model"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/config"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/mysql"
	"log"
	"time"
)

type Repository interface {
	Find(out interface{}) error
	First(out interface{}, where ...interface{}) error
	Related(from interface{}, out interface{}) error
	Save(value interface{}) error
	Delete(value interface{}) error
}

type MysqlRepository struct {
	db *gorm.DB
}

func (r *MysqlRepository) Find(out interface{}) error {
	return r.db.Find(out).Error
}

func (r *MysqlRepository) First(out interface{}, where ...interface{}) error {
	return r.db.First(out, where...).Error
}

func (r *MysqlRepository) Related(from interface{}, out interface{}) error {
	db := r.db.Model(from)
	if err := db.Error; err != nil {
		return err
	}

	return db.Related(out).Error
}

func (r *MysqlRepository) Save(value interface{}) error {
	return r.db.Save(value).Error
}

func (r *MysqlRepository) Delete(value interface{}) error {
	return r.db.Delete(value).Error
}

// dbMigrate will create and migrate the tables, and then make the some relationships if necessary
func dbMigrate(db *gorm.DB) *gorm.DB {
	db.AutoMigrate(&model.Tank{}, &model.FuelPart{})
	db.Model(&model.FuelPart{}).AddForeignKey("tank_id", "tanks(id)", "CASCADE", "CASCADE")
	return db
}

func openConnection(connectionTimeout time.Duration, dbURI string) (*gorm.DB, error) {
	var db *gorm.DB

	db, err := gorm.Open("mysql", dbURI)
	if err != nil && connectionTimeout <= 0 {
		return nil, err
	} else if err != nil {
		wait := 500 * time.Millisecond
		time.Sleep(wait)
		db, err = openConnection(connectionTimeout-wait, dbURI)
		if err != nil {
			return nil, err
		}
	}

	return db, nil
}

func CreateMysqlRepository(config *config.Config) Repository {
	dbURI := fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=%s&parseTime=True",
		config.DB.Username,
		config.DB.Password,
		config.DB.Host,
		config.DB.Port,
		config.DB.Name,
		config.DB.Charset)

	db, err := openConnection(config.DB.ConnectionTimeout, dbURI)
	if err != nil {
		log.Fatalln("Could not connect database", err)
	}

	sqlDB := db.DB()
	sqlDB.SetMaxIdleConns(5)
	sqlDB.SetMaxOpenConns(10)
	sqlDB.SetConnMaxLifetime(30 * time.Minute)
	sqlDB.SetConnMaxIdleTime(5 * time.Minute)

	return &MysqlRepository{db: dbMigrate(db)}
}
