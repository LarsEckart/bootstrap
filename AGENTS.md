
This project uses Java 25 and Gradle.

Since we use java 25, here is a list of new features that are now available and valid java syntax:

### New Java Language Features
- **Pattern Matching for switch** (Java 21) - Exhaustive switch with type patterns
- **Record Patterns** (Java 21) - Deconstruct records in patterns: `if (obj instanceof Point(int x, int y))`
- **Sequenced Collections** (Java 21) - `SequencedCollection`, `SequencedSet`, `SequencedMap` with `getFirst()`, `getLast()`, `reversed()`
- **Virtual Threads** (Java 21) - Lightweight threads: `Thread.ofVirtual().start(runnable)`
- **Unnamed Variables & Patterns** (Java 22) - Use `_` for unused variables: `case Point(int x, _)`
- **Stream Gatherers** (Java 24) - Custom intermediate stream operations via `stream.gather()`
- **Class-File API** (Java 24) - Read/write class files without ASM
- **Scoped Values** (Java 25) - Thread-local alternative for virtual threads
- **Compact Source Files & Instance Main** (Java 25) - Simpler main: `void main() { }`
- **Flexible Constructor Bodies** (Java 25) - Statements before `super()` call
