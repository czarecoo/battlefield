CREATE TABLE game (
    id INT AUTO_INCREMENT PRIMARY KEY,
    board_width INT NOT NULL,
    board_height INT NOT NULL
);

CREATE TABLE unit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    position_x INT NOT NULL,
    position_y INT NOT NULL,
    type VARCHAR(255) NOT NULL,
    move_count INT NOT NULL,
    color VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    game_id INT,
    FOREIGN KEY (game_id) REFERENCES game(id)
);

CREATE TABLE archer (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES unit(id)
);

CREATE TABLE transport (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES unit(id)
);

CREATE TABLE cannon (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES unit(id)
);

CREATE TABLE command (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    unit_id BIGINT,
    command_type VARCHAR(255) NOT NULL,
    parameters VARCHAR(255),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (unit_id) REFERENCES unit(id)
);