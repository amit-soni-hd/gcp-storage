package com.example.gcp.gcpstorage.controller;

import com.example.gcp.gcpstorage.service.GCPStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gcp")
public class GCPStorageController {

    @Autowired
    private GCPStorageService gcpStorageService;


    @PostMapping("/sendData")
    public ResponseEntity<?> uploadData() {
        return ResponseEntity.status(HttpStatus.OK).body(gcpStorageService.uploadFile());
    }

    @GetMapping("/fetchData")
    public ResponseEntity<?> fetchData() {
        return ResponseEntity.status(HttpStatus.OK).body(gcpStorageService.getFileData());
    }

    @PatchMapping("/addData")
    public ResponseEntity<?> addData() {
        return ResponseEntity.status(HttpStatus.OK).body(gcpStorageService.addDataInExistingFile());
    }
}
