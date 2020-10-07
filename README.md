Framework Dependency Kata
-------------------------

The purpose of this refactoring kata is to practice the inverting of framework dependencies in your code.

Frameworks typically make it easy for you to scatter your code with dependencies on them.
Such dependencies may be harmful if they reside in your model.
Keeping the framework out of your model can be advantageous.

- Your code, especially your model becomes more testable.
- It allows you to unittest your model without the framework. Those tests are much faster.
- Your model becomes cleaner as you keep awkward infrastructure code out of it.
- Tech Upgrades are easy because your model is not changing.
- The Framework becomes a detail that is easy to replace.

## Tests

There are integrated tests covering the whole application.
Run them in your IDE or use the `./tdd.sh` script or `tdd` on Windows to run your tests continuously.

## Refactoring Exercises

The model package contains all the business logic and is scattered with framework dependencies.
Your task is to remove or invert them, so the model does not depend on the framework at all.
You are allowed to create new Infrastructure code like @Configuration classes that depend on your model, but the model should never depend back on them.

### Exercise #1: Remove CDI from the model

Get rid of framework related DI Code like @Service and @Autowired annotations in your model.

### Exercise #2: Make sure the model has no dependency on the web

Remove or invert any dependency the model has on the web such as implementation specifics like http.
Make the web a detail, it should be easy to add a CLI.

### Exercise #3: Remove Framework based authorization from the model

Logic that governs who is allowed to perform which action should be a part of your model, but the framework should not.
Remove framework related auth code like @PreAuthorize annotations from your model while maintaining the actual auth logic within your model.

### Exercise #4: Invert the dependency on the persistence framework

Invert the dependencies the model has on Hibernate or Spring Data.
Make the persistence implementation a detail, it should be easy to test the model without a database.

### Exercise #5: Remove the Dependency on Scheduling Infrastructure from the model

Make sure the model does not depend on @Scheduled. 
Keep the actual report job within in the model while moving the trigger outside.

### Bonus Exercise #6: Get rid of @Transactional

Replace the @Transactional annotation with a monadic transaction implementation and avoid the deep callstack caused by the cglib proxy.

## Reflect on the done refactorings

What steps did you take and would there be an easier way?
What does the result look like? Can you think of a better solution?
Was it worth it? Discuss Advantages as well as Disadvantages.

