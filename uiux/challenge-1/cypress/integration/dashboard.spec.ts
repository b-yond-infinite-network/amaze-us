/// <reference types="cypress" />

context("Dashboard", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000")
  })

  it("Renders top 8 songs and 6 artists", () => {
    cy.get(".test-content")
      .find(".test-artist-card")
      .its("length")
      .should("eq", 6)

    cy.get(".test-content")
      .find(".test-track-card")
      .its("length")
      .should("eq", 8)
  })

  it("Redirects to artist page after clicking an artist card", () => {
    cy.get(".test-content").find(".test-artist-card").first().click()

    cy.location("pathname").should((pathname) => {
      expect(pathname).match(/^\/artists\/[0-9]*$/)
    })
  })

  it("Redirects to track page after clicking a track card", () => {
    cy.get(".test-content").find(".test-track-card").first().click()

    cy.location("pathname").should((pathname) => {
      expect(pathname).match(/^\/tracks\/[0-9]*$/)
    })
  })

  it("Allows to query artists", () => {
    const input = cy.get(".test-artist-search-input")
    input.should("be.visible")
    input.type("queen")

    cy.location("pathname").should((pathname) => {
      expect(pathname).to.eq("/artists/search")
    })
  })

  it("Allows to query artists in xs devices", () => {
    cy.viewport("samsung-s10")

    const queryButton = cy.get(".test-artist-search-button-xs")
    queryButton.should("be.visible")
    queryButton.click()

    const input = cy.get(".test-artist-search-input-xs")
    input.should("be.visible")
    input.type("emerson lake palmer")

    cy.location("pathname").should((pathname) => {
      expect(pathname).to.eq("/artists/search")
    })
  })
})
