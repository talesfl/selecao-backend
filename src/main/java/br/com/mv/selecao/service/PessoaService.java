package br.com.mv.selecao.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.mv.selecao.model.Pessoa;

@Service
public interface PessoaService {
	
	Iterable<Pessoa> findAll();
	Optional<Pessoa> findById(Long id);
	Pessoa save(Pessoa p);
	Pessoa update(Pessoa p);
	void delete(Long id);
	Iterable<Pessoa> pesquisa(String nome, String cpf);
}
