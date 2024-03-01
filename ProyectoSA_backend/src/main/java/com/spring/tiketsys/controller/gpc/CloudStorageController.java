package com.spring.tiketsys.controller.gpc;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.core.io.buffer.DataBuffer;
import java.io.InputStream;


@RestController
@CrossOrigin
@RequestMapping("/api/cloud-storage")
public class CloudStorageController {

    @Value("${gcs.bucket.name}")
    private String bucketName;

    private Storage storage;

    public CloudStorageController() {
        // Inicializa el cliente de Google Cloud Storage
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    @PostMapping("/uploadFile")
    public Flux<String> uploadFile(@RequestPart("file") FilePart filePart) {
        // Obtener el contenido del archivo como InputStream

        return filePart.content()
                .flatMap(dataBuffer -> {
                    InputStream inputStream = dataBuffer.asInputStream();
                    // Subir el archivo a Google Cloud Storage
                    return Mono.fromCallable(() -> storage.create(
                            BlobInfo.newBuilder("your-bucket-name", "uploaded-files/" + filePart.filename()).build(),
                            inputStream));
                })
                .map(blob -> {
                    // Obtener la URL de acceso al objeto
                    String uri = blob.getMediaLink();
                    // Devolver la URL de acceso al objeto
                    return "File uploaded successfully. Access URL: " + uri;
                });
    }
}
