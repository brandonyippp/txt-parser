package com.example.fundloader;

import com.example.fundloader.config.LoadCacheConfig;
import com.example.fundloader.utils.FileIO;
import com.example.fundloader.model.Load;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(LoadCacheConfig.class)
public class FundLoaderApplication {

    private final FileIO<Load> loadsFileProcessor;

    @Autowired
    public FundLoaderApplication(FileIO<Load> loadsFileProcessor) {
        this.loadsFileProcessor = loadsFileProcessor;
    }

    public static void main(String[] args) {
        SpringApplication.run(FundLoaderApplication.class, args);
    }

    @PostConstruct
    public void processFiles() {
        loadsFileProcessor.processFile("src/main/resources/");
    }
}