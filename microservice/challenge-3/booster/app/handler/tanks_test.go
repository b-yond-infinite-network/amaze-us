package handler

import (
	"encoding/json"
	"errors"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app/model"
	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"
)

func TestBooks(t *testing.T) {
	RegisterFailHandler(Fail)
	RunSpecs(t, "Tank Handler Suite")
}

type TestTankRepository struct {
	findSucceed    bool
	firstSucceed   bool
	relatedSucceed bool
	saveSucceed    bool
	deleteSucceed  bool
}

func getTank() model.Tank {
	tank := model.Tank{Title: "Tank14"}
	tank.ID = 14

	return tank
}

func (r *TestTankRepository) Find(out interface{}) error {
	if err := succeedOrError(r.findSucceed); err != nil {
		return err
	}

	tank := getTank()

	tanks := out.(*[]model.Tank)
	*tanks = append(*tanks, tank)

	return nil
}

func (r *TestTankRepository) First(out interface{}, where ...interface{}) error {
	if err := succeedOrError(r.firstSucceed); err != nil {
		return err
	}

	tank := out.(*model.Tank)
	*tank = getTank()

	return nil
}

func (r *TestTankRepository) Related(from interface{}, out interface{}) error {
	return succeedOrError(r.relatedSucceed)
}

func (r *TestTankRepository) Save(value interface{}) error {
	if err := succeedOrError(r.saveSucceed); err != nil {
		return err
	}

	tank := value.(*model.Tank)
	*tank = getTank()

	return nil
}

func (r *TestTankRepository) Delete(value interface{}) error {
	return succeedOrError(r.deleteSucceed)
}

func succeedOrError(succeed bool) error {
	if succeed {
		return nil
	} else {
		return errors.New("unexpected method call")
	}
}

var _ = Describe("Tank Handler", func() {
	Describe("when calling GetAllTanks", func() {
		It("should work on success case", func() {
			repository := &TestTankRepository{findSucceed: true}
			writer := httptest.NewRecorder()
			response, _ := json.Marshal([]model.Tank{getTank()})

			GetAllTanks(repository, writer, nil)

			Expect(writer.Code).To(Equal(http.StatusOK))
			Expect(writer.Header().Get("Content-Type")).To(Equal("application/json"))
			Expect(writer.Body.Bytes()).To(Equal(response))
		})

		It("should fail on error case", func() {
			repository := &TestTankRepository{}
			writer := httptest.NewRecorder()
			response, _ := json.Marshal(map[string]string{"error": "unexpected method call"})

			GetAllTanks(repository, writer, nil)

			Expect(writer.Code).To(Equal(http.StatusInternalServerError))
			Expect(writer.Header().Get("Content-Type")).To(Equal("application/json"))
			Expect(writer.Body.Bytes()).To(Equal(response))
		})
	})

	Describe("when calling CreateTank", func() {
		It("should work on success case", func() {
			repository := &TestTankRepository{saveSucceed: true}
			writer := httptest.NewRecorder()
			tank := getTank()
			tank.ID = 0
			body, _ := json.Marshal(tank)
			request := httptest.NewRequest("POST", "http://example.com/foo", strings.NewReader(string(body)))
			response, _ := json.Marshal(getTank())

			CreateTank(repository, writer, request)

			Expect(writer.Code).To(Equal(http.StatusCreated))
			Expect(writer.Header().Get("Content-Type")).To(Equal("application/json"))
			Expect(writer.Body.Bytes()).To(Equal(response))
		})

		It("should fail on bad request", func() {
			repository := &TestTankRepository{saveSucceed: true}
			writer := httptest.NewRecorder()
			body := `{"title": 33}`
			request := httptest.NewRequest("POST", "http://example.com/foo", strings.NewReader(body))
			response, _ := json.Marshal(map[string]string{"error": "json: cannot unmarshal number into Go struct field Tank.title of type string"})

			CreateTank(repository, writer, request)

			Expect(writer.Code).To(Equal(http.StatusBadRequest))
			Expect(writer.Header().Get("Content-Type")).To(Equal("application/json"))
			Expect(writer.Body.Bytes()).To(Equal(response))
		})

		It("should fail on error case", func() {
			repository := &TestTankRepository{}
			writer := httptest.NewRecorder()
			tank := getTank()
			tank.ID = 0
			body, _ := json.Marshal(tank)
			request := httptest.NewRequest("POST", "http://example.com/foo", strings.NewReader(string(body)))
			response, _ := json.Marshal(map[string]string{"error": "unexpected method call"})

			CreateTank(repository, writer, request)

			Expect(writer.Code).To(Equal(http.StatusInternalServerError))
			Expect(writer.Header().Get("Content-Type")).To(Equal("application/json"))
			Expect(writer.Body.Bytes()).To(Equal(response))
		})
	})
})
