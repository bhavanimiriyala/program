package com.mss.gcp;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GcpStorageController {
	@Autowired
	GcpStorageService gcpStorageService;

	@PostMapping("/uploadFile/{fileName}")
	public String moveFileToBucket(@PathVariable String fileName) throws Exception {
		return gcpStorageService.moveFileToBucket(fileName);
	}

	@GetMapping("/downloadFile/{fileName}")
	public String downloadFileFromBucket(@PathVariable String fileName) throws Exception {
		return gcpStorageService.downloadFileFromBucket(fileName);
	}
	}
