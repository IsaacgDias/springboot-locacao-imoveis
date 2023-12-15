package com.lcm.Locadora.controller;

import com.lcm.Locadora.model.Imovel;
import com.lcm.Locadora.repository.LocadoraRepository;
import com.lcm.Locadora.service.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@RestController retorna em Json
@RestController
@RequestMapping("/")
public class LocadoraController {
    //Injetar Instâncias
    @Autowired
    LocadoraRepository locadoraRepository;

    @Autowired
    ImovelService imovelService;
    // Cadastrar
    @PostMapping("/cadastrarImovel")
    public ResponseEntity<String> form(@RequestBody Imovel imovel) {
        try {
            locadoraRepository.save(imovel);
            return ResponseEntity.ok("Imóvel cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar o imóvel");
        }
    }

    // Listar
    @GetMapping("/listaImovel")
    public List<Imovel> getAllDados() {
        return locadoraRepository.findAll();
    }

    // Deletar
    @GetMapping("/deletarImovel")
    public ResponseEntity<String> deletarImovelPorId(@RequestParam Long id) {
        try {
            imovelService.deletarImovelPorId(id);
            return ResponseEntity.ok("Imóvel deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar imovel");
        }
    }

}
