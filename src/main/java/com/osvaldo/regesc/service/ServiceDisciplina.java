package com.osvaldo.regesc.service;

import com.osvaldo.regesc.orm.Aluno;
import com.osvaldo.regesc.orm.Disciplina;
import com.osvaldo.regesc.orm.Professor;
import com.osvaldo.regesc.repository.AlunoRepository;
import com.osvaldo.regesc.repository.DisciplinaRepository;
import com.osvaldo.regesc.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ServiceDisciplina {

    private DisciplinaRepository disciplinaRepository;
    private ProfessorRepository professorRepository;
    private AlunoRepository alunoRepository;
    public ServiceDisciplina(DisciplinaRepository disciplinaRepository, ProfessorRepository professorRepository, AlunoRepository alunoRepository){
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;

    }

  public void menu(Scanner scanner){
        Boolean isTrue = true;

        while(isTrue){

            System.out.println("||||||||||||||||||||||||||||||||||");
            System.out.println("Qual acção voce deseja realizar");
            System.out.println("|----------------------------------|");
            System.out.println("0 - Voltar para o menu principal");
            System.out.println("1 - Inserir");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Visualizar ");
            System.out.println("5 - Matricular Aluno ");

            int escolha =  scanner.nextInt();

            switch (escolha){
                case 1:
                    this.inserir(scanner);
                    break;
                case 2:
                    this.atualizar(scanner);
                    break;
                case 3:
                    this.deletar(scanner);
                    break;
                case 4:
                    this.visualizar();
                    break;
                case 5:
                    this.matricularAluno(scanner);
                    break;
                default:
                    isTrue = false;
                    break;
            }
        }
  }

        private void inserir(Scanner scanner){

            System.out.println("Digite o Nome da Disciplina: ");
            String nome = scanner.next();

            System.out.println("Digite o Semestre: ");
            Integer semestre = scanner.nextInt();

            System.out.println("Digite o ID do Professor: ");
            Long professorID = scanner.nextLong();

            Optional<Professor> optional = professorRepository.findById(professorID);
            if(optional.isPresent()){
                Professor professor = optional.get();
                Set<Aluno> alunos = this.matricular(scanner);
                Disciplina disciplina = new Disciplina(nome, semestre, professor);
                disciplinaRepository.save(disciplina);
                System.out.println("Disciplina Registada!");
            }else {
                System.out.println("O ID "+ professorID+ " do Professor é inválido, Tente Novamente!");
            }
        }


        private void atualizar(Scanner scanner){
            System.out.println("Digite o ID da Disciplina");
            Long discipinaID = scanner.nextLong();

            Optional<Disciplina> optionalDisciplina = this.disciplinaRepository.findById(discipinaID);
            if(optionalDisciplina.isPresent()){
                Disciplina disciplina = optionalDisciplina.get();
                System.out.println("Digite o Nome da Disciplina: ");
                String nome = scanner.next();

                System.out.println("Digite o Semestre: ");
                Integer semestre = scanner.nextInt();

                System.out.println("Digite o ID do Professor: ");
                Long professorID = scanner.nextLong();

                Optional<Professor> optionalProfessor = this.professorRepository.findById(professorID);
                if (optionalProfessor.isPresent()){
                    Professor professor = optionalProfessor.get();

                    Set<Aluno> alunos = this.matricular(scanner);

                    disciplina.setNome(nome);
                    disciplina.setSemestre(semestre);
                    disciplina.setProfessor(professor);
                    disciplina.setAlunos(alunos);

                    disciplinaRepository.save(disciplina);
                    System.out.println("Disciplina Atualizada!");
                }else {
                    System.out.println("O ID "+ professorID+ " do Professor é inválido, Tente Novamente!");
                }
            }else{
                System.out.println("O ID "+ discipinaID+ " da Disciplina é inválido, Tente Novamente!");
            }
        }


    private void deletar(Scanner scanner){
        System.out.println("||||||||||||||||||||||||||||||||||");
        System.out.println("Digite o ID da Disciplina: ");
        Long id = scanner.nextLong();
        System.out.println("|----------------------------------|");
        Optional<Disciplina> optional = this.disciplinaRepository.findById(id);

        if (optional.isPresent()){
            Disciplina disciplina = optional.get();
            String nome = disciplina.getNome();
            System.out.println("|----------------------------------------------------------|");
            System.out.println("Desejas excluir a disciplina "+ nome +  " (S/N)");
            String resposta = scanner.next();
            System.out.println("|----------------------------------------------------------|");
            if (resposta.equalsIgnoreCase("S")){
                this.disciplinaRepository.deleteById(id);
            } else if (resposta.equalsIgnoreCase("N")) {
                System.out.println("||||||||||||||||||||||||||||||||||");
                System.out.println("Operação Cancelada!");
                System.out.println("||||||||||||||||||||||||||||||||||");
            }
        }else {
            System.out.println("||||||||||||||||||||||||||||||||||");
            System.out.println("Disciplina Não Encotrada!");
            System.out.println("||||||||||||||||||||||||||||||||||");
        }
    }

    public void visualizar(){
        Iterable<Disciplina> disciplinas = this.disciplinaRepository.findAll();
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||");
        for(Disciplina disciplina : disciplinas){

            System.out.println(disciplina);
        }

    }

    private Set<Aluno> matricular(Scanner scanner){
        Boolean isTrue = true;

        Set<Aluno> alunos = new HashSet<>();

        while(isTrue) {
            System.out.println("ID do Aluno a ser matriculado (Digite 0 Para Sair): ");
            Long idAluno = scanner.nextLong();

            if (idAluno > 0) {
                System.out.println("ID do Aluno: " + idAluno);
                Optional<Aluno> optional = this.alunoRepository.findById(idAluno);
                if (optional.isPresent()) {
                    alunos.add(optional.get());
                } else {
                    System.out.println("Nenhum Aluno Possui o ID " + idAluno);
                }
            } else {
                isTrue = false;
            }
        }
            return alunos;

    }

    private void matricularAluno(Scanner scanner){
        System.out.println("Digite o ID da Disciplina para Matricular Aluno: ");
        Long idDisciplina = scanner.nextLong();

        Optional<Disciplina> optional = this.disciplinaRepository.findById(idDisciplina);
        if (optional.isPresent()){
            Disciplina disciplina = optional.get();
            Set<Aluno> novosAlunos = this.matricular(scanner);
            disciplina.getAlunos().addAll(novosAlunos);
            this.disciplinaRepository.save(disciplina);
        }else{
            System.out.println("O ID "+idDisciplina+" da Disciplina é Invalido!");
        }
    }
}
