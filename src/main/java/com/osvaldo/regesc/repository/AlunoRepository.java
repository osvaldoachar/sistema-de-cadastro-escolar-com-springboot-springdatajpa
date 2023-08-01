package com.osvaldo.regesc.repository;

import com.osvaldo.regesc.orm.Aluno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlunoRepository extends CrudRepository<Aluno, Long> {

    /*
    * Na Interface do CRUD nós podemos criar nossos proprios metodos personalizados usando o NATIVE QUERIES
    */
    //Metodo Para Buscar um determinado aluno pelo Nome
    List<Aluno> findByNome(String nome);

    List<Aluno> findByNomeStartingWithAndIdadeLessThanEqual(String nome, Integer idade);
    //Aqui termina as DeliveredQueries


    /*
    * Para Fazer as consultas mais especificas, podemos usar outras duas formas JPQL e NATIVE SQL.
    * O JPQL é uma linguagem do JPA que se parece com SQL nativo
    * Quando usamos o JPQL a nossa referencia de dados sera a classe Java
    **/
    @Query("Select a from Aluno a Where a.nome like :nome% AND a.idade >= :idade")
    List<Aluno> findNomeIdadeMaiorOuIgual(String nome, Integer idade);
}
