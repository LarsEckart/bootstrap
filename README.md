![Test Status](../../workflows/test/badge.svg)

#  TODO
# question: who owns the validation logic around game events?

## Next
* wrap List<Player> players in Skyjo
* test on Player to test that isFinishedPlaying
* test on player when calling swapLastRound that they are finished
* reorder methods in Player to make it more obvious what are the 2 actions they take

## Later
* move code to main folder
* shuffle the deck
* does Position need more input validation?
* print unflipped card scores (debug mode?)
* 
* handle invalid input. player cannot flip card that is already flipped




## Done
* three-in-a-row logic
* two methods for flipping card: one advances player/one does not


# Retro

pattern
emotions
  few confusing moments when determining last round
    design smell that we had to add it in two places?
tools
  cmd z to revert
  EqualsVerifier
team
domain
language
  static fields are global and thus shared across tests
events
surprises
  are we really done now??? :D
