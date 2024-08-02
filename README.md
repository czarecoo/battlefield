# Battle Simulation Server Application

## Game Assumptions/Rules:
- **Battlefield:** A chessboard with dimensions defined in the configuration
- **Opponents:** Two opponents: white and black
- **Unit Positioning:** Only one vehicle/unit can occupy a square at a time
- **Unit Types:**
  - **Archer:** Possible commands: shoot n squares left/right/down/up; move one square: up/down/left/right
  - **Transport:** Possible commands: move 1, 2, or 3 squares: up/down/left/right
  - **Cannon:** Possible commands: shoot n squares left/right and m squares up/down - can shoot diagonally
- **API Command Execution:** Players can issue commands through the WebService API with a minimum time interval from the last command
- **Command Intervals:**
  - Archer move: 5s
  - Transport move: 7s
  - Archer shot: 10s
  - Cannon shot: 13s
- **Hit Mechanics:**
  - If a shot hits a unit (enemy or own), the unit is destroyed
  - If a vehicle moves onto an enemy unit, the enemy unit is destroyed
  - A vehicle cannot move onto its own unit; if attempted, the vehicle remains in place but the time interval for the next command is still applied

## Game Start:
- Units are randomly placed on the board according to the configuration (e.g., 4 archers, 2 cannons, 5 transports)

## WebService Commands:
- List units (with details: unit ID, position, unit type, status - destroyed or active, number of moves, etc.)
- Command a unit with specific details (e.g., where to shoot, where to move)
- Command a unit with a random move (the server application executes a random command)
- New game - creates a new game, removing the previous one

## API and Application Assumptions:
- For each API method (except for the new game command), one of the input details specifies whether the function pertains to white or black
- Data-returning functions (e.g., unit list) return data in JSON
- Both opponents can issue commands at any time (observing the specified time intervals), i.e., commands can be issued "simultaneously" - concurrency!
- Command execution is atomic
- The application saves the current state and the history of commands and unit positions for the current and each previously created game

## Technology:
- Java Spring
- REST API
- JPA
- Database - any (can be in-memory)
- Maven or Gradle project
- Unit tests
- OOP

*If any assumptions are "fluid," any scenario can be assumed.*
