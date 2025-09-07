package com.felipe.Tarefa001;

import com.felipe.Tarefa001.model.Tarefa;
import com.felipe.Tarefa001.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

@SpringBootApplication
public class Tarefa001Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Tarefa001Application.class, args);
	}
    private static String caminho = "D:\\ProjetosJava\\Tarefa001\\src\\main\\resources\\tarefas";


    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int continuar = 1;


        while(continuar == 1){
            System.out.println("Escolha uma opção:");
            System.out.println("1 = Escrever");
            System.out.println("2 = Ler");
            System.out.println("3 = Sair");
            System.out.print("Digite sua escolha: ");

            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    System.out.print("Qual a descrição da tarefa? \n");
                    scanner.nextLine(); // Limpa o buffer
                    String desc = scanner.nextLine();

                    System.out.print("Quem é o responsável? \n");
                    String resp = scanner.nextLine();

                    System.out.print("A tarefa está concluída? S para sim e N para não.\n ");
                    String tarConc = scanner.nextLine();

                    Tarefa tarefa = new Tarefa();
                    tarefa.setDescricao(desc);
                    tarefa.setPessoaResponsavel(resp);
                    tarefa.setConcluida("S".equalsIgnoreCase(tarConc));

                    System.out.print("Qual o nome do ficheiro para gravar?\n ");
                    String fileName = scanner.nextLine();

                    gravarDados(tarefa, fileName);


                    System.out.print("Deseja continuar com as operações? 1 para sim e 0 para não.\n ");
                    continuar = scanner.nextInt();
                    break;
                case 2:
                    System.out.println("Há os seguintes ficheiros:\n");
                    listarFicheiros();
                    System.out.println("\nQual ficheiro você deseja abrir?\n");
                    scanner.nextLine(); // Limpa o buffer
                    String ficheiroNome = scanner.nextLine();

                    try {
                        String conteudo = Files.readString(Paths.get(caminho+"\\"+ficheiroNome));
                        System.out.println("Tarefa:");
                        System.out.println(conteudo);
                    } catch (IOException e) {
                        System.out.println("Erro ao ler o ficheiro: " + e.getMessage());
                    }

                    System.out.print("Deseja continuar com as operações? 1 para sim e 0 para não.\n ");
                    continuar = scanner.nextInt();

                    break;
                case 3:
                    System.out.println("Sair.");
                    continuar=0;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }

    private void listarFicheiros() {
        File pasta = new File(caminho);
        if (pasta.exists() && pasta.isDirectory()) {
            File[] ficheiros = pasta.listFiles();

            if (ficheiros != null && ficheiros.length > 0) {
                System.out.println("Ficheiros encontrados:");
                for (File f : ficheiros) {
                    if (f.isFile()) {
                        System.out.println("- " + f.getName());
                    }
                }
            } else {
                System.out.println("A pasta está vazia.");
            }
        } else {
            System.out.println("O caminho especificado não é uma pasta válida.");
        }
    }

    private void gravarDados(Tarefa tarefa, String fileName) {
        ConverteDados conversor = new ConverteDados();
        String dados = conversor.gravarDados(tarefa);
        try (FileWriter writer = new FileWriter(caminho+"\\"+fileName)) {
            writer.write(dados);
            System.out.println("Ficheiro gravado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao gravar o ficheiro: " + e.getMessage());
        }
    }
}
