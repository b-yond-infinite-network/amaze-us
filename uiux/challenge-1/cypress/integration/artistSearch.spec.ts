/// <reference types="cypress" />

context("Dashboard", () => {
  it("renders results in artists cards (max. 12)", () => {
    cy.visit("http://localhost:3000/artists/search?keywords=romeo santos")

    cy.get(".test-content")
      .find(".test-artist-card")
      .its("length")
      .should((len) => len > 0 && len <= 12)

    cy.get(".test-content").find(".test-artist-card").first().click()

    cy.location("pathname").should((pathname) => {
      expect(pathname).match(/^\/artists\/[0-9]*$/)
    })
  })

  it("renders a message when no data was found", () => {
    cy.visit(
      "http://localhost:3000/artists/search?keywords=eahsghapgjpijgapije giasp"
    )

    cy.get(".test-artist-card").should("not.exist")

    cy.get(".test-content").contains("No artist found")
  })
})
