package br.com.mv.selecao.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mv.selecao.model.Pessoa;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {
	
	@Query("select p from Pessoa p where p.nome like %:nome% or p.cpf like %:cpf%")
	Iterable<Pessoa> pesquisa(@Param("nome") String nome, @Param("cpf") String cpf);

}
