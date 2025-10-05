package com.example.tarefa002.tarefa._2.principal;

import java.util.Scanner;

public class Principal {

    private final Scanner leitura = new Scanner(System.in);

    public void runPrincipal() {

        //Operacao - implementacao
        Operacao operacao = (a, b) -> a * b;

        //Primo - implementacao
        NumeroPrimo numeroPrimo = (int number) -> {
            if (number <= 1) return false;
            if (number == 2) return true;
            if (number % 2 == 0) return false;

            int limite = (int) Math.sqrt(number);
            for (int i = 3; i <= limite; i += 2) {
                if (number % i == 0) return false;
            }

            return true;};

        //Multiplicar
        System.out.println("Digite 2 numeros inteiros para multiplicar ");
        var valorA = this.leitura.nextLine();
        var valorB = this.leitura.nextLine();

        int resultado = operacao.multiplicacao(Integer.parseInt(valorA), Integer.parseInt(valorB));
        System.out.println("Resultado: " + resultado);

        //Numero primo
        System.out.println("Digite 1 numeros inteiro para verificar se é primo ");
        var valorPrimo = this.leitura.nextLine();
        System.out.println("Esse número " + (numeroPrimo.isNumeroPrimo(Integer.parseInt(valorPrimo)) ? "" : "não ") + "é número um primo.");
    }

}
