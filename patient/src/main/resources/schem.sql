CREATE TABLE IF NOT EXISTS patients (
    patient_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL CHECK (age >= 0),
    phone VARCHAR(11) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    disease VARCHAR(255) NOT NULL,
    blood_type VARCHAR(10) NOT NULL,
    date_of_registration DATETIME,
    gender VARCHAR(10) NOT NULL
);
