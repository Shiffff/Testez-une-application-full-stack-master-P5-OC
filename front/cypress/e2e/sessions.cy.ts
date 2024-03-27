describe.only('Admin acess to sessions page"', () => {
  before(() => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true,
      },
    });

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      [
        {
          id: 2,
          name: 'test',
          date: '2024-03-24T00:00:00.000+00:00',
          teacher_id: 2,
          description: 'test',
          users: [4],
          createdAt: '2024-03-20T15:38:00',
          updatedAt: '2024-03-20T15:38:00',
        },
      ]
    ).as('session');

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );

    cy.url().should('include', '/sessions');
  });

  it('button create detail and edit', () => {
    cy.contains('Create').should('be.visible');
    cy.contains('Detail').should('be.visible');
    cy.contains('Edit').should('be.visible');
  });
  it('test btn create', () => {
    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      [
        {
          id: 2,
          name: 'test',
          date: '2024-03-24T00:00:00.000+00:00',
          teacher_id: 2,
          description: 'test',
          users: [4],
          createdAt: '2024-03-20T15:38:00',
          updatedAt: '2024-03-20T15:38:00',
        },
      ]
    ).as('session');
    cy.contains('Create').click();
    cy.url().should('include', '/create');
    cy.go('back');
  });
  it('test btn detail', () => {
    cy.contains('Detail').click();
    cy.url().should('include', '/detail');
  });
  //   it('test btn edit', () => {
  //     cy.contains('Edit').click();
  //     cy.url().should('include', '/update');
  //   });
});
describe('User acess to sessions page"', () => {
  before(() => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false,
      },
    });

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      [
        {
          id: 2,
          name: 'test',
          date: '2024-03-24T00:00:00.000+00:00',
          teacher_id: 2,
          description: 'test',
          users: [4],
          createdAt: '2024-03-20T15:38:00',
          updatedAt: '2024-03-20T15:38:00',
        },
      ]
    ).as('session');

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );

    cy.url().should('include', '/sessions');
  });

  it('initialize session page', () => {
    cy.contains('Create').should('not.exist');
    cy.contains('Session on').should('be.visible');
  });
});
