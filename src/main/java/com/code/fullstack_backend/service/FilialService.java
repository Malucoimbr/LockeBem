package com.code.fullstack_backend.service;

import com.code.fullstack_backend.model.Filial;
import com.code.fullstack_backend.repository.FilialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilialService {

    @Autowired
    private FilialRepository filialRepository;

    public Filial buscarFilialPorId(Long id) {
        return filialRepository.findById(id).orElseThrow(() -> new RuntimeException("Filial não encontrada"));
    }
}
