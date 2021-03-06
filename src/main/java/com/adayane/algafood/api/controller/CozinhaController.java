package com.adayane.algafood.api.controller;

import com.adayane.algafood.domain.entity.Cozinha;
import com.adayane.algafood.domain.exception.EntidadeEmUsoException;
import com.adayane.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.adayane.algafood.domain.repository.CozinhaRepository;
import com.adayane.algafood.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cozinhas")//, produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

    @Autowired
    private CozinhaService cozinhaService;

    @GetMapping()
    public List<Cozinha> listarTodos(){
        return cozinhaService.listarTodos();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<?> listar(@PathVariable Long cozinhaId){
        try{
            Optional<Cozinha> cozinha = cozinhaService.listar(cozinhaId);
            return ResponseEntity.ok(cozinha.get());
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinhaNew){
        try{
            Optional<Cozinha> cozinha = Optional.ofNullable(cozinhaService.atualizar(cozinhaId, cozinhaNew));
            return ResponseEntity.ok(cozinha.get());
        }catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<?> deletar(@PathVariable Long cozinhaId){
        try{
            cozinhaService.excluir(cozinhaId);
            return ResponseEntity.noContent().build();

        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
