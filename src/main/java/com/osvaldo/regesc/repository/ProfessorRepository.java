package com.osvaldo.regesc.repository;

//Repositorio para trabalhar com com operacoes de CRUD na classe ou modelo Professor
//Um repository contem os metodos do CRUD
//Imports para trabalhar com a INTERFACE Repository
import com.osvaldo.regesc.orm.Professor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Indica essa Interface como um Repository contendo os medodos CRUD
//Extendemos a classe CrudRepository e Passamos a classe cujo esse repositorio referencia e o tipo de dados do ID como parametros
@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long> {

    List<Professor> findByNome(String nome);


    //Native SQL: INCOMPLETO
    @Query(nativeQuery = true, value="Select * from professores p INNER JOIN disciplinas d ON p.id = d.professor_id Where p.nome like :nomeProfessor% and d.nome like :nomeDisciplina%")
    List<Professor> acharProfessorPorDisciplina(String nomeProfessor, String nomeDisciplina);
}
