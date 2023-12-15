package com.lcm.Locadora.service;

import com.lcm.Locadora.repository.LocadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ImovelService {
    @Autowired
    LocadoraRepository locadoraRepository;
    public void deletarImovelPorId(Long id) {
        locadoraRepository.deleteById(id);
    }
}
