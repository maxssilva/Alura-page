package br.com.alura.demo.controller.form;

import br.com.alura.demo.modelo.Curso;
import br.com.alura.demo.modelo.Topico;
import br.com.alura.demo.repository.CursoRepository;
import com.sun.istack.NotNull;

import javax.validation.constraints.NotEmpty;


public class TopicoForm {

    @NotNull @NotEmpty
    private  String titulo;
    private String mensagem;
    private String nomeCurso;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }



    //para converter passando o nome do curso, precisa buscar dentro do BD
    //o nome do curso, para isso será criado uma uma classe CursoRepository
    public Topico converter(CursoRepository cursoRepository) {
        Curso curso = cursoRepository.findByNome(nomeCurso);

        return new Topico(titulo, mensagem, curso);
    }
}
