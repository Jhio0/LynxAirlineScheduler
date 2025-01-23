INSERT INTO Pilot (Name, Email, Password, Rank)
VALUES ('John Doe', 'johndoe@example.com', 'hashedpassword123', 'Captain');

INSERT INTO Aircraft (AC_Type)
VALUES ('Boeing 737');

-- Insert data into Flight table
INSERT INTO Flight (PilotID, AircraftID, Date, PairingActivity, DutyReportTime, DEPSTN, ARRSTN, DutyDebriefEnd, FlyingHours, DutyHours, Hotel, UpdatedBy, File)
VALUES (
    (SELECT PilotID FROM Pilot WHERE Email = 'johndoe@example.com'), -- Use Pilot's generated ID
    (SELECT AircraftID FROM Aircraft WHERE AC_Type = 'Boeing 737'), -- Use Aircraft's generated ID
    '2024-11-15',           -- Date
    'Routine Check',        -- PairingActivity
    '06:00:00',             -- DutyReportTime
    'S001',                 -- DEPSTN (Departure Station)
    'S002',                 -- ARRSTN (Arrival Station)
    '12:30:00',             -- DutyDebriefEnd
    '06:30:00',             -- FlyingHours
    '08:00:00',             -- DutyHours
    'Hilton NY Downtown',   -- Hotel
    'admin',                -- UpdatedBy
    '/files/flight001.pdf'  -- File
);
