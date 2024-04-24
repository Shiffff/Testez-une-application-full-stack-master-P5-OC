/// <reference types="cypress" />
describe('Create session spec', () => {
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
        url: '/api/teacher',
      },
      [
        {
          id: 1,
          lastName: 'DELAHAYE',
          firstName: 'Margot',
          createdAt: '2024-03-19T16:26:48',
          updatedAt: '2024-03-19T16:26:48',
        },
        {
          id: 2,
          lastName: 'THIERCELIN',
          firstName: 'Hélène',
          createdAt: '2024-03-19T16:26:48',
          updatedAt: '2024-03-19T16:26:48',
        },
      ]
    );

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
          description: 't',
          users: [4],
          createdAt: '2024-03-20T15:38:00',
          updatedAt: '2024-03-21T18:03:48',
        },
      ]
    ).as('session');

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );

    cy.url().should('include', '/sessions');
    cy.get('span#createBtn').click();
    cy.contains('Create session').should('be.visible');
    cy.url().should('include', '/create');
  });
  it('Create new session', () => {
    cy.intercept('POST', '/api/session', {
      body: {
        id: 4,
        name: 'Nom de la session',
        date: '2024-04-13T00:00:00.000+00:00',
        teacher_id: 1,
        description: 'fafas',
        users: [],
        createdAt: '2024-04-10T14:54:28.233674',
        updatedAt: '2024-04-10T14:54:28.234113',
      },
    });

    cy.get('input[formControlName=name]').type('Nom de la session');
    cy.get('input[formControlName=date]').type('2024-04-11');
    cy.get('mat-select[formControlName=teacher_id]').click();
    cy.get('mat-option').contains('Margot DELAHAYE').click();
    cy.get('textarea[formControlName=description]').type(
      'Description de la session'
    );
    cy.get('button#formBtn').click();
    cy.url().should('include', '/sessions');
  });
});
