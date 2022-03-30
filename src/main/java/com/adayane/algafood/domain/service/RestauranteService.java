package com.adayane.algafood.domain.service;

import com.adayane.algafood.domain.entity.Cozinha;
import com.adayane.algafood.domain.entity.Restaurante;
import com.adayane.algafood.domain.exception.EntidadeEmUsoException;
import com.adayane.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.adayane.algafood.domain.repository.CozinhaRepository;
import com.adayane.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Restaurante> listarTodos(){
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        return restaurantes;
    }

    public Optional<Restaurante> listar(Long restauranteId){

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("#### Não existe cadastro de restaurante com o código %d. ####", restauranteId)
                ));
        return Optional.ofNullable(restaurante);
    }

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de restaurante com o código %d", cozinhaId)
                ) );

        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    public Restaurante atualizar(Long restauranteId, Restaurante restauranteNew) {
        try{
            Optional<Restaurante> restauranteOld = restauranteRepository.findById(restauranteId);

            BeanUtils.copyProperties(restauranteNew,restauranteOld.get(), "id"); // copia as propriedades de um objeto para outro
            return  restauranteRepository.save(restauranteOld.get());
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de restaurante com código => %d. ####",restauranteId));
        }catch (NoSuchElementException e){
            throw new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de restaurante com código => %d. ####",restauranteId));
        }

    }

    public void excluir(Long restauranteId) {
        try{
            restauranteRepository.deleteById(restauranteId);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de restaurante com código => %d. ####",restauranteId));
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("#### restaurante de código => %d não pode ser removida, pois está em uso. ####", restauranteId));
        }
    }
}
