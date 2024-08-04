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
    last_command_time TIMESTAMP NULL;
    FOREIGN KEY (game_id) REFERENCES game(id)
);

CREATE TABLE command (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    unit_id BIGINT NOT NULL,
    command_time TIMESTAMP NOT NULL,
    command_type VARCHAR(255) NOT NULL,
    direction VARCHAR(255),
    squares INT,
    changes TEXT,
    FOREIGN KEY (unit_id) REFERENCES unit(id)
);