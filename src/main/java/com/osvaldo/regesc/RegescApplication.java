package com.osvaldo.regesc;

import com.osvaldo.regesc.orm.Professor;
import com.osvaldo.regesc.repository.ProfessorRepository;
import com.osvaldo.regesc.service.RelatorioGeralService;
import com.osvaldo.regesc.service.ServiceDisciplina;
import com.osvaldo.regesc.service.ServiceProfessor;
import com.osvaldo.regesc.service.ServiceAluno;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class RegescApplication implements CommandLineRunner {
    private ServiceProfessor servicoprofessor;
    private ServiceDisciplina serviceDisciplina;
    private ServiceAluno serviceAluno;
    private RelatorioGeralService relatorioGeralService;
    //Os objectos passados por parametros sao injectados automaticamente, sem a necessidade de instanciar com o NEW
	public RegescApplication(ServiceProfessor servicoprofessor,
                             ServiceDisciplina serviceDisciplina,
                             ServiceAluno serviceAluno,
                             RelatorioGeralService relatorioGeralService){
        this.servicoprofessor = servicoprofessor;
        this.serviceDisciplina = serviceDisciplina;
        this.serviceAluno = serviceAluno;
        this.relatorioGeralService = relatorioGeralService;
    }
    public static void main(String[] args) {
		SpringApplication.run(RegescApplication.class, args);

	}

    @Override
    public void run(String... args) throws Exception {

        Boolean isTrue = true;
        Scanner sc = new Scanner(System.in);

        while(isTrue){
            System.out.println("||||||||||||||||||||||||||||||||||");
            System.out.println("Oque desejas fazer!");
            System.out.println("|----------------------------------|");
            System.out.println("0 - Sair");
            System.out.println("1 - Professor");
            System.out.println("2 - Disciplina");
            System.out.println("3 - Aluno");
            System.out.println("4 - Relatórios");

            int opcao = sc.nextInt();

            switch (opcao){
                case 1:
                    this.servicoprofessor.menu(sc);
                    break;
                case 2:
                    this.serviceDisciplina.menu(sc);
                    break;
                case 3:
                    this.serviceAluno.menu(sc);
                    break;
                case 4:
                    this.relatorioGeralService.menuRelatorio(sc);
                    break;
                default:
                    isTrue  = false;
                    break;
            }

        }
    }
}
