package br.com.alura.demo.repository;

import br.com.alura.demo.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

//essa interface tem de extender o JpaRepository para buscar as informações no BD
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String nome);
}
