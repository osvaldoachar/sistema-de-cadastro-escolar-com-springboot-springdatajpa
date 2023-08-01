package com.osvaldo.regesc.service;

import com.osvaldo.regesc.orm.Aluno;
import com.osvaldo.regesc.orm.Disciplina;
import com.osvaldo.regesc.orm.Professor;
import com.osvaldo.regesc.repository.AlunoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Scanner;
@Transactional
@Service
public class ServiceAluno {

    private AlunoRepository alunoRepository;

    public ServiceAluno(AlunoRepository alunoRepository){
        this.alunoRepository = alunoRepository;
    }
    @Transactional
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
            System.out.println("5 - Visualizar um Aluno ");

            int escolha = scanner.nextInt();

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
                    this.visualizaAluno(scanner);
                    break;
                default:
                    //System.out.print("Escolha uma opção VÁLIDA!!!");
                    isTrue=false;
                    break;
            }
            System.out.println("");


        }
    }
    private void inserir(Scanner scanner){

        String nome;
        Integer idade;

        System.out.println("||||||||||||||||||||||||||||||||||");
        System.out.println("Digite o nome: ");
        nome = scanner.next();
        System.out.println("|----------------------------------|");
        System.out.println("Digite a Idade: ");
        idade = scanner.nextInt();
        System.out.println("||||||||||||||||||||||||||||||||||");

        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setIdade(idade);
        this.alunoRepository.save(aluno);
        System.out.println("|----------------------------------|");
        System.out.println("Aluno Salvo!");
        System.out.println("||||||||||||||||||||||||||||||||||");
    }

    private void atualizar(Scanner scanner){
        Long idAluno;
        System.out.println("||||||||||||||||||||||||||||||||||");
        System.out.println("Digite o ID do Aluno: ");
        idAluno = scanner.nextLong();
        System.out.println("|----------------------------------|");
        //O Hibernate pode trazer valores nulos por padrao, entao, essa classe OPTINAL trata para que isso
        //Nao aconteça
        Optional<Aluno> optional = this.alunoRepository.findById(idAluno);

        //Fazemos uma condiçao para verificar se a busca FINDBYID retornou algo
        if(optional.isPresent()){
            System.out.println("||||||||||||||||||||||||||||||||||");
            System.out.println("Digite o novo nome: ");
            String nome = scanner.next();
            System.out.println("|----------------------------------|");
            System.out.println("Digite a nova Idade: ");
            Integer idade = scanner.nextInt();
            System.out.println("|----------------------------------|");

            Aluno aluno = optional.get();
            aluno.setNome(nome);
            aluno.setIdade(idade);

            this.alunoRepository.save(aluno);
            System.out.println("Aluno Atualizado!");
            System.out.println("||||||||||||||||||||||||||||||||||");
        }else {
            System.out.println("||||||||||||||||||||||||||||||||||");
            System.out.println("Aluno não encontrado!");
            System.out.println("||||||||||||||||||||||||||||||||||");
        }
    }

    private void deletar(Scanner scanner){
        System.out.println("||||||||||||||||||||||||||||||||||");
        System.out.println("Digite o ID do Aluno: ");
        Long id = scanner.nextLong();
        System.out.println("|----------------------------------|");
        Optional<Aluno> optional = this.alunoRepository.findById(id);

        if (optional.isPresent()){
            Aluno aluno = optional.get();
            String nome = aluno.getNome();
            System.out.println("|----------------------------------------------------------|");
            System.out.println("Desejas excluir o Aluno "+ nome +  " (S/N)");
            String resposta = scanner.next();
            System.out.println("|----------------------------------------------------------|");
            if (resposta.equalsIgnoreCase("S")){
                this.alunoRepository.deleteById(id);
                System.out.println("||||||||||||||||||||||||||||||||||");
                System.out.println("Aluno Deletado!");
                System.out.println("||||||||||||||||||||||||||||||||||");
            } else if (resposta.equalsIgnoreCase("N")) {
                System.out.println("||||||||||||||||||||||||||||||||||");
                System.out.println("Operação Cancelada!");
                System.out.println("||||||||||||||||||||||||||||||||||");
            }
        }else {
            System.out.println("||||||||||||||||||||||||||||||||||");
            System.out.println("Aluno Não Encotrado!");
            System.out.println("||||||||||||||||||||||||||||||||||");
        }
    }

    private void visualizar() {
        Iterable<Aluno> alunos = this.alunoRepository.findAll();
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||");
        for (Aluno aluno : alunos) {

            System.out.println(aluno);
        }
    }

    private void visualizaAluno(Scanner scanner){
        System.out.println("Digite o ID do Aluno: ");
        Long id = scanner.nextLong();

        Optional<Aluno> optional = this.alunoRepository.findById(id);
        if (optional.isPresent()){
            Aluno aluno = optional.get();

            System.out.println("Aluno: {");
            System.out.println("ID: " + aluno.getId());
            System.out.println("Nome: "+ aluno.getNome());
            System.out.println("Idade: "+ aluno.getIdade());
            System.out.println("DISCIPLINAS: [");
            if (aluno.getDisciplinas() != null) {
                for (Disciplina discipliona : aluno.getDisciplinas()) {
                    System.out.println("\t Nome: " + discipliona.getNome());
                    System.out.println("\t Semestre: " + discipliona.getSemestre());
                    System.out.println();
                }
            }
            System.out.println("] \n }");
        }else{
            System.out.println("||||||||||||||||||||||||||||||||||");
            System.out.println("O Aluno com o ID: "+id+" Não Existe!");
            System.out.println("||||||||||||||||||||||||||||||||||");
        }
    }
}
