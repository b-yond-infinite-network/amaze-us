package model

import (
	. "github.com/onsi/ginkgo"
	. "github.com/onsi/gomega"
	"testing"
)

func TestBooks(t *testing.T) {
	RegisterFailHandler(Fail)
	RunSpecs(t, "Model Suite")
}

var _ = Describe("Tank", func() {
	Describe("when using Archive method", func() {
		It("should set Archived as 'true' when it is 'false'", func() {
			tank := Tank{Archived: false}

			tank.Archive()

			Expect(tank.Archived).To(Equal(true))
		})

		It("should set Archived as 'true' when it is 'true'", func() {
			tank := Tank{Archived: true}

			tank.Archive()

			Expect(tank.Archived).To(Equal(true))
		})
	})

	Describe("when using Restore method", func() {
		It("should set Archived as 'false' when it is 'false'", func() {
			tank := Tank{Archived: false}

			tank.Restore()

			Expect(tank.Archived).To(Equal(false))
		})

		It("should set Archived as 'false' when it is 'true'", func() {
			tank := Tank{Archived: true}

			tank.Restore()

			Expect(tank.Archived).To(Equal(false))
		})
	})
})

var _ = Describe("FuelPart", func() {
	Describe("when using Complete method", func() {
		It("should set Done as 'true' when it is 'false'", func() {
			fuelPart := FuelPart{Done: false}

			fuelPart.Complete()

			Expect(fuelPart.Done).To(Equal(true))
		})

		It("should set Done as 'true' when it is 'true'", func() {
			fuelPart := FuelPart{Done: true}

			fuelPart.Complete()

			Expect(fuelPart.Done).To(Equal(true))
		})
	})

	Describe("when using Undo method", func() {
		It("should set Done as 'false' when it is 'false'", func() {
			fuelPart := FuelPart{Done: false}

			fuelPart.Undo()

			Expect(fuelPart.Done).To(Equal(false))
		})

		It("should set Done as 'false' when it is 'true'", func() {
			fuelPart := FuelPart{Done: true}

			fuelPart.Undo()

			Expect(fuelPart.Done).To(Equal(false))
		})
	})
})
