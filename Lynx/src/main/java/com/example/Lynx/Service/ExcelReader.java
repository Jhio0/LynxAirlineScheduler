package com.example.Lynx.Service;

import com.example.Lynx.DTO.AircraftDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.Lynx.DTO.FlightDTO;
import com.example.Lynx.DTO.PilotDTO;

import java.io.InputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;

public class ExcelReader {
    // Define the date format to match the Excel format
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static void readExcel(MultipartFile file, PilotService pilotService, FlightService flightService, AircraftService aircraftService) throws IOException {
        try (InputStream is = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate through the rows of the Excel file
            PilotDTO pilotDTO = new PilotDTO();
            pilotDTO.setPassword("Something");
            pilotDTO.setName("jhio");
            pilotDTO.setEmail("sorianojhio218@gmail.com");
            pilotDTO.setRank("Captain");

            PilotDTO savedPilot = pilotService.savePilot(pilotDTO);
            Long pilotID = savedPilot.getPilotID();  // Get the updated pilotID
            System.out.println("Pilot ID after save: " + pilotID);
            if (savedPilot == null || savedPilot.getPilotID() == null) {
                throw new IllegalStateException("Pilot save failed or pilotID is null.");
            }
            System.out.println("Saved Pilot ID: " + savedPilot.getPilotID());

            // Validate before saving flight
            if (pilotID == null) {
                throw new IllegalStateException("Pilot ID is null. Cannot save flight.");
            }

            // Log FlightDTO details



            for (Row row : sheet) {
                if (row.getRowNum() < 2) {
                    // Skip the header row
                    continue;
                }

                System.out.println("Row " + row.getRowNum() + " column 0 value: " + row.getCell(1));
                // Create and save the Pilot (without pilotId, it will be auto-generated)


                if (pilotID == null) {
                    throw new IllegalStateException("Pilot ID should not be null after saving the pilot.");
                }


                //flight data
                Double flightDateString = row.getCell(1).getNumericCellValue();
                LocalDate flightDate = convertToLocalDate(flightDateString);

                String pairingActivity = row.getCell(2).getStringCellValue();

                String dutyReportTimeString =  row.getCell(3).getStringCellValue();
                LocalTime dutyReportTime = convertToLocalTime(dutyReportTimeString);
                System.out.println("dutyReportTime: " + dutyReportTime);

                String aircraft =  row.getCell(5).getStringCellValue();

                String departureStationString = row.getCell(8).getStringCellValue();
                String departureStation = extractLocation(departureStationString);

                String arrivalStationString = row.getCell(10).getStringCellValue();
                String arrivalStation = extractLocation(arrivalStationString);

                String dutyDebriefEndString = row.getCell(11) != null ? row.getCell(11).getStringCellValue() : null;
                LocalTime dutyDebriefEnd = null;

                if (dutyDebriefEndString != null && !dutyDebriefEndString.isEmpty()) {
                    dutyDebriefEnd = convertToLocalTime(dutyDebriefEndString);
                } else {
                    // Set to a default value (null or LocalTime.MIDNIGHT)
                    dutyDebriefEnd = null;  // or LocalTime.MIDNIGHT, depending on your needs
                }
                System.out.println("dutyDebriefEnd: " + dutyDebriefEnd);

                String flyingHoursString = row.getCell(12) != null ? row.getCell(12).getStringCellValue() : null;
                LocalTime flyingHoursTime = null;

                if (flyingHoursString != null && !flyingHoursString.isEmpty()) {
                    flyingHoursTime = convertToLocalTime(flyingHoursString);
                } else {
                    // Optionally set a default time value (e.g., LocalTime.MIDNIGHT)
                    flyingHoursTime = LocalTime.MIDNIGHT;  // or leave it as null if preferred
                }
                System.out.println("flyingHoursTime: " + flyingHoursTime);

                String dutyHoursString = row.getCell(13) != null ? row.getCell(13).getStringCellValue() : null;
                LocalTime dutyHoursTime = null;

                if (dutyHoursString != null && !dutyHoursString.isEmpty()) {
                    dutyHoursTime = convertToLocalTime(dutyHoursString);
                } else {
                    // Optionally set a default time value (e.g., LocalTime.MIDNIGHT)
                    dutyHoursTime = LocalTime.MIDNIGHT;  // or leave it as null if preferred
                }

                System.out.println("dutyHoursTime: " + dutyHoursTime);
                String acType =  row.getCell(15).getStringCellValue();

                String hotel = row.getCell(16).getStringCellValue();

                String updatedBy = row.getCell(17).getStringCellValue();

                Double updatedDateString = row.getCell(18).getNumericCellValue();
                LocalDate updatedDate = convertToLocalDate(updatedDateString);

                AircraftDTO aircraftDTO = new AircraftDTO();
                aircraftDTO.setAircraftID(aircraft);
                aircraftDTO.setAcType(acType);
                aircraftService.saveAircraft(aircraftDTO);
                System.out.println("Saving AircraftDTO: " + aircraftDTO);

                FlightDTO flightDTO = new FlightDTO();
                // Get the saved PilotDTO
                flightDTO.setPilotID(pilotID);
                flightDTO.setAircraftID(aircraft);
                flightDTO.setDate(flightDate);
                flightDTO.setPairingActivity(pairingActivity);
                flightDTO.setDutyReportTime(dutyReportTime);
                flightDTO.setDepartureStation(departureStation);
                flightDTO.setArrivalStation(arrivalStation);
                flightDTO.setDutyDebriefEnd(dutyDebriefEnd);
                flightDTO.setFlyingHours(flyingHoursTime);
                flightDTO.setDutyHours(dutyHoursTime);
                flightDTO.setHotel(hotel);
                flightDTO.setUpdatedBy(updatedBy);
                flightDTO.setUpdatedDate(updatedDate);

                flightService.saveFlight(flightDTO);
                System.out.println("Saving FlightDTO: " + flightDTO);
            }
        }

    }
    // Helper method to convert the string date into LocalDate
    private static LocalDate convertToLocalDate(Double excelDate) {
        if (excelDate == null) {
            return null;  // Return null if the Excel date is missing
        }
        // Excel stores dates as days since 1900-01-01 (with 1900-02-29 bug adjustment)
        return LocalDate.of(1900, 1, 1).plusDays(excelDate.longValue() - 2);
    }

    // Helper method to convert time string to LocalTime
    private static LocalTime convertToLocalTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            return null;  // Return null if the time string is missing or empty
        }

        // Handle cases like "0:00" by normalizing the input if needed
        String normalizedTimeStr = normalizeTimeString(timeStr);

        try {
            // Parse the normalized time string
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm"); // Supports single-digit hour
            return LocalTime.parse(normalizedTimeStr, timeFormatter);
        } catch (Exception e) {
            // If there's an error parsing, return null
            return null;
        }
    }

    private static String normalizeTimeString(String timeStr) {
        return timeStr != null ? timeStr.trim() : null;  // Ensure it returns null if timeStr is null
    }

    // Helper method to extract location from string like "YYC 18:51"
    private static String extractLocation(String locationWithTime) {
        if (locationWithTime == null || locationWithTime.trim().isEmpty()) {
            return null;  // Return null if the location string is missing or empty
        }

        if (locationWithTime.contains(" ")) {
            return locationWithTime.split(" ")[0];  // Extract the first part before the space
        }
        return locationWithTime;  // If no space is found, return the whole string
    }
}