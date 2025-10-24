#drop database springsteenesp
CREATE DATABASE IF NOT EXISTS springsteenesp;
USE springsteenesp;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

INSERT INTO users (username, email, password)
VALUES ('Christian', 'brucechristian@gmail.com', '12345');

CREATE TABLE concerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    city VARCHAR(100),
    venue VARCHAR(100),
    tour VARCHAR(100),
    duration VARCHAR(20) -- por ejemplo "2h 30m"
);

-- Ejemplo de inserción de un concierto
INSERT INTO concerts (date, city, venue, tour, duration)
VALUES ('2025-06-01', 'Madrid', 'Wanda Metropolitano', 'Springsteen Tour', '3h 0m');


CREATE TABLE songs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    concert_id INT NOT NULL,
    position INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    FOREIGN KEY (concert_id) REFERENCES concerts(id) ON DELETE CASCADE
);

-- Ejemplo de inserción de canciones ordenadas
INSERT INTO songs (concert_id, position, title)
VALUES
(1, 1, 'Born to Run'),
(1, 2, 'Thunder Road'),
(1, 3, 'Dancing in the Dark');
