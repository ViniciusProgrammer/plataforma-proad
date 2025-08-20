package br.ufrn.plataformaproad.controller;

import br.ufrn.plataformaproad.domain.Processo;
import br.ufrn.plataformaproad.repository.ProcessoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    private final ProcessoRepository repository;

    public ProcessoController(ProcessoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Processo> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processo> buscarPorId(@PathVariable Long id) {
        Optional<Processo> processo = repository.findById(id);
        return processo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Processo criar(@RequestBody Processo processo) {
        return repository.save(processo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Processo> editar(@PathVariable Long id, @RequestBody Processo novoProcesso) {
        return repository.findById(id)
                .map(processo -> {
                    processo.setNumero(novoProcesso.getNumero());
                    processo.setTipo(novoProcesso.getTipo());
                    processo.setObjeto(novoProcesso.getObjeto());
                    repository.save(processo);
                    return ResponseEntity.ok(processo);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}