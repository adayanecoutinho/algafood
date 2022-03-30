package com.adayane.algafood.domain.service;

import com.adayane.algafood.domain.entity.Estado;
import com.adayane.algafood.domain.exception.EntidadeEmUsoException;
import com.adayane.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.adayane.algafood.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Optional<Estado> listar(Long estadoId) {
        Estado estado = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de estado com código => %d. ####", estadoId)));
        return Optional.ofNullable(estado);
    }

    public List<Estado> listarTodos() {
        return estadoRepository.findAll();
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void excluir(Long estadoId){
        try{
            estadoRepository.deleteById(estadoId);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de estado com código => %d. ####",estadoId));
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("#### Estado de código => %d não pode ser removida, pois está em uso. ####", estadoId));
        }

    }

    public Estado atualizar(Long estadoId, Estado estadoNew) {
        try{
            Optional<Estado> estadoOld = estadoRepository.findById(estadoId);

            BeanUtils.copyProperties(estadoNew,estadoOld.get(), "id"); // copia as propriedades de um objeto para outro
            return  estadoRepository.save(estadoOld.get());
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de estado com código => %d. ####",estadoId));
        }catch (NoSuchElementException e){
            throw new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de estado com código => %d. ####",estadoId));
        }

    }
}
