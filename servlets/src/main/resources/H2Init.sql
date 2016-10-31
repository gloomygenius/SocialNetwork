CREATE TABLE Users(
  id         INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  email      VARCHAR(255) NOT NULL,
  password   VARCHAR(255) NOT NULL,
  male       BOOLEAN      NOT NULL,
);
INSERT INTO Users (first_name, last_name, email, password, male)
VALUES ('Василий', 'Bobkov', 'admin@exam.com', '123456', TRUE);

INSERT INTO Users (first_name, last_name, email, password, male)
VALUES ('Иван', 'Иванов', 'user@exam.com', '123456', TRUE);
-- CREATE TABLE Roles (
--   email VARCHAR(255) NOT NULL,
--   role  VARCHAR(15)  NOT NULL,
--   PRIMARY KEY (email, role),
--   FOREIGN KEY (email) REFERENCES Users (email)
-- );
--
-- INSERT INTO Roles (email, role) VALUES ('admin@exam.com', 'admin');