package com.example.fundloader.utils;

import java.io.*;

import com.example.fundloader.model.Load;
import com.example.fundloader.model.interfaces.Model;
import com.example.fundloader.service.StorageManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileIO <T> {
    private final StorageManagerService storageManagerService;
    private final ObjectMapper objectMapper;
    private final Class<T> model;

    @Autowired
    public FileIO(Class<T> model, StorageManagerService storageManagerService, ObjectMapper objectMapper) {
        this.model = model;
        this.storageManagerService = storageManagerService;
        this.objectMapper = objectMapper;
    }

    public void processFile(String path) {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(path + "input.txt"));
            writer = new BufferedWriter(new FileWriter(path + "output.txt")); //TODO: Proper file in /resources


            String line = "";
            while ((line = reader.readLine()) != null) { //TODO: Multithreading
                Model obj = createModelObject(line);
                String res = processLine(obj);

                writer.write(res + "\n");
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
//            throw new RuntimeException(e);
        } finally {
            try {
                closeBuffers(reader, writer);
            } catch (IOException e) {
                e.printStackTrace(); //TODO: Better logging
            }
        }
    }

    private void closeBuffers(BufferedReader reader, BufferedWriter writer) throws IOException {
        if (reader != null) {
            reader.close();
        }

        if (writer != null) {
            writer.close();
        }
    }

    private String processLine(Model obj) {
        if (this.model.equals(Load.class)) {
            Boolean success = this.storageManagerService.processLoad((Load) obj);

            return success == null ? "" : ((Load) obj).generateOutputString(success);
        }

        return null;
    }

    private Model createModelObject(String line) throws IOException {
        if (this.model.equals(Load.class)) {
            return this.objectMapper.readValue(line, Load.class);
        }

        return null;
    }
}
