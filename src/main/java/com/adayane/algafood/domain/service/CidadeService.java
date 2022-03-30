package com.adayane.algafood.domain.service;

import com.adayane.algafood.domain.entity.Cidade;
import com.adayane.algafood.domain.entity.Cozinha;
import com.adayane.algafood.domain.exception.EntidadeEmUsoException;
import com.adayane.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.adayane.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public Cidade salvar(Cidade cidade){
        return cidadeRepository.save(cidade);
    }

    public void excluir(Long cidadeId){
        try{
            cidadeRepository.deleteById(cidadeId);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de cidade com código => %d. ####",cidadeId));
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("#### Cidade de código => %d não pode ser removida, pois está em uso. ####", cidadeId));
        }

    }

    public List<Cidade> listarTodos(){
        return cidadeRepository.findAll();
    }

    public Optional<Cidade> listar(Long cidadeId) {
        Cidade cidade = cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de cidade com código => %d. ####", cidadeId)));
        return Optional.ofNullable(cidade);
    }

    public Cidade atualizar(Long cidadeId, Cidade cidadeNew) {
        try{
            Optional<Cidade> cidadeOld = cidadeRepository.findById(cidadeId);

            BeanUtils.copyProperties(cidadeNew,cidadeOld.get(), "id"); // copia as propriedades de um objeto para outro
            return  cidadeRepository.save(cidadeOld.get());
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de cidade com código => %d. ####",cidadeId));
        }catch (NoSuchElementException e){
            throw new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de cidade com código => %d. ####",cidadeId));
        }

    }
}
