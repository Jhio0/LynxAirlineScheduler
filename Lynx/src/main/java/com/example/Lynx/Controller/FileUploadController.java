package com.example.Lynx.Controller;

import com.example.Lynx.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Autowired
    private PilotService pilotService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private AircraftService aircraftService;

    @Autowired
    private StationService stationService;

    @PostMapping("/excel")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            // Ensure filename is correctly decoded to handle any special characters
            String decodedFileName = URLDecoder.decode(file.getOriginalFilename(), StandardCharsets.UTF_8.name());
            System.out.println("Decoded File Name: " + decodedFileName);

            // Logic to parse the Excel file and save data
            ExcelReader.readExcel(file, pilotService, flightService, aircraftService);

            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("File upload failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}