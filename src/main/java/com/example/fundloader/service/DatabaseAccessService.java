package com.example.fundloader.service;

import com.example.fundloader.model.Load;
import com.example.fundloader.repository.LoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseAccessService {

    private final LoadRepository loadRepository;

    @Autowired
    public DatabaseAccessService(LoadRepository loadRepository) {
        this.loadRepository = loadRepository;
    }

    public Load saveLoad(Load load) {
        return loadRepository.save(load);
    }

    public void deleteLoadById(String id) {
        loadRepository.deleteById(id);
    }
}