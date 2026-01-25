# CI/CD Pipeline Setup

This document explains how to configure the CI/CD pipeline for the customer-service microservice.

## Overview

The CI/CD pipeline is configured to:
- **On Pull Request to main/master**: Validate the application build and run SonarCloud code quality analysis with minimum 70% coverage
- **On Merge to main/master**: Build and deploy the customer-service microservice

## GitHub Secrets Configuration

You only need to configure **one secret** in your GitHub repository:

### SONAR_TOKEN
- Your SonarCloud authentication token
- Get it from: SonarCloud → My Account → Security → Generate Token
- Add to: GitHub Repository → Settings → Secrets and variables → Actions → New repository secret

**Note:** The workflow is pre-configured with:
- SonarCloud URL: `https://sonarcloud.io`
- Organization: `ericatici`
- Project Key: `Ericatici_customer-service`

## How to Add the Secret to GitHub

1. Go to your GitHub repository: https://github.com/Ericatici/customer-service
2. Click on **Settings**
3. In the left sidebar, click on **Secrets and variables** → **Actions**
4. Click **New repository secret**
5. Add the secret:
   - Name: `SONAR_TOKEN`
   - Value: Your SonarCloud token
   - Click **Add secret**

## SonarCloud Project Configuration

1. Go to https://sonarcloud.io and log in
2. Your project should already be configured with:
   - Organization: `ericatici`
   - Project Key: `Ericatici_customer-service`
3. Go to **Quality Gates**
4. Create or modify a quality gate to enforce minimum 70% code coverage:
   - Go to Quality Gates → Create or select a gate
   - Add condition: Coverage on New Code ≥ 70%
   - Assign this quality gate to your `Ericatici_customer-service` project
5. Ensure your project is set to analyze the master branch

## Coverage Configuration

The project is configured with:
- **JaCoCo** for code coverage measurement
- **Minimum coverage**: 70% (configured in pom.xml)
- **Excluded from coverage**:
  - Configuration classes (**/config/**)
  - DTOs (**/dto/**)
  - Entities (**/entities/**)
  - Exceptions (**/exceptions/**)
  - Request/Response models (**/requests/**, **/responses/**)

## Workflow Triggers

### Pull Request Workflow
Triggers when:
- A pull request is opened to `main` or `master` branch
- A pull request is updated

Steps:
1. Checkout code
2. Set up Java 17
3. Build with Maven
4. Run tests (using H2 in-memory database)
5. Run SonarCloud analysis with quality gate validation
6. Upload coverage reports as artifacts

### Deployment Workflow
Triggers when:
- Code is merged to `main` or `master` branch

Steps:
1. Checkout code
2. Set up Java 17
3. Build the application with Maven
4. Build Docker image
5. Upload JAR artifacts

## Testing Configuration

Tests are configured to use:
- **H2 in-memory database** instead of MySQL for CI/CD
- **Random port** for web server to avoid conflicts
- **MockMvc** for web layer testing
- **Active profile**: `test`

## Testing the Pipeline

### Test PR Workflow
1. Create a new branch: `git checkout -b feature/test-pipeline`
2. Make a small change
3. Push the branch: `git push origin feature/test-pipeline`
4. Create a pull request to `main` or `master`
5. Check the **Actions** tab in GitHub to see the workflow running

### Test Deployment Workflow
1. Merge the pull request
2. Check the **Actions** tab to see the deployment workflow running

## Troubleshooting

### SonarCloud Analysis Fails
- Verify `SONAR_TOKEN` is correctly set in GitHub Secrets
- Check that the project exists in SonarCloud with key: `Ericatici_customer-service`
- Ensure your SonarCloud token has permission to analyze projects
- Verify you're logged into the `ericatici` organization in SonarCloud

### Coverage Below 70%
- Review the JaCoCo report in the workflow artifacts
- Add more unit tests to increase coverage
- Check that test files are properly located in `src/test/java`

### Tests Fail Locally but Pass in CI
- Ensure you're using the `test` profile: `mvn test -Dspring.profiles.active=test`
- Check that H2 database is in test dependencies
- Verify test configuration in `src/test/resources/application-test.yml`

### Build Fails
- Ensure the pom.xml is valid
- Check that all dependencies are available
- Verify Java version is 17

## Local Testing

To test the build and coverage locally:

```bash
# Run tests with coverage
mvn clean test

# Generate coverage report
mvn jacoco:report

# View coverage report
open target/site/jacoco/index.html

# Run SonarCloud analysis locally
mvn sonar:sonar \
  -Dsonar.login=YOUR_SONAR_TOKEN
```
