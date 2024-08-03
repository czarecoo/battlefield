CREATE TABLE game (
    id INT AUTO_INCREMENT PRIMARY KEY,
    board_width INT NOT NULL,
    board_height INT NOT NULL
);

CREATE TABLE unit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    position_x INT NOT NULL,
    position_y INT NOT NULL,
    type VARCHAR(255) NOT NULL,
    move_count INT NOT NULL,
    color VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    game_id INT,
    FOREIGN KEY (game_id) REFERENCES game(id)
);