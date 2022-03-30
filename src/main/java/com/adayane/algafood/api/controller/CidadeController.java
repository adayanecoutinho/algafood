package com.adayane.algafood.api.controller;

import com.adayane.algafood.domain.entity.Cidade;
import com.adayane.algafood.domain.entity.Cozinha;
import com.adayane.algafood.domain.exception.EntidadeEmUsoException;
import com.adayane.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.adayane.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping()
    public List<Cidade> listarTodos(){
        return cidadeService.listarTodos();
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<?> listar(@PathVariable Long cidadeId){
        try{
            Optional<Cidade> cozinha = cidadeService.listar(cidadeId);
            return ResponseEntity.ok(cozinha.get());
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody Cidade cidade){
        return cidadeService.salvar(cidade);
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidadeNew){
        try{
            Optional<Cidade> cidade = Optional.ofNullable(cidadeService.atualizar(cidadeId, cidadeNew));
            return ResponseEntity.ok(cidade.get());
        }catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> deletar(@PathVariable Long cidadeId){
        try{
            cidadeService.excluir(cidadeId);
            return ResponseEntity.noContent().build();

        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
