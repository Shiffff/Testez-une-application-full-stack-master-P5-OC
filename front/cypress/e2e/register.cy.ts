describe('empty spec', () => {
  it('Register successfull', () => {
    cy.visit('/register');

    cy.intercept('POST', '/api/auth/register', {
      body: {
        message: 'User registered successfully!',
      },
    });

    cy.get('input[formControlName=firstName]').type('John');
    cy.get('input[formControlName=lastName]').type('Doe');
    cy.get('input[formControlName=email]').type('johndoe@test.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );

    cy.url().should('include', '/login');
  });
  it('Register failed', () => {
    cy.visit('/register');

    cy.intercept('POST', '/api/auth/register', {
      statusCode: 400,
      body: {
        error: 'Bad Request',
        path: '/api/auth/register',
        status: 400,
        timestamp: '2024-03-21T14:46:42.454+00:00',
      },
    });

    cy.get('input[formControlName=firstName]').type('John');
    cy.get('input[formControlName=lastName]').type('Doe');
    cy.get('input[formControlName=email]').type('johndoe@test.fr');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );

    cy.url().should('include', '/register');
    cy.contains('An error occurred').should('be.visible');
  });
});
