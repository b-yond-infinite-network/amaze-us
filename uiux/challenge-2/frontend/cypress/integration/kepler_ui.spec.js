context('Kepler Colony Tests', () => {
  beforeEach(() => {
    cy.intercept('POST', 'http://localhost:3000/v1/login', { fixture: 'authentication.json' });
    cy.visit('http://localhost:3000/login');
    cy.get('input[data-test=username]').type('username');
    cy.get('input[data-test=password]').type(`password`);
    cy.get('button.login-button').click();
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

  it('can go to Audit page', () => {
    cy.get('[aria-label="Audit"]').click();
    cy.get('.content').contains('Processed requests')
  });

});
