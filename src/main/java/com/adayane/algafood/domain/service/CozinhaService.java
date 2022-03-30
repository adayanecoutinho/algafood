package com.adayane.algafood.domain.service;

import com.adayane.algafood.domain.entity.Cozinha;
import com.adayane.algafood.domain.entity.Estado;
import com.adayane.algafood.domain.exception.EntidadeEmUsoException;
import com.adayane.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.adayane.algafood.domain.repository.CozinhaRepository;
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
public class CozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long cozinhaId){
        try{
            cozinhaRepository.deleteById(cozinhaId);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de cozinha com código => %d. ####",cozinhaId));
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("#### Cozinha de código => %d não pode ser removida, pois está em uso. ####", cozinhaId));
        }

    }

    public List<Cozinha> listarTodos(){
        return cozinhaRepository.findAll();
    }

    public Optional<Cozinha> listar(Long cozinhaId) {
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de cozinha com código => %d. ####", cozinhaId)));
        return Optional.ofNullable(cozinha);
    }

    public Cozinha atualizar(Long cozinhaId, Cozinha cozinhaNew) {
        try{
            Optional<Cozinha> cozinhaOld = cozinhaRepository.findById(cozinhaId);

            BeanUtils.copyProperties(cozinhaNew,cozinhaOld.get(), "id"); // copia as propriedades de um objeto para outro
            return  cozinhaRepository.save(cozinhaOld.get());
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de estado com código => %d. ####",cozinhaId));
        }catch (NoSuchElementException e){
            throw new EntidadeNaoEncontradaException(String.format("#### Não existe um cadastro de estado com código => %d. ####",cozinhaId));
        }

    }

}
