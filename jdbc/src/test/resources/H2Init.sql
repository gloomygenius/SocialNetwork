CREATE TABLE Users (
  id         INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  email      VARCHAR(255) NOT NULL UNIQUE,
  password   VARCHAR(255) NOT NULL,
  male       BOOLEAN      NOT NULL,
  birthday   DATE,
  city       VARCHAR(25),
  university VARCHAR(50),
  team       VARCHAR(25),
  position   VARCHAR(25),
);
INSERT INTO Users (first_name, last_name, email, password, male)
VALUES ('Василий', 'Бобков', 'admin@exam.com', '123456', TRUE);

CREATE TABLE Roles (
  email VARCHAR(255) NOT NULL,
  role  VARCHAR(15)  NOT NULL,
  PRIMARY KEY (email, role),
  FOREIGN KEY (email) REFERENCES Users (email)
);

INSERT INTO Roles (email, role) VALUES ('admin@exam.com', 'admin');