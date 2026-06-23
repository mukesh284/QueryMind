# Contributing to QueryMind

Thank you for your interest in contributing to QueryMind! This document provides guidelines and instructions for contributing.

## Code of Conduct

- Be respectful and inclusive
- Focus on constructive feedback
- Help others learn and grow
- Report issues through proper channels

## How to Contribute

### Reporting Bugs

1. Check existing issues first
2. Provide detailed description of the bug
3. Include steps to reproduce
4. Share your environment details (Java version, OS, etc.)
5. Attach relevant logs or screenshots

### Suggesting Features

1. Describe the feature clearly
2. Explain the use case and benefits
3. Provide examples if applicable
4. Consider existing functionality first

### Submitting Code Changes

1. **Fork the repository**
   ```bash
   git clone https://github.com/yourusername/QueryMind.git
   cd QueryMind
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes**
   - Follow code style guidelines
   - Write clean, readable code
   - Add comments for complex logic
   - Update documentation

4. **Run tests**
   ```bash
   mvn clean test
   ```

5. **Commit your changes**
   ```bash
   git commit -m "Add your descriptive commit message"
   ```

6. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```

7. **Open a Pull Request**
   - Provide clear description of changes
   - Link related issues
   - Request review from maintainers

## Development Setup

### Prerequisites
- Java 11+
- Maven 3.6+
- Git

### Environment Setup
```bash
# Clone repository
git clone https://github.com/yourusername/QueryMind.git
cd QueryMind

# Set environment variables
export AI_API_KEY=your-test-key
export JAVA_HOME=/path/to/java

# Build project
mvn clean install

# Run tests
mvn test

# Start application
mvn spring-boot:run
```

## Code Style Guidelines

### Java Code Style
- Use meaningful variable and method names
- Maximum line length: 120 characters
- Use 4 spaces for indentation
- Always use curly braces, even for single statements

### Example
```java
// Good
public String generateSQL(String userQuery, String schema) {
    if (userQuery == null || userQuery.isEmpty()) {
        throw new IllegalArgumentException("Query cannot be empty");
    }
    return buildSQL(userQuery, schema);
}

// Avoid
public String generateSQL(String q, String s){
    if(q==null||q.isEmpty())throw new IllegalArgumentException("empty");
    return buildSQL(q,s);
}
```

### Naming Conventions
- Classes: PascalCase (e.g., `QueryService`)
- Methods: camelCase (e.g., `generateSQL`)
- Constants: UPPER_SNAKE_CASE (e.g., `MAX_TOKENS`)
- Private variables: camelCase with underscore (e.g., `_privateVar`)

## Testing Requirements

All pull requests must include:
1. Unit tests for new functionality
2. Integration tests for API changes
3. Test coverage ≥ 80%
4. All tests must pass

### Writing Tests
```java
@Test
public void testGenerateSQL_WithValidInput_ReturnsSQL() {
    // Arrange
    String query = "Show all customers";
    
    // Act
    String result = queryService.generateSQL(query, schema);
    
    // Assert
    assertNotNull(result);
    assertTrue(result.toUpperCase().contains("SELECT"));
}
```

## Documentation

### Update Documentation For:
- New features
- API endpoint changes
- Configuration options
- Deployment procedures
- Bug fixes affecting users

### Documentation Files
- `README.md` - Main documentation
- `QUICKSTART.md` - Getting started guide
- `DEPLOYMENT.md` - Deployment guide
- `examples/API-EXAMPLES.md` - API examples
- Javadoc comments for public methods

## Commit Message Guidelines

```
[TYPE] Subject (50 chars max)

Detailed description (72 chars per line)
- List key changes
- Reference issues with #123
- Explain the why, not the what

Fixes: #123
Closes: #456
```

### Types
- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation
- `style:` Code style changes
- `refactor:` Code refactoring
- `test:` Test additions
- `chore:` Build/dependency changes

## Pull Request Process

1. **Before submitting:**
   - Run `mvn clean install`
   - Run `mvn test`
   - Update `README.md` if needed
   - Check for code style issues

2. **PR Description should include:**
   - What problem does this solve?
   - How was it solved?
   - Any breaking changes?
   - Testing performed?

3. **Review process:**
   - Maintainers will review your PR
   - Address feedback and suggestions
   - Push additional commits as needed
   - PR will be merged once approved

## Branch Naming Convention

```
feature/description          - New feature
fix/issue-description        - Bug fix
docs/update-description      - Documentation
refactor/component-name      - Refactoring
test/feature-description     - Testing
```

## Review Checklist

Before submitting a PR, verify:
- [ ] Code follows style guidelines
- [ ] All tests pass: `mvn test`
- [ ] Build succeeds: `mvn clean install`
- [ ] Documentation updated
- [ ] No hardcoded values or secrets
- [ ] Error handling included
- [ ] Logging added for debugging
- [ ] No breaking changes (or documented)

## Areas for Contribution

### High Priority
- [ ] Additional AI provider support (Claude, Gemini)
- [ ] Web UI dashboard
- [ ] Advanced formula templates
- [ ] Performance optimizations
- [ ] Security enhancements

### Medium Priority
- [ ] Multi-language support
- [ ] Mobile app
- [ ] Batch query optimization
- [ ] Advanced caching
- [ ] Analytics dashboard

### Low Priority
- [ ] UI theme options
- [ ] Export features
- [ ] Additional example schemas
- [ ] Blog posts/tutorials

## Questions or Need Help?

- Create a GitHub Discussion
- Open an issue with the question label
- Email: support@querymind.ai
- Check existing documentation first

---

Thank you for contributing to QueryMind! 🎉

