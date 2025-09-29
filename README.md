# Spring Boot + GraphQL

Manage User and their Roles using Spring for GraphQL API backend

## Technologies Used
- **Spring Boot 2.7.18**
- **Spring for GraphQL** (migrated from GraphQL Java Tools)
- **Spring Data JPA**
- **H2 Database** (default) / MySQL (configurable)
- **GraphiQL** interface for testing

## Features
- User management (create, read, update, delete)
- Role management (create, read)
- Many-to-many relationship between users and roles
- GraphQL API with mutations and queries
- Pagination support for listing users and roles

## Run Spring Boot application

```bash
./mvnw spring-boot:run
```

The application will start on port 9090.

## Access GraphiQL

Once the application is running, you can access the GraphiQL interface at:
```
http://localhost:9090/graphiql
```

## GraphQL Endpoint

The GraphQL endpoint is available at:
```
http://localhost:9090/graphql
```

## Example Queries and Mutations

### Create a Role
```graphql
mutation {
  createRole(roleName: "ADMIN", description: "Administrator role") {
    id
    roleName
    description
  }
}
```

### Create a User
```graphql
mutation {
  createUser(
    username: "john_doe"
    email: "john@example.com"
    firstName: "John"
    lastName: "Doe"
    roleIds: [1]
  ) {
    id
    username
    email
    firstName
    lastName
    roles {
      id
      roleName
      description
    }
  }
}
```

### Query Users
```graphql
query {
  findAllUsers(page: 0, size: 10) {
    id
    username
    email
    firstName
    lastName
    roles {
      id
      roleName
      description
    }
  }
  countUsers
}
```

### Query Roles
```graphql
query {
  findAllRoles(page: 0, size: 10) {
    id
    roleName
    description
  }
  countRoles
}
```

## Migration Notes

This project has been migrated from GraphQL Java Tools to **Spring for GraphQL**:

- **Old**: GraphQL Java Tools with `GraphQLQueryResolver` and `GraphQLMutationResolver` interfaces
- **New**: Spring for GraphQL with `@QueryMapping` and `@MutationMapping` annotations in `@Controller` classes
- **Schema**: Consolidated from separate `.graphqls` files to a single `schema.graphqls`
- **Configuration**: Simplified configuration using Spring Boot auto-configuration
- **Dependencies**: Updated to use `spring-boot-starter-graphql` instead of legacy GraphQL Java Tools libraries

The migration maintains all existing functionality while providing better integration with the Spring ecosystem and improved performance.