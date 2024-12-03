-- Create the Pilot table with a random 4-digit integer PilotID
CREATE TABLE Pilot (
    PilotID INTEGER PRIMARY KEY DEFAULT (floor(random() * 9000 + 1000)::int),
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL, -- Store hashed passwords
    Rank VARCHAR(50)
);

-- Create the Aircraft table with AircraftID as VARCHAR
CREATE TABLE Aircraft (
    AircraftID VARCHAR(10) PRIMARY KEY DEFAULT (floor(random() * 9000 + 1000)::int::text),  -- Altered to be a string
    AC_Type VARCHAR(50) NOT NULL
);



-- Create the Flight table with a random 4-digit integer FlightID
CREATE TABLE Flight (
    FlightID INTEGER PRIMARY KEY DEFAULT (floor(random() * 9000 + 1000)::int),
    PilotID INTEGER NOT NULL,
    AircraftID VARCHAR(10) NOT NULL,  -- Altered to match AircraftID as VARCHAR
    Date DATE NOT NULL,
    PairingActivity VARCHAR(100),
    DutyReportTime TIME,
    DEPSTN VARCHAR(10) NOT NULL, -- Foreign key for departure station
    ARRSTN VARCHAR(10) NOT NULL, -- Foreign key for arrival station
    DutyDebriefEnd TIME,
    FlyingHours TIME,
    DutyHours TIME,
    Hotel VARCHAR(100),
    UpdatedBy VARCHAR(50) NOT NULL,
    UpdatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    File VARCHAR(255),
    
    -- Define foreign keys and cascade rules
    FOREIGN KEY (PilotID) REFERENCES Pilot(PilotID) ON DELETE CASCADE,
    FOREIGN KEY (AircraftID) REFERENCES Aircraft(AircraftID) ON DELETE CASCADE,  -- Altered to match AircraftID as VARCHAR
);
