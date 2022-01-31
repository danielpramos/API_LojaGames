package br.org.generation.lojagames.controller;

import java.math.BigDecimal;
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

import br.org.generation.lojagames.model.Produto;
import br.org.generation.lojagames.repository.CategoriaRepository;
import br.org.generation.lojagames.repository.ProdutoRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository  produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping("/listar")
	public ResponseEntity <List<Produto>> lista() {
		return ResponseEntity.ok(produtoRepository.findAll());
		
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity <List<Produto>> produto(@PathVariable String nome){
		
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <Produto> listaId(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());	
	}
	
	@GetMapping("/preco/{start}/e/{end}")
	public ResponseEntity <List<Produto>> precoEntre(@PathVariable BigDecimal start,@PathVariable BigDecimal end){	
		return ResponseEntity.ok(produtoRepository.findByPrecoBetween(start,end));
		
	
	}
	
	@GetMapping("/preco_maior/{preco}")
	public ResponseEntity <List<Produto>> precoMaior(@PathVariable BigDecimal preco){
		
		return ResponseEntity.ok(produtoRepository.findByPrecoGreaterThanOrderByPreco(preco));
	
	}
	
	@GetMapping("/preco_menor/{preco}")
	public ResponseEntity <List<Produto>> precoMenor(@PathVariable BigDecimal preco){
		
		return ResponseEntity.ok(produtoRepository.findByPrecoLessThanOrderByPrecoDesc(preco));
	
	}
	
	
	@PostMapping
	public ResponseEntity <Produto> posta(@Valid @RequestBody Produto produto) {
		return categoriaRepository.findById(produto.getCategoria().getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto)))
				.orElse(ResponseEntity.badRequest().build());
				
	}
	     	
	@PutMapping
	public ResponseEntity <Produto> atualiza(@Valid @RequestBody Produto atualiza){
		if(produtoRepository.existsById(atualiza.getId())){
	      return categoriaRepository.findById(atualiza.getCategoria().getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(atualiza)))
				.orElse(ResponseEntity.badRequest().build());
		}
		return ResponseEntity.notFound().build();
	}		
				
	@DeleteMapping("/{id}")
	public ResponseEntity <?> deletaProduto(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map(resposta -> {
					produtoRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
		
	}
	
}
