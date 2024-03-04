package com.spring.tiketsys.service;

import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class GCPService {
    @Autowired
    private Storage storage;
    @Value("${gcs.bucket.name}")
    private String bucketName;


    public String uploadSample() throws IOException {
        BlobId id = BlobId.of(bucketName,"archivo_prueba.jpg");
        BlobInfo info = BlobInfo.newBuilder(id).build();
        File file = new File("/home/pharaox/Downloads/gato1.jpg");
        byte[] arr = Files.readAllBytes(Paths.get(file.toURI()));
        storage.create(info, arr);
        return "Aprobado hahahhaha";
    }

    public String uploadFile(FilePart file) throws IOException {
        final String fileName = "uploaded-files/"+UUID.randomUUID()+file.filename(); // Genera un nombre de archivo único
        File mainFile = new File(file.name());
        try (FileOutputStream stream = new FileOutputStream(mainFile)) {
            byte[] arrayBytes = DataBufferUtils.join(file.content())
                    .map(dataBuffer -> dataBuffer.asByteBuffer().array()).block();
            stream.write(arrayBytes);

            //Main file is the one to upload
            storage.create(BlobInfo.newBuilder(bucketName, fileName).build(), arrayBytes);
            String publicLink = storage.get(bucketName, fileName).signUrl(1, TimeUnit.DAYS).toString();
            return publicLink;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

}
