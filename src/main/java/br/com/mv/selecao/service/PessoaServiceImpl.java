package br.com.mv.selecao.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mv.selecao.model.Pessoa;
import br.com.mv.selecao.repository.PessoaRepository;

@Service
public class PessoaServiceImpl implements PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public Iterable<Pessoa> findAll() {
		return this.pessoaRepository.findAll();
	}

	@Override
	public Optional<Pessoa> findById(Long id) {
		return this.pessoaRepository.findById(id);
	}

	@Override
	public Pessoa save(Pessoa p) {
		return this.pessoaRepository.save(p);
	}

	@Override
	public Pessoa update(Pessoa p) {
		return this.pessoaRepository.save(p);
	}

	@Override
	public void delete(Long id) {
		this.pessoaRepository.deleteById(id);

	}

	@Override
	public Iterable<Pessoa> pesquisa(String nome, String cpf) {
		return this.pessoaRepository.pesquisa(nome, cpf);
		
	}

}
