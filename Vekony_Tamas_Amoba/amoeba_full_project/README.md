Amoeba NxM game implementation (Java 21, Maven)
Features included:
- Full game logic (5-in-row win detection)
- VO classes (Player, Move)
- Random AI
- Save/load board to text file
- SQLite persistence for high scores (database.db)
- Unit tests (JUnit5) covering core logic
How to build:
  mvn clean package
Run:
  java -jar target/amoeba-1.0-SNAPSHOT-jar-with-dependencies.jar
