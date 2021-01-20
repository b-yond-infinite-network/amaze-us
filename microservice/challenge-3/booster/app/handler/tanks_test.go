package handler_test

import (
	"database/sql"
	"github.com/DATA-DOG/go-sqlmock"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/config"
	"github.com/jinzhu/gorm"
	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
	"net/http/httptest"
	"regexp"
	"testing"
)

func TestTests(t *testing.T) {
	RegisterFailHandler(Fail)
	RunSpecs(t, "Tests Suite")
}


var _ = Describe("Repository", func() {
	var booster *app.App
	var mock sqlmock.Sqlmock

	BeforeEach(func() {
		var db *sql.DB
		var err error

		db, mock, err = sqlmock.New()
		Expect(err).ShouldNot(HaveOccurred())

		gdb, err := gorm.Open(config.GetConfig().DB.Dialect, db)
		Expect(err).ShouldNot(HaveOccurred())

		booster = &app.App{DB:gdb}
	})
	AfterEach(func() {
		err := mock.ExpectationsWereMet() // make sure all expectations were met
		Expect(err).ShouldNot(HaveOccurred())
	})

	It("test GetAllTanks", func(){

		const sqlSelectAll = "SELECT * FROM `tanks` WHERE `tanks`.`deleted_at` IS NULL"

		mock.ExpectQuery(regexp.QuoteMeta(sqlSelectAll)).
			WillReturnRows(sqlmock.NewRows(nil))

		r := httptest.NewRequest("GET", "/tanks", nil)
		w := httptest.NewRecorder()
		booster.GetAllTanks(w, r)

	})

	It("test CreateTank", func(){

		const sqlCreate = "SELECT * FROM `tanks` WHERE `tanks`.`deleted_at` IS NULL"

		mock.ExpectQuery(regexp.QuoteMeta(sqlCreate)).
			WillReturnRows(sqlmock.NewRows(nil))

		r := httptest.NewRequest("POST", "/tanks", nil)
		w := httptest.NewRecorder()
		booster.CreateTank(w, r)
	})
})
