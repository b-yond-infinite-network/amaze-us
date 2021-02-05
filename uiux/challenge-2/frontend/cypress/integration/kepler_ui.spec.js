context('Kepler Colony Tests', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000')
  });



  it('can go to Home page', () => {
    cy.get('[aria-label="Home"]').click();
    cy.get('.content').contains('Kepler Colony')
  });

  it('can go to Pioneer page', () => {
    cy.get('[aria-label="Pioneers"]').click();
    cy.get('.content').contains('Pioneers')
  });

  it('can go to Manage page', () => {
    cy.get('[aria-label="Manage"]').click();
    cy.get('.content').contains('New requests')
  });

});
