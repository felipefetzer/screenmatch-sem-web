package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://omdbapi.com/?t=";
    private final String SEASON = "&season=";
    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca:");
        var nomeSerie = this.leitura.nextLine();
        String cleanSerieName = nomeSerie.replaceAll(" ", "+");
        var json = this.consumoApi.obterDados(ENDERECO + cleanSerieName + API_KEY);
        DadosSerie dados = this.conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        //temporadas
        List<DadosTemporada> dadosTemporadaList = new ArrayList<>();
        for(int i = 1; i<=dados.totalTemporadas(); i++) {
            json = this.consumoApi.obterDados(ENDERECO + cleanSerieName+ SEASON + i + API_KEY);
            DadosTemporada dadosTemp = conversor.obterDados(json, DadosTemporada.class);
            dadosTemporadaList.add(dadosTemp);
        }

        System.out.println("\nDados Por Temporada:\n");
        //dadosTemporadaList.forEach(System.out::println);

        for (DadosTemporada dadosTemp : dadosTemporadaList) {
             for(DadosEpisodio dadosEpisodio : dadosTemp.listaEpisodios()) {
                 System.out.println("S" + dadosTemp.numero() + "E" + dadosEpisodio.numero() + ": " + dadosEpisodio.titulo());
             }
        }

        System.out.println("\nDados Por Temporada - Versão Lambda:\n");
        dadosTemporadaList.forEach( dadosTemporada -> dadosTemporada.listaEpisodios().forEach(System.out::println));

    }

}
