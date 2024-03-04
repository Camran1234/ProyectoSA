package com.spring.tiketsys.controller.gpc;

import com.google.cloud.storage.*;
import com.spring.tiketsys.security.entity.Message;
import com.spring.tiketsys.service.GCPService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.core.io.buffer.DataBuffer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@CrossOrigin
@RequestMapping("/api/cloud-storage")
public class CloudStorageController {

    @Autowired
    GCPService gcpService;

//    public CloudStorageController() {
//        // Inicializa el cliente de Google Cloud Storage
//        this.storage = StorageOptions.getDefaultInstance().getService();
//    }

    @GetMapping("/send-data")
    public String sendData() throws IOException{
        gcpService.uploadSample();
        return "File created of gatitos";
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestPart("file") FilePart filePart) {
        // Obtener el contenido del archivo como InputStream
        try {
            String url = gcpService.uploadFile(filePart);
            Map<String, Object> provider = new HashMap<>();
            provider.put("url", url);
            System.out.println("Retornando: "+url);
            return new ResponseEntity<>(provider, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Error! "+e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
}
