package tests

import (
	"fmt"
	"net/http"
	"time"

	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app/handler"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app/model"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/config"

	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
)

var _ = Describe("Tests", func() {

	Context("Booster", func() {

		var appSrvUrl, tanksUrl string
		var appSrv *app.App
		var configTest *config.Config

		//Start the server (in background) with default settings
		BeforeSuite(func() {
			appSrvUrl = "http://127.0.0.1:3000"
			tanksUrl = appSrvUrl + "/tanks"
			configTest = config.GetConfig()
			appSrv = &app.App{}
			errChan := make(chan bool)
			go func() {
				appSrv.StartAppMain(configTest)
				errChan <- true
			}()

			select {
			case err := <-errChan:
				Expect(err).ShouldNot(BeTrue())
			case <-time.After(time.Second):
			}
			Expect(appSrv.Server).ShouldNot(BeNil())

		})

		//Gracefully stop the serer
		AfterSuite(func() {
			Expect(handler.DeleteAll(appSrv.DB)).Should(BeNil())
			appSrv.Stop()

		})

		Context("Check basic booster HTTP Server", func() {
			It("Tests basic http", func() {
				res, err := http.Get(appSrvUrl)
				Expect(err).Should(BeNil())
				Expect(res.StatusCode).Should(Equal(http.StatusNotFound))
			})
		})

		Context("Tanks", func() {

			var shouldDelete bool

			t1 := model.Tank{
				Title: "tank1",
			}

			BeforeEach(func() {
				shouldDelete = true
				Expect(SendJson(tanksUrl, POST, t1)).Should(BeNil())

			})

			AfterEach(func() {
				if shouldDelete {
					Expect(SendJson(tanksUrl+"/"+t1.Title, DEL, t1)).Should(BeNil())
				}
			})

			It("Creates", func() {
				var tank1 model.Tank
				tank_bytes, err := HttpGet(tanksUrl + "/" + t1.Title)
				Expect(err).ShouldNot(HaveOccurred())
				Expect(UnmarshalStruct(tank_bytes, &tank1)).ShouldNot(HaveOccurred())
				Expect(tank1.Title).Should(Equal(t1.Title))
			})

			It("Deletes", func() {

				//Delete the tank record
				Expect(SendJson(tanksUrl+"/"+t1.Title, DEL, t1)).Should(BeNil())
				_, err := HttpGet(tanksUrl + "/" + t1.Title)
				Expect(err).Should(HaveOccurred())

				//Just dont try to delete in the AfterEach Script
				shouldDelete = false

			})

			It("Updtes", func() {
				var tank1, tank2 model.Tank
				tank1.Title = t1.Title
				tank1.Archived = true

				Expect(SendJson(tanksUrl+"/"+t1.Title, PUT, tank1)).Should(BeNil())

				tank2_bytes, err := HttpGet(tanksUrl + "/" + t1.Title)
				Expect(err).ShouldNot(HaveOccurred())
				Expect(UnmarshalStruct(tank2_bytes, &tank2)).ShouldNot(HaveOccurred())
				Expect(tank1.Title).Should(Equal(tank2.Title))
				Expect(tank1.Archived).Should(Equal(tank2.Archived))

			})
			It("Archive", func() {

				var tank1 model.Tank

				Expect(SendJson(tanksUrl+"/"+t1.Title+"/archive", PUT, t1)).Should(BeNil())

				tank2_bytes, err := HttpGet(tanksUrl + "/" + t1.Title)
				Expect(err).ShouldNot(HaveOccurred())
				Expect(UnmarshalStruct(tank2_bytes, &tank1)).ShouldNot(HaveOccurred())
				Expect(tank1.Archived).Should(BeTrue())

			})
			It("Restores", func() {

				var tank1 model.Tank

				Expect(SendJson(tanksUrl+"/"+t1.Title+"/archive", DEL, t1)).Should(BeNil())

				tank2_bytes, err := HttpGet(tanksUrl + "/" + t1.Title)
				Expect(err).ShouldNot(HaveOccurred())
				Expect(UnmarshalStruct(tank2_bytes, &tank1)).ShouldNot(HaveOccurred())
				Expect(tank1.Archived).Should(BeFalse())

			})
		})

		Context("FuelParts", func() {

			var fuelUrl string
			var shouldDelete bool

			t1 := model.Tank{
				Title: "tank1",
			}

			f1 := model.FuelPart{
				Title: "f1",
			}

			Context("CRUD Tests", func() {

				BeforeEach(func() {
					shouldDelete = true
					fuelUrl = tanksUrl + "/" + t1.Title + "/fuel"
					deadLine := time.Now()
					f1.Deadline = &deadLine
					Expect(SendJson(tanksUrl, POST, t1)).Should(BeNil())
					Expect(SendJson(fuelUrl, POST, f1)).Should(BeNil())
				})

				AfterEach(func() {
					//FuelParts can be deleted by their ID which gets auto-incremented so
					// we need a slightly complicated logic than delting a tank
					if shouldDelete {
						fParts, err := getFuelParts(fuelUrl)
						Expect(err).ShouldNot(HaveOccurred())
						for _, fPart := range fParts {
							Expect(SendJson(fmt.Sprintf("%s/%d", fuelUrl, fPart.ID), DEL, f1)).Should(BeNil())
						}
					}
					Expect(SendJson(tanksUrl+"/"+t1.Title, DEL, t1)).Should(BeNil())
				})

				It("Creates", func() {
					var f2 []model.FuelPart
					f2_bytes, err := HttpGet(fuelUrl)
					Expect(err).ShouldNot(HaveOccurred())
					Expect(UnmarshalStruct(f2_bytes, &f2)).ShouldNot(HaveOccurred())
					Expect(len(f2)).Should(Equal(1))
					Expect(f2[0].Title).Should(Equal(f1.Title))
				})

				It("Deletes", func() {

					f2, err := getFuelParts(fuelUrl)
					Expect(err).ShouldNot(HaveOccurred())
					Expect(len(f2)).Should(Equal(1))

					//Delete it
					Expect(SendJson(fmt.Sprintf("%s/%d", fuelUrl, f2[0].ID), DEL, f1)).Should(BeNil())

					f2, err = getFuelParts(fuelUrl)
					Expect(err).ShouldNot(HaveOccurred())
					Expect(len(f2)).Should(Equal(0))

					//Just dont try to delete the fuelpart again @ AfterEach
					shouldDelete = false

				})
				It("Complete", func() {

					//Get the fuel parts to fetch the lates ID
					f2, err := getFuelParts(fuelUrl)
					Expect(err).ShouldNot(HaveOccurred())
					Expect(len(f2)).Should(Equal(1))

					Expect(SendJson(fmt.Sprintf("%s/%d/complete", fuelUrl, f2[0].ID), PUT, f1)).Should(BeNil())

					f2, err = getFuelParts(fuelUrl)
					Expect(err).ShouldNot(HaveOccurred())
					Expect(len(f2)).Should(Equal(1))

					//Check if Complete flag is set
					Expect(f2[0].Done).Should(BeTrue())

				})

				It("Undo", func() {
					//Get the fuel parts to fetch the lates ID and set it as complte
					f2, err := getFuelParts(fuelUrl)
					Expect(err).ShouldNot(HaveOccurred())
					Expect(len(f2)).Should(Equal(1))

					Expect(SendJson(fmt.Sprintf("%s/%d/complete", fuelUrl, f2[0].ID), PUT, f1)).Should(BeNil())

					//Now immediately call undo, this is the only way if we want to write
					//Self contained tests
					Expect(SendJson(fmt.Sprintf("%s/%d/complete", fuelUrl, f2[0].ID), DEL, f1)).Should(BeNil())

					f2, err = getFuelParts(fuelUrl)
					Expect(err).ShouldNot(HaveOccurred())
					Expect(len(f2)).Should(Equal(1))

					//Check if Complete flag is set
					Expect(f2[0].Done).Should(BeFalse())

				})

			})

		})
	})
})

func getFuelParts(url string) ([]model.FuelPart, error) {

	var f []model.FuelPart

	f2_bytes, err := HttpGet(url)
	if err != nil {
		return f, err
	}
	err = UnmarshalStruct(f2_bytes, &f)
	if err != nil {
		return f, err
	}

	return f, nil

}
