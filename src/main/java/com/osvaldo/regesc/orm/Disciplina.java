package com.osvaldo.regesc.orm;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="Disciplinas")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer semestre;

    @ManyToOne//Serve para definir esse atributo como chave estrangeira do relacionamento Professor:Disciplina
    @JoinColumn(name="professor_id", nullable = true)//Serve para informar ao Hibernate para criar apenas um campo na tabela Disciplinas
    private Professor professor;//Caso contrario criaria uma tabela intermediaria como se fosse relacionamento N:M

    @ManyToMany
    @JoinTable(name="disciplina_aluno",
    joinColumns = @JoinColumn(name="disciplina_fk"),
    inverseJoinColumns = @JoinColumn(name="aluno_fk"))
    private Set<Aluno> alunos;



    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Deprecated//Indica que naõ estamos usando esse contrutor e sera usado por um elemento externo
    public Disciplina() {
    }

    public Disciplina(String nome, Integer semestre, Professor professor){
        this.nome = nome;
        this.semestre = semestre;
        this.professor = professor;
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

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }
    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", semestre=" + semestre +
                ", professor=" + professor +
                ", alunos=" + alunos +
                '}';
    }
}
