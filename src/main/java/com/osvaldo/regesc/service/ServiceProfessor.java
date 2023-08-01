package com.osvaldo.regesc.service;


import com.osvaldo.regesc.orm.Disciplina;
import com.osvaldo.regesc.orm.Professor;
import com.osvaldo.regesc.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Scanner;

@Service
@Transactional
public class ServiceProfessor {
    private ProfessorRepository professorRepository;

    public ServiceProfessor(ProfessorRepository professorRepository){
        this.professorRepository = professorRepository;
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
            System.out.println("5 - Visualizar um Professor ");

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
                    this.visualizarProfessor(scanner);
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
        String prontuario;

        System.out.println("||||||||||||||||||||||||||||||||||");
        System.out.println("Digite o nome: ");
        nome = scanner.next();
        System.out.println("|----------------------------------|");
        System.out.println("Digite o Prontuario: ");
        prontuario = scanner.next();
        System.out.println("||||||||||||||||||||||||||||||||||");

        Professor professor = new Professor(nome, prontuario);
        this.professorRepository.save(professor);
        System.out.println("|----------------------------------|");
        System.out.println("Professor Salvo!");
        System.out.println("||||||||||||||||||||||||||||||||||");
    }

    private void atualizar(Scanner scanner){
        Long idProf;
        System.out.println("||||||||||||||||||||||||||||||||||");
        System.out.println("Digite o ID do Professor: ");
        idProf = scanner.nextLong();
        System.out.println("|----------------------------------|");
        //O Hibernate pode trazer valores nulos por padrao, entao, essa classe OPTINAL trata para que isso
        //Nao aconteça
        Optional<Professor> optional = this.professorRepository.findById(idProf);

        //Fazemos uma condiçao para verificar se a busca FINDBYID retornou algo
        if(optional.isPresent()){
            System.out.println("||||||||||||||||||||||||||||||||||");
            System.out.println("Digite o novo nome: ");
            String nome = scanner.next();
            System.out.println("|----------------------------------|");
            System.out.println("Digite o novo pronduario: ");
            String prontuario = scanner.next();
            System.out.println("|----------------------------------|");

            Professor professor = optional.get();
            professor.setNome(nome);
            professor.setProntuario(prontuario);

            this.professorRepository.save(professor);
            System.out.println("Professor Atualizado!");
            System.out.println("||||||||||||||||||||||||||||||||||");
        }else {
            System.out.println("||||||||||||||||||||||||||||||||||");
            System.out.println("Professor não encontrado!");
            System.out.println("||||||||||||||||||||||||||||||||||");
        }
    }

    private void deletar(Scanner scanner){
        System.out.println("||||||||||||||||||||||||||||||||||");
        System.out.println("Digite o ID do professor: ");
        Long id = scanner.nextLong();
        System.out.println("|----------------------------------|");
        Optional<Professor> optional = this.professorRepository.findById(id);

        if (optional.isPresent()){
            Professor professor = optional.get();
            String nome = professor.getNome();
            System.out.println("|----------------------------------------------------------|");
            System.out.println("Desejas excluir o professor "+ nome +  " (S/N)");
            String resposta = scanner.next();
            System.out.println("|----------------------------------------------------------|");
            if (resposta.equalsIgnoreCase("S")){
                this.professorRepository.deleteById(id);
            } else if (resposta.equalsIgnoreCase("N")) {
                System.out.println("||||||||||||||||||||||||||||||||||");
                System.out.println("Operação Cancelada!");
                System.out.println("||||||||||||||||||||||||||||||||||");
            }
        }else {
            System.out.println("||||||||||||||||||||||||||||||||||");
            System.out.println("Professor Não Encotrado!");
            System.out.println("||||||||||||||||||||||||||||||||||");
        }
    }

    private void visualizar(){
        Iterable<Professor> professores = this.professorRepository.findAll();
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||");
        for(Professor professor : professores){

            System.out.println(professor);
        }

        //Um outro jeito de fazer esse metodo e usando o LAMBDA FUNCTION
            //Iterable<Professor> professores = this.professorRepository.findAll();
            //professores.forEach(professor -> {
            //System.out.println(professor);
            //})

        //Outro metodo é:
        //Iterable<Professor> professores = this.professorRepository.findAll();
        //professores.forEach(System.out::println);
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||");
    }

    private void visualizarProfessor(Scanner scanner){
        System.out.println("Digite o ID do Professor: ");
        Long id = scanner.nextLong();

        Optional<Professor> optional = professorRepository.findById(id);
        if (optional.isPresent()){
            Professor professor = optional.get();

            System.out.println("Professor: {");
            System.out.println("ID: " + professor.getId());
            System.out.println("Nome: "+ professor.getNome());
            System.out.println("Prontuario: "+ professor.getProntuario());
            System.out.println("DISCIPLINAS: [");
            for (Disciplina discipliona : professor.getDisciplinas()){
                System.out.println("\t Id: "+discipliona.getId());
                System.out.println("\t Nome: "+discipliona.getNome());
                System.out.println("\t Semestre: "+discipliona.getSemestre());
                System.out.println();
            }
            System.out.println("] \n }");
        }
    }


}
