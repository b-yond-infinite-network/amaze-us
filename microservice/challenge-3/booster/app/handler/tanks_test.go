package handler_test

import (
	"io/ioutil"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/jinzhu/gorm"
	"github.com/mingrammer/go-todo-rest-api-example/app/handler"
	"github.com/mingrammer/go-todo-rest-api-example/app/model"
	"github.com/stretchr/testify/assert"

	_ "github.com/jinzhu/gorm/dialects/sqlite"
)

//dbGorm generate a database
func dbGorm(t *testing.T, path string, tanks ...model.Tank) *gorm.DB {
	db, err := gorm.Open("sqlite3", path)
	if err != nil {
		t.Fatal(err)
	}
	model.DBMigrate(db)
	for _, tank := range tanks {
		db.Save(tank)
	}
	return db
}

func TestGetAllTanks(t *testing.T) {

	type args struct {
		db                  *gorm.DB
		w                   *httptest.ResponseRecorder
		r                   *http.Request
		expectedStatus      int
		expectedBody        string
		expectedContentType string
	}
	tests := []struct {
		name string
		args args
	}{
		{name: "OK - empty list",
			args: args{
				db:                  dbGorm(t, "/tmp/empty-list-tanks.db"),
				w:                   httptest.NewRecorder(),
				r:                   httptest.NewRequest("GET", "http://example.com/GetAllTanks", nil),
				expectedStatus:      200,
				expectedBody:        "[]",
				expectedContentType: "application/json",
			},
		},

		{name: "OK - list",
			args: args{
				db:                  dbGorm(t, "/tmp/list-tanks.db", model.Tank{}),
				w:                   httptest.NewRecorder(),
				r:                   httptest.NewRequest("GET", "http://example.com/GetAllTanks", nil),
				expectedStatus:      200,
				expectedBody:        "[]",
				expectedContentType: "application/json",
			},
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			handler.GetAllTanks(tt.args.db, tt.args.w, tt.args.r)
			resp := tt.args.w.Result()
			body, _ := ioutil.ReadAll(resp.Body)
			assert.EqualValues(t, tt.args.expectedStatus, resp.StatusCode)
			assert.EqualValues(t, tt.args.expectedContentType, resp.Header.Get("Content-Type"))
			//TODO better test for json
			assert.EqualValues(t, tt.args.expectedBody, string(body))
		})
	}
}
