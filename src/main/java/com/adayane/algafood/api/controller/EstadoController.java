package com.adayane.algafood.api.controller;

import com.adayane.algafood.domain.repository.CozinhaRepository;
import com.adayane.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    //@GetMapping
   /* public List<Estado> listar(){
        return estadoRepository.listar();
    }*/
}
