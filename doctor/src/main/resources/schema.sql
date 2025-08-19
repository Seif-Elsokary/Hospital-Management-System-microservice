CREATE TABLE IF NOT EXISTS doctors (
    doctor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    age INT NOT NULL CHECK (age BETWEEN 25 AND 80),
    year_of_experience INT NOT NULL CHECK (year_of_experience >= 0),
    specialty VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    gender VARCHAR(50) NOT NULL
);
