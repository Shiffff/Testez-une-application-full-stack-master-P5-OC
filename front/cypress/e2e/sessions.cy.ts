/// <reference types="cypress" />
describe('Sessions spec admin', () => {
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
  });

  afterEach(() => {
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
    cy.get('span#sessionsBtn').click();
    cy.url().should('include', '/sessions');
  });

  it('Navigates to "/me" after clicking account button', () => {
    cy.intercept('GET', '/api/user/1', {
      body: {
        admin: true,
        createdAt: '2024-03-19T16:26:48',
        email: 'yoga@studio.com',
        firstName: 'Admin',
        id: 1,
        lastName: 'Admin',
        updatedAt: '2024-03-19T16:26:48',
      },
    });

    cy.get('span#accountBtn').click();
    cy.contains('yoga@studio.com').should('be.visible');
    cy.url().should('include', '/me');
  });
  it('Navigates to "/create" after clicking create button', () => {
    cy.get('span#createBtn').click();
    cy.contains('Create session').should('be.visible');
    cy.url().should('include', '/create');
  });

  it('Navigates to "" after clicking logout button and connect again', () => {
    cy.get('span#logoutBtn').click();
    cy.url().should('include', '');
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
  });
  it('Navigates to "/update" after clicking create button', () => {
    cy.get('span#editBtn').click();
    cy.contains('Update session').should('be.visible');
    cy.url().should('include', '/update');
  });
  it('Navigates to "/details" after clicking details button', () => {
    cy.intercept(
      {
        method: 'GET',
        url: '/api/teacher/2',
      },
      [
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
        url: '/api/session/2',
      },
      {
        id: 2,
        name: 'test',
        date: '2024-03-24T00:00:00.000+00:00',
        teacher_id: 2,
        description: 't',
        users: [4],
        createdAt: '2024-03-20T15:38:00',
        updatedAt: '2024-03-21T18:03:48',
      }
    );

    cy.get('span#detailBtn').click();
    cy.contains('Delete').should('be.visible');
    cy.contains('Description').should('be.visible');

    cy.url().should('include', '/detail');
  });
});
/// <reference types="cypress" />
describe('Sessions spec user', () => {
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
  });

  it('create and edit isn t visible', () => {
    cy.contains('span#createBtn', { matchCase: false }).should('not.exist');
    cy.contains('span#editBtn', { matchCase: false }).should('not.exist');
  });
});
