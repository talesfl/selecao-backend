package br.com.mv.selecao.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mv.selecao.model.Pessoa;
import br.com.mv.selecao.service.PessoaService;

@Controller()
public class PessoaController {

	@Autowired
	private PessoaService service;

	@GetMapping("/pessoas")
	public ResponseEntity<Iterable<Pessoa>> findAll() {
		return new ResponseEntity<Iterable<Pessoa>>(this.service.findAll(), getCORSHeader(), HttpStatus.OK);
	}

	@GetMapping("/pessoas/{id}")
	public ResponseEntity<Pessoa> findById(@PathVariable("id") Long id) {
		Optional<Pessoa> optPessoa = this.service.findById(id);

		return optPessoa.isPresent() ? new ResponseEntity<Pessoa>(optPessoa.get(), getCORSHeader(), HttpStatus.OK)
				: new ResponseEntity<Pessoa>(getCORSHeader(), HttpStatus.NOT_FOUND);
	}

	@GetMapping("/pessoas/pesquisa")
	public ResponseEntity<Iterable<Pessoa>> pesquisa(@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cpf", required = false) String cpf) {
		
		Optional<Iterable<Pessoa>> opt = Optional.of(this.service.pesquisa(nome, cpf));

		ResponseEntity<Iterable<Pessoa>> response = null;

		if (opt.isPresent()) {
			response = new ResponseEntity<Iterable<Pessoa>>(opt.get(), getCORSHeader(), HttpStatus.OK);
		} else {
			response = new ResponseEntity<Iterable<Pessoa>>(getCORSHeader(), HttpStatus.NOT_FOUND);
		}

		return response;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/pessoas/new")
	public ResponseEntity<Pessoa> saveNew(@RequestBody Pessoa p) {

		Optional<Pessoa> optPessoa = Optional.of(p);

		Pessoa pessoa = null;
		ResponseEntity<Pessoa> response = null;

		if (optPessoa.isPresent()) {

			pessoa = this.service.save(p);
			response = new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);

		} else {
			response = new ResponseEntity<Pessoa>(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return response;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/pessoas/update")
	public ResponseEntity<Pessoa> update(@RequestBody Pessoa p) {

		Optional<Pessoa> optPessoa = Optional.of(p);

		Pessoa pessoa = null;
		ResponseEntity<Pessoa> response = null;

		if (optPessoa.isPresent()) {
			Optional<Pessoa> toUpdate = this.service.findById(p.getId());

			if (toUpdate.isPresent()) {

				Pessoa toUp = toUpdate.get();
				toUp.setCpf(p.getCpf());
				toUp.setDataNascimento(p.getDataNascimento());
				toUp.setEmail(p.getEmail());
				toUp.setNome(p.getNome());
				toUp.setTelefones(p.getTelefones());

				pessoa = this.service.update(p);
				response = ResponseEntity.ok(pessoa);

			} else {
				response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
			}

		} else {
			response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		return response;
	}

	@GetMapping("/pessoas/{id}/delete")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		Optional<Pessoa> toDelete = this.service.findById(id);

		ResponseEntity<Void> response = null;
		if (toDelete.isPresent()) {
			this.service.delete(id);
			response = new ResponseEntity<Void>(getCORSHeader(), HttpStatus.OK);
		} else {
			response = new ResponseEntity<Void>(getCORSHeader(), HttpStatus.NOT_FOUND);
		}

		return response;
	}

	private HttpHeaders getCORSHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
		return headers;
	}
}
