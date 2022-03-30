package com.adayane.algafood.api.controller;

import com.adayane.algafood.domain.entity.Estado;
import com.adayane.algafood.domain.exception.EntidadeEmUsoException;
import com.adayane.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.adayane.algafood.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @GetMapping()
    public List<Estado> listarTodos(){
        return estadoService.listarTodos();
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<?> listar(@PathVariable Long estadoId){
        try{
            Optional<Estado> estado = estadoService.listar(estadoId);
            return ResponseEntity.ok(estado.get());
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado){
        return estadoService.salvar(estado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long estadoId, @RequestBody Estado estadoNew){
        try{
            Optional<Estado> estado = Optional.ofNullable(estadoService.atualizar(estadoId, estadoNew));
            return ResponseEntity.ok(estado.get());
        }catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> deletar(@PathVariable Long estadoId){
        try{
            estadoService.excluir(estadoId);
            return ResponseEntity.noContent().build();

        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        }
    }
}
