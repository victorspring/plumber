DROP TABLE IF EXISTS house;
DROP TABLE IF EXISTS plumber;

CREATE TABLE plumber
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50) NOT NULL,
    last_name   VARCHAR(50) NOT NULL
);

CREATE TABLE house
(
    id         BIGSERIAL PRIMARY KEY,
    street     VARCHAR(100) NOT NULL,
    building   VARCHAR(100) NOT NULL,
    zip_code   VARCHAR(10) NOT NULL,
    plumber_id BIGINT REFERENCES plumber (id) ON DELETE SET NULL
);