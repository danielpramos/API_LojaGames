package br.org.generation.lojagames.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.lojagames.model.Categoria;
import br.org.generation.lojagames.repository.CategoriaRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository  categoriaRepository;
	
	@GetMapping
	public ResponseEntity <List<Categoria>> lista() {
		return ResponseEntity.ok(categoriaRepository.findAll());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <Categoria> listaId(@PathVariable Long id){
		return categoriaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());	
	}
	
	@GetMapping("/tipo/{tipo}")
	public ResponseEntity <List<Categoria>> tipo(@PathVariable String tipo){
		
		return ResponseEntity.ok(categoriaRepository.findAllByTipoContainingIgnoreCase(tipo));
		
	}
	
	@PostMapping
	public ResponseEntity <Categoria> posta(@Valid @RequestBody Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
	}
	
	@PutMapping
	public ResponseEntity <Categoria> atualiza(@Valid @RequestBody Categoria atualiza){
		return categoriaRepository.findById(atualiza.getId())
				.map(resposta -> {
					return ResponseEntity.ok().body(categoriaRepository.save(atualiza));	
				})
				.orElse(ResponseEntity.notFound().build());
	}
	@DeleteMapping
	public ResponseEntity <?> DeletaCategoria(@PathVariable Long id){
		return categoriaRepository.findById(id)
				.map(resposta -> {
					categoriaRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	
}
