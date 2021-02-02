/// <reference types="cypress" />

context("Lyrics Page", () => {
  it("renders some track lyrics with copyright and pixel", () => {
    cy.visit("http://localhost:3000/artists/118") // Queen

    cy.get(".test-content").find(".test-track-card").first().click()

    cy.get(".test-content").contains("Lyrics powered by www.musixmatch.com")

    cy.get("body").find(".test-mxm-pixel").should("exist")
  })
})
