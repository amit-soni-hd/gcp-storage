package com.example.gcp.gcpstorage.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
public class GCPStorageService {

    @Autowired
    private Storage storage;

    @Value("${gcp.bucket.name}")
    private String bucketName;

    public String uploadFile() {
        String data = "hello i am testing gcp bucket";
        BlobId blobId = BlobId.of(bucketName, "temp-file.txt");
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        byte[] arr = data.getBytes(StandardCharsets.UTF_8);
        storage.create(blobInfo, arr);
        return Arrays.toString(arr);
    }

    public String getFileData() {
        BlobId blobId = BlobId.of(bucketName, "temp-file.txt");
        Blob blob = storage.get(blobId);
        if(blob == null)
            return "Blob Not found";
        return Arrays.toString(blob.getContent());
    }

    public String addDataInExistingFile() {
        BlobId blobId = BlobId.of(bucketName, "temp-file.txt");
        Blob blob = storage.get(blobId);
        if(blob == null)
            return "Blob Not found";
        try (WritableByteChannel channel = blob.writer()) {
            channel.write(ByteBuffer.wrap("add some more data".getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Arrays.toString(blob.getContent());
    }
}
