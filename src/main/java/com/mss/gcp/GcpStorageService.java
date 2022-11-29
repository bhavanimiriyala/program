package com.mss.gcp;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GcpStorageService {
	@Value("bucketname")
	String bucketName;
//	    @Value("subdirectory")
//	    String subdirectory;

//	Storage storage = StorageOptions.getDefaultInstance().getService();
	@Autowired
	Storage storage;

	public String moveFileToBucket(String fileName) throws IOException {
		// BlobId is a combination of bucketName + subdirectiory(optional) + fileName
		BlobId blobId = BlobId.of("mss-gcp-trainbucket", fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		File file = new File("/home/training/Documents/", fileName);
		// convert the file to byte array
		byte[] data = Files.readAllBytes(Paths.get(file.toURI()));
		// Upload the blob to GCS
		storage.create(blobInfo, data);
		return "File uploaded successfully to GCP";
	}

	public String downloadFileFromBucket(String fileName) {
		try {
			String bucketName = "mss-gcp-trainbucket";
			String file = fileName;
			log.info("Attempting to download file {} from bucket {}",
					"/home/training/Downloads/gcp-bucket-accessing-25e39635ef42.json", bucketName);
			Blob blob = storage.get(bucketName, file);
			if (Objects.isNull(blob)) {
				log.error("file not present for {}", file);
			}
			System.out.println("Blob Response:" + blob);
			String contentString = new String(blob.getContent());
			return contentString ;
     } catch (Exception ex) {
			ex.printStackTrace();
		}
		return "File downloaded";
	}
		
}
