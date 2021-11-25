package br.com.alura.demo.controller;

import br.com.alura.demo.controller.dto.TopicoDto;
import br.com.alura.demo.controller.form.TopicoForm;
import br.com.alura.demo.modelo.Topico;
import br.com.alura.demo.repository.CursoRepository;
import br.com.alura.demo.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        return ResponseEntity.created(uri).body((new TopicoDto(topico)));
    }
}

