package com.osvaldo.regesc.orm;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="Alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30)
    private String nome;
    private Integer idade;
    /*
    * Mudamos o tipo do atributo do Relacionamento ManyToMany de List<> para Set<> porque nos queremos que a combinação
    * das duas chaves estrangeiras na tabela intermediaria seja a chave primaria da tabela para que naõ haja combinaçoes
    * repetidas, O List<> armazena dados repetidos, o Set<> nao.
    * */
    @ManyToMany(mappedBy = "alunos")
    private Set<Disciplina> disciplinas;

    public Aluno() {
    }

    public Aluno(Long id, String nome, Integer idade, Set<Disciplina> disciplinas) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.disciplinas = disciplinas;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                '}';
    }
}
