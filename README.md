# Checkout Kata

## Architectural decisions

Parameterised tests: I had the option to implement them in cucumber. For a small applications such
as this one, it would not do any harm. For for real life systems, though, using cucumber to test every single 
variant can degrade build time. That's why I chose to implement such tests as unit tests using JunitParams.
