package br.com.alura.demo.controller;

import br.com.alura.demo.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.demo.controller.dto.TopicoDto;
import br.com.alura.demo.controller.form.AtualizacaoTopicoForm;
import br.com.alura.demo.controller.form.TopicoForm;
import br.com.alura.demo.modelo.Topico;
import br.com.alura.demo.repository.CursoRepository;
import br.com.alura.demo.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos") //mapeando os metodos que caiam em topicos
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    private URI uri;


    @GetMapping
    public List<TopicoDto> lista(String nomeCurso) {
        if(nomeCurso == null){
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        }else{
            List<Topico>topicos = topicoRepository.findByCursoNome(nomeCurso);
            return TopicoDto.converter(topicos);
        }

    }

    /*
    * chama primeiramente o form, depois o converter, que puxa o repository
    *
    * */
    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        return ResponseEntity.created(uri).body((new TopicoDto(topico)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id){

        Optional<Topico> topico = topicoRepository.findById(id);
        if(topico.isPresent()){
            return ResponseEntity.ok((new DetalhesDoTopicoDto((topico.get()))));
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto>atualizar(@PathVariable Long id,
                                              @RequestBody @Valid AtualizacaoTopicoForm form ){

        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()){
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();


    }
    @DeleteMapping ("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id){
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()){
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();

    }
}

