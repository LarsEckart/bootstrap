![Test Status](../../workflows/test/badge.svg)

# Blackjack Game

A command-line blackjack implementation built with Java 21, showcasing object-oriented design principles and comprehensive testing.

https://github.com/LarsEckart/bootstrap

## Features

- Complete blackjack game mechanics with proper scoring
- Interactive command-line interface
- Standard blackjack rules:
  - Dealer hits on soft 17
  - Blackjack (21 with 2 cards) beats regular 21
  - Ace can be 1 or 11 (soft/hard hands)
  - Face cards worth 10 points
- Play multiple rounds with option to continue or exit

## Architecture

The implementation follows object-oriented design principles with high cohesion and low coupling:

- **Card**: Immutable card with suit and rank
- **Suit/Rank**: Enums with behavior (blackjack values)
- **Deck**: Manages 52-card deck with shuffling and dealing
- **Hand**: Encapsulates card collection with scoring and comparison logic
- **Score**: Value object handling blackjack scoring rules
- **BlackjackGame**: Game controller managing rounds and player interaction

## Building and Running

### Prerequisites
- Java 21 or higher

### Build the Project
```bash
./gradlew build
```

### Run the Game

**Option 1: Using Gradle**
```bash
./gradlew run
```

**Option 2: Using JAR file**
```bash
./gradlew build
java -jar build/libs/kata.jar
```

### Development Tasks

- **Run tests**: `./gradlew test`
- **Run single test**: `./gradlew test --tests "kata.DeckTest.deck_contains_52_cards"`
- **Format code**: `./gradlew spotlessApply`
- **Static analysis**: `./gradlew spotbugsMain`
- **Mutation testing**: `./gradlew pitest`
- **Full quality check**: `./gradlew check`

## Testing

The project includes comprehensive test coverage with:
- Unit tests for all classes
- Integration tests for game scenarios
- Mutation testing with PiTest
- Static analysis with SpotBugs
- Code formatting with Spotless
