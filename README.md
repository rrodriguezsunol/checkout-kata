# Checkout Kata

This is my solution to the checkout kata.

## Considerations

I haven't added unit tests for the edge cases of integer overflow when we calculate the total amount
as it felt like wrapping it too much in cotton wool for this exercise.

SpecialOffersProcessor returns a list of Special offers but in it could as well return the total amount to be discounted.
I modeled it this way thinking of the printed out ticket where you see all the offers being applied.

I separated the pricing list in two:
* A list of items provided by the ItemRepository.
* A list of applicable offers that the MultibuySpecialOffersProcessor has. Each offer is applied to an item.

It felt like two separate concerns to me.

