Framework Dependency Kata
-------------------------

The purpose of this refactoring kata is to practice the inverting of framework dependencies in your code.

Frameworks typically make it easy for you to scatter your code with dependencies on it.
Such dependencies may be harmful.
Keeping the Framework out of your model brings several advantages.

- Your code, especially your model becomes more testable.
- Your model becomes cleaner as you keep awkward infrastructure code out of it.
- Tech Upgrades are easy because your model is not changing.
- The Framework is easy to replace.

## Tests

There are integrated tests covering the whole application.
Run them in your IDE or use the `./tdd.sh` script or `tdd` on Windows to run your tests continuously.

## Refactoring Exercises

### Exercise #1: Remove Framework based Authorization from the model

Logic that governs who is allowed to perform which action should be a part of your model, but the framework should not.
Remove framework related auth code like @PreAuthorize annotations from your model while maintaining the actual auth logic within your model.

### Exercise #2: Remove CDI from the model

Get rid of framework related DI Code like @Service and @Autowired annotations in your model.

### Exercise #3: Invert the dependency on the Persistence Framework

Make sure there is no dependency from the model to Hibernate or Spring Data.
Those Frameworks should depend on our model and become a plugin.

### Exercise #4: Reflect on the done refactorings

What steps did you take and would there be an easier way?
What does the result look like? Can you think of a better solution?
Was it worth it? Discuss Advantages as well as Disadvantages.
