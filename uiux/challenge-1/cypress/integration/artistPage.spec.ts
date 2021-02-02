/// <reference types="cypress" />

context("Artist Page", () => {
  it("renders all artist's tracks", () => {
    cy.visit("http://localhost:3000/artists/118") // Queen

    cy.get(".test-content")
      .find(".test-track-card")
      .its("length")
      .should((len) => len > 0)

    cy.get(".test-content").find(".test-track-card").first().click()

    cy.location("pathname").should((pathname) => {
      expect(pathname).match(/^\/tracks\/[0-9]*$/)
    })
  })

  it("Shows which tracks have no lyrics", () => {
    cy.visit("http://localhost:3000/artists/13258") // Focus: artist with many instrumental songs

    const instrumentalTrack = cy
      .get(".test-content")
      .find(".test-track-card")
      .filter(':contains("Hocus Pocus")')
      .first()

    instrumentalTrack.trigger("mouseover")
    instrumentalTrack.contains("No lyrics available")

    instrumentalTrack.trigger("mouseout")
    instrumentalTrack
      .get(':contains("No lyrics available")')
      .should("not.exist")
  })

  it("Filters tracks without lyrics", () => {
    cy.visit("http://localhost:3000/artists/13258")

    cy.get(".test-sort-select").click()
    cy.get("body")
      .find(".test-sort-menu-item")
      .filter(':contains("Has lyrics")')
      .click()

    cy.get(".test-content")
      .find(".test-track-card")
      .filter(':contains("Sylvia")')
      .should("not.exist")
  })
})
