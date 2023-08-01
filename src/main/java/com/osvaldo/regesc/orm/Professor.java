package com.osvaldo.regesc.orm;

import jakarta.persistence.*;

import java.util.List;

//Cada clsse modelo e mapeada para a base de dados como uma tabela
//anotacao usada para mapear uma classe como entidade na base de dados
@Entity
@Table(name="Professores")//usada para dar um nome diferente da classe para a tabela
public class Professor {
    @Id//usada para configurar uma coluna como chave primaria na tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Para definir a chave como autoIncrement
    private Long id;
    @Column(nullable = false)//O campo na base de dados nao sera nulo
    private String nome;
    @Column(nullable = false)//Nao sera nulo e o campo tera valores unicos
    private String prontuario;

    //Os fetch sao propriedades usadas para tr5azer os registros da base de dados para uma tabela que e referencia de outra
    //Excixtem 2 tipos de Fetch (LAZY e EAGER) o LAZY nao traz os dados, o EAGER traz
    //Caso contrario podemos usar a anotação @Transactional em da classe service
    @OneToMany(mappedBy = "professor"/*, cascade = CascadeType.ALL , fetch = FetchType.EAGER*/)//Indica que a chave primaria da classe Professor esta a ser mapeada para um
    private List<Disciplina> disciplinas;//atributo "professores" na classe Disciplina

    //Criando um REPOSITORY

    //Por padrao o HIBERNATE exige um contrutor vazio
    public Professor(){}
    //Contrutor da classe, sem o ID pois sera gerado automaticamente
    public Professor(String nome, String prontuario) {
        this.nome = nome;
        this.prontuario = prontuario;
    }

    //Metodos GETTERS e SETTERS
    //O ID so tem o metodo get por causa da variavel ser autoIncrement
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }


    /*Metodo usado para setar um valor nulo no campo Professor da tabela Disciplina caso o Professor seja removido
    da Entidade Professor, como o campo professor na tabela Disciplina e uma chave estrangeira daria erro ao ao deletar
    o professor na tabela Professores por causa da incosistencia de dados.
    A principio podertiamos so usar a Interface (cascade = CascadeType.ALL) mas se assim fosse, ao removermos um professor
    na Tabela professor a disciplina associada a esse professor tambem seria removida na tabela Disciplina.
    Para resolver isso, criamos um metodo que vai SETTAR um valor nulo no campo professor da tabela Disciplina a medida que um professor e removido da
    tabela PROFESSORES
    */
    @PreRemove
    public void atualizaDiscipliOnNoRemove(){
        System.out.println("**************METODO CHAMADO NA DISCIPLINA************");
        for (Disciplina disciplina : this.getDisciplinas()){
            disciplina.setProfessor(null);
        }
    }
    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prontuario='" + prontuario + '\'' +
                '}';
    }
}
