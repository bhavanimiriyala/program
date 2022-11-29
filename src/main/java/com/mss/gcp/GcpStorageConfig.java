package com.mss.gcp;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GcpStorageConfig {
//	@Value("${spring.cloud.gcp.credentials.location}")
//	private Resource key;

	private GoogleCredentials gcsCredentials() throws IOException {
		return GoogleCredentials
				.fromStream(new FileInputStream("/home/training/Downloads/gcp-bucket-accessing-25e39635ef42.json"));
	}

	@Bean
	public Storage storage() {
        Storage storage = null;
		try {
			storage = StorageOptions.newBuilder().setCredentials(gcsCredentials()).build().getService();
		} catch (FileNotFoundException fe) {
			log.error("Bucket key file not found. Failing silently", fe);
		} catch (StorageException | IOException e) {
			log.error("Exception", e);
		}
		return storage;
	}
}
