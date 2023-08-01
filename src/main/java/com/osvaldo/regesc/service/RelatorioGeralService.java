package com.osvaldo.regesc.service;

import com.osvaldo.regesc.orm.Aluno;
import com.osvaldo.regesc.orm.Professor;
import com.osvaldo.regesc.repository.AlunoRepository;
import com.osvaldo.regesc.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioGeralService {

    private AlunoRepository alunoRepository;
    private ProfessorRepository professorRepository;

    public RelatorioGeralService(AlunoRepository alunoRepository, ProfessorRepository professorRepository){
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
    }


    public void menuRelatorio(Scanner scanner){

        Boolean isTrue = true;

        while(isTrue){
            System.out.println("||||||||||||||||||||||||||||||||||");
            System.out.println("Qual Relatorio voce deseja ver");
            System.out.println("|----------------------------------|");
            System.out.println("0 - Voltar para o menu principal");
            System.out.println("1 - Aluno Por Nome");
            System.out.println("2 - Aluno Por Nome e Idade");
            System.out.println("3 - Professor Por Nome");
            System.out.println("4 - Alunos por um dado Nome Maior ou Igual ");
            System.out.println("5 - Visualizar Professor Por Disciplina");

            int escolha = scanner.nextInt();

            switch (escolha){
                case 1:
                    this.alunoPorNome(scanner);
                    break;
                case 2:
                    this.alunoPorNomeEIdade(scanner);
                    break;
               case 3:
                    this.professorPorNome(scanner);
                    break;
                 case 4:
                    this.alunoPorNomeIdadeMaiorOuIgual(scanner);
                    break;
                  case 5:
                    this.professorPorDisciplina(scanner);
                    break;
                default:
                    //System.out.print("Escolha uma opção VÁLIDA!!!");
                    isTrue=false;
                    break;
            }
            System.out.println("");
        }
    }

    private void alunoPorNome(Scanner scanner){
        System.out.println("Digite o Nome do Aluno: ");
        String nome = scanner.next();

        List<Aluno> alunos = this.alunoRepository.findByNome(nome);

        for(Aluno aluno : alunos){
            System.out.println(aluno);
        }
    }

    private void alunoPorNomeEIdade(Scanner scanner){
        System.out.println("Digite o Nome do Aluno: ");
        String nome = scanner.next();
        System.out.println("Digite a Idade do Aluno: ");
        Integer idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findByNomeStartingWithAndIdadeLessThanEqual(nome, idade);

        for(Aluno aluno : alunos){
            System.out.println(aluno);
        }
    }

    private void professorPorNome(Scanner scanner){
        System.out.println("Digite o Nome do Professor: ");
        String nome = scanner.next();

        List<Professor> professors = this.professorRepository.findByNome(nome);

        for(Professor professor : professors){
           System.out.println(professor);
        }
    }

    private void alunoPorNomeIdadeMaiorOuIgual(Scanner scanner){
        System.out.println("Digite o Nome do Aluno: ");
        String nome = scanner.next();
        System.out.println("Digite a Idade do Aluno: ");
        Integer idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findNomeIdadeMaiorOuIgual(nome, idade);

        for(Aluno aluno : alunos){
            System.out.println(aluno);
        }
    }

    private void professorPorDisciplina(Scanner scanner){
        System.out.println("Digite o Nome do Professor: ");
        String nomeProfessor = scanner.next();
        System.out.println("Digite o Nome da Disciplina: ");
        String nomeDisciplina = scanner.next();

        List<Professor> professores = this.professorRepository.acharProfessorPorDisciplina(nomeProfessor, nomeDisciplina);

        for(Professor professor : professores){
            System.out.println(professor);
        }
    }
}
