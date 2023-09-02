CREATE TABLE people (
                        person_id SERIAL PRIMARY KEY,
                        name VARCHAR(50) NOT NULL,
                        age INT NOT NULL,
                        has_license BOOLEAN NOT NULL
);

CREATE TABLE cars (
                      car_id SERIAL PRIMARY KEY,
                      brand VARCHAR(50) NOT NULL,
                      model VARCHAR(50) NOT NULL,
                      price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE peopleCars (
                            person_id INT REFERENCES people(person_id),
                            car_id INT REFERENCES cars(car_id),
                            PRIMARY KEY (person_id, car_id)
);