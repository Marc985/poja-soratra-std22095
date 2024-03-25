package hei.school.soratra.endpoint.rest.controller;

import hei.school.soratra.file.BucketComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static java.nio.file.Files.createTempDirectory;
import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/soratra/{id}")
public class Soratra {
    BucketComponent bucketComponent;
    String soratraDirectory = "soratra/";
    @PutMapping("/soratra/{id}")
    public ResponseEntity<?> addPoem(@PathVariable int id,@RequestBody String poem) throws IOException {


        byte[] convertedPoem=poem.getBytes(StandardCharsets.UTF_8);
        String bucketKey=soratraDirectory+id+".txt";

        var directoryPrefix="dir-"+id;
        var directoryToUpload = createTempDirectory(directoryPrefix).toFile();
        File file =new File(directoryToUpload.getAbsolutePath()+"/"+id+".txt");
        writeContent(file,poem);
        bucketComponent.upload(file,bucketKey);

         return  ResponseEntity.ok().body("ok");


        }

    private void writeContent(File file,String poem) throws IOException {
        FileWriter writer = new FileWriter(file);
        var content = poem;
        writer.write(content);
        writer.close();
    }

    @GetMapping("/soratra/{id}")
    public  ResponseEntity<?> downloadFile(@PathVariable  int id)
    {
        String bucketKey=soratraDirectory+"poem"+id+".txt";
        bucketComponent.download(soratraDirectory+id+".txt");
        return ResponseEntity
                .ok().body("ok");

    }

}
