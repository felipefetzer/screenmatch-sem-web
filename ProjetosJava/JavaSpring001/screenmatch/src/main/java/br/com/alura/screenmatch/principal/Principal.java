package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

        List<String> epNames = dadosTemporadaList.stream().flatMap(dadosTemporada -> dadosTemporada.listaEpisodios().stream()).map(DadosEpisodio::titulo).toList();
        epNames.forEach(System.out::println);

        System.out.println("\nTop 10 episódios\n");
        dadosTemporadaList.stream().flatMap(dadosTemporada -> dadosTemporada.listaEpisodios().stream())
                .filter(dadosEpisodio -> !dadosEpisodio.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro filtro (N/A) - " + e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenação - " + e))
                .limit(10)
                .peek(e -> System.out.println("Limite - " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Map - " + e))
                .forEach(System.out::println);
                //.forEach(d -> System.out.println("Nome: " + d.titulo() + " - Avaliaçao: " + d.avaliacao() ));

        List<Episodio> episodios = dadosTemporadaList.stream()
                .flatMap(t -> t.listaEpisodios().stream().map(d -> new Episodio(t.numero(), d))).toList();
        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios?");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream().filter(episodio -> episodio.getDataLancamento()!=null && episodio.getDataLancamento().isAfter(dataBusca))
                .forEach(e ->
                        System.out.println("S"+e.getTemporada()
                                +"E"+e.getNumero()
                                +" - " + e.getTitulo()
                                + " - Data: "+ e.getDataLancamento().format(formatador)));

        System.out.println("Digite o nome do episódio para buscar:");
        var trechoTitulo = leitura.nextLine();

        Optional<Episodio> epBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();

        System.out.println("Episódio localizado: " + (epBuscado.isPresent()?epBuscado.get().getTitulo() : "Episódio não encontrado."));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(episodio -> episodio.getAvaliacao()>0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream().filter(episodio -> episodio.getAvaliacao()>0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Media: " + est.getAverage());
        System.out.println("Melhor Episódio: " + est.getMax());
        System.out.println("Pior Episódio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());

    }

}
