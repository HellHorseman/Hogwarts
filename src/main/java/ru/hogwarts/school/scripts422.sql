CREATE TABLE People (
                        PersonID SERIAL PRIMARY KEY,
                        Name VARCHAR(50) NOT NULL,
                        Age INT NOT NULL,
                        HasLicense BOOLEAN NOT NULL
);

CREATE TABLE Cars (
                      CarID SERIAL PRIMARY KEY,
                      Brand VARCHAR(50) NOT NULL,
                      Model VARCHAR(50) NOT NULL,
                      Price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE PeopleCars (
                            PersonID INT REFERENCES People(PersonID),
                            CarID INT REFERENCES Cars(CarID),
                            PRIMARY KEY (PersonID, CarID)
);