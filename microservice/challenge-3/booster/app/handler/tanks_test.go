package handler_test

import (
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/jinzhu/gorm"
	"github.com/mingrammer/go-todo-rest-api-example/app/handler"
	"github.com/mingrammer/go-todo-rest-api-example/app/model"
	"github.com/stretchr/testify/assert"

	_ "github.com/jinzhu/gorm/dialects/sqlite"
)

func dbGorm(t *testing.T, path string, tanks ...*model.Tank) *gorm.DB {
	db, err := gorm.Open("sqlite3", path)
	if err != nil {
		t.Fatal(err)
	}
	db = db.AutoMigrate(model.Tank{})
	for _, tank := range tanks {
		db = db.Save(tank)
	}
	return db
}

func TestGetAllTanks(t *testing.T) {

	type args struct {
		db                  *gorm.DB
		w                   *httptest.ResponseRecorder
		r                   *http.Request
		expectedStatus      int
		expectedSize        int
		expectedContentType string
	}
	tests := []struct {
		name string
		args args
	}{
		{name: "OK - empty list",
			args: args{
				db:                  dbGorm(t, "/tmp/tanks-empty-list.db"),
				w:                   httptest.NewRecorder(),
				r:                   httptest.NewRequest("GET", "http://example.com/GetAllTanks", nil),
				expectedStatus:      200,
				expectedSize:        0,
				expectedContentType: "application/json",
			},
		},

		{name: "OK - list",
			args: args{
				db:                  dbGorm(t, "/tmp/tanks-list.db", &model.Tank{Title: "title"}),
				w:                   httptest.NewRecorder(),
				r:                   httptest.NewRequest("GET", "http://example.com/GetAllTanks", nil),
				expectedStatus:      200,
				expectedSize:        1,
				expectedContentType: "application/json",
			},
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			handler.GetAllTanks(tt.args.db, tt.args.w, tt.args.r)
			defer tt.args.db.Close()
			resp := tt.args.w.Result()
			assert.EqualValues(t, tt.args.expectedStatus, resp.StatusCode)
			assert.EqualValues(t, tt.args.expectedContentType, resp.Header.Get("Content-Type"))
			decoder := json.NewDecoder(resp.Body)
			var actualTanks []interface{}
			decoder.Decode(&actualTanks)
			//TODO better test for json
			assert.EqualValues(t, tt.args.expectedSize, len(actualTanks))
		})
	}
}
