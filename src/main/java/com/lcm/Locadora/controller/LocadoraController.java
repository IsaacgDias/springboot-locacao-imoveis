package com.lcm.Locadora.controller;

import com.lcm.Locadora.model.Imovel;
import com.lcm.Locadora.repository.LocadoraRepository;
import com.lcm.Locadora.service.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    @DeleteMapping("/deletarImovel")
    public ResponseEntity<String> deletarImovelPorId(@RequestParam Long id) {
        try {
            imovelService.deletarImovelPorId(id);
            return ResponseEntity.ok("Imóvel deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar imovel");
        }
    }

    // antes de editar pega os dados e exibe na página para editar os dados
    @GetMapping("/getImovelById")
    public ResponseEntity<Imovel> getImovelById(@RequestParam Long id) {
        Imovel imovel = locadoraRepository.findById(id).orElse(null);

        if (imovel != null) {
            return ResponseEntity.ok(imovel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Editar
    @PutMapping("/editarImovel")
    public String editarImovel(@RequestParam Long id, @RequestBody Imovel updatedImovel) {
        Imovel imovelExistente = locadoraRepository.findById(id).orElse(null);

        double novoValorAluguelDouble = updatedImovel.getValorAluguel();
        float novoValorAluguelFloat = (float) novoValorAluguelDouble;

        if (imovelExistente != null) {

            imovelExistente.setTitulo(updatedImovel.getTitulo());
            imovelExistente.setDescricao(updatedImovel.getDescricao());
            imovelExistente.setValorAluguel(novoValorAluguelFloat);
            imovelExistente.setCep(updatedImovel.getCep());
            imovelExistente.setBairro(updatedImovel.getBairro());
            imovelExistente.setCidade(updatedImovel.getCidade());
            imovelExistente.setEstado(updatedImovel.getEstado());

            Imovel salvarImovel = locadoraRepository.save(imovelExistente);

            return salvarImovel.getId() + " alterado com sucesso";
        } else {
            return "Imóvel do ID " + id + " não encontrado.";
        }
    }

    // API Via CEP
    @GetMapping("/consultaCEP/{cep}")
    public ResponseEntity<?> consultarCEP(@PathVariable String cep) {
        // Chamada à API do Via CEP
        String viaCepUrl = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();

        // Faz a requisição get para obeter a resposta
        ResponseEntity<String> response = restTemplate.getForEntity(viaCepUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            return ResponseEntity.ok(responseBody);
        } else {

            return ResponseEntity.status(response.getStatusCode()).body("Erro ao consultar o CEP.");
        }
    }
}


