package pokeucsal;
import java.util.Scanner;
import java.util.Random;

public class Batalha {
    private String terrenoAtual = "Normal";
    private String climaAtual = "Normal"; // "Asfalto Quente", "Piso Escorregadio", "Canteiro Central"

    public void mudarTerreno(String novoTerreno) {
        this.terrenoAtual = novoTerreno;
        System.out.println("\nO Sal Shard brilhou! O campo de batalha agora favorece o tipo " + novoTerreno + "!");
    }
    // Método auxiliar para pausar a execução
    public static void pausar(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    // Processa os eventos de fim de turno (Clima Canteiro Central + Status Passivos)
    public void processarFimDeTurno(Pokemon p1, Pokemon p2) {
        System.out.println("\n--- Fim de Turno ---");

        // Regra do Canteiro Central: Planta recupera 5% do HP máximo
        if (climaAtual.equals("Canteiro Central")) {
            if (p1.getTipo().getNomeTipo().equals("Planta") && p1.getHp() > 0) {
                p1.curar(0.05);
            }
            if (p2.getTipo().getNomeTipo().equals("Planta") && p2.getHp() > 0) {
                p2.curar(0.05);
            }
        }

        // Processa status passivos
        if (p1.getHp() > 0) p1.processarStatusFimDeTurno();
        if (p2.getHp() > 0) p2.processarStatusFimDeTurno();
    }

    public void resolverRodadaDeAtaques(Pokemon jogador, Golpe golpeJogador, Pokemon inimigo, Golpe golpeInimigo) {
        Pokemon primeiro, segundo;
        Golpe acaoPrimeiro, acaoSegundo;

        if (jogador.getSpd() >= inimigo.getSpd()) {
            primeiro = jogador; acaoPrimeiro = golpeJogador;
            segundo = inimigo; acaoSegundo = golpeInimigo;
        } else {
            primeiro = inimigo; acaoPrimeiro = golpeInimigo;
            segundo = jogador; acaoSegundo = golpeJogador;
        }

        // Executa o primeiro ataque
        executarGolpeComClima(primeiro, segundo, acaoPrimeiro);

        // O segundo só ataca se sobreviveu
        if (segundo.getHp() > 0) {
            executarGolpeComClima(segundo, primeiro, acaoSegundo);
        } else {
            System.out.println("\n" + segundo.getNome() + " desmaiou antes de conseguir atacar!");
        }

        // Processa os efeitos do fim da rodada
        processarFimDeTurno(jogador, inimigo);
    }

    // Método auxiliar para injetar o clima caso o golpe seja elemental
    private void executarGolpeComClima(Pokemon atacante, Pokemon defensor, Golpe golpe) {
        if (golpe instanceof Golpe.Elemental) {
            ((Golpe.Elemental) golpe).executar(atacante, defensor, climaAtual);
        } else {
            golpe.executar(atacante, defensor);
        }
    }

    public boolean iniciarCombate(Pokemon jogador, Pokemon inimigo, Bag mochila) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nUm " + inimigo.getNome() + " adversário se aproxima!");

        // Sorteia um clima/terreno ambiental aleatório para a luta
        String[] climasPossiveis = {"Normal", "Asfalto Quente", "Piso Escorregadio", "Canteiro Central"};
        Random rand = new Random();
        this.climaAtual = climasPossiveis[rand.nextInt(climasPossiveis.length)];

        if (!climaAtual.equals("Normal")) {
            System.out.println("🌍 Condição do ambiente de combate: " + climaAtual + "!");
        }

        while (jogador.getHp() > 0 && inimigo.getHp() > 0) {
            System.out.println("\nHP: " + jogador.getHp() + " | Inimigo: " + inimigo.getHp());
            System.out.println("1 - Atacar");
            System.out.println("2 - Mochila");
            int acao = sc.nextInt();

            if (acao == 1) {
                System.out.println("\nEscolha um golpe:");
                Golpe[] golpesJogador = jogador.getGolpes();
                for (int i = 0; i < golpesJogador.length; i++) {
                    System.out.println((i + 1) + " - " + golpesJogador[i].getNome());
                }

                int esc = sc.nextInt() - 1;
                if (esc < 0 || esc >= golpesJogador.length) esc = 0;
                Golpe golpeEscolhido = golpesJogador[esc];

                Golpe golpeInimigo = inimigo.getGolpes()[rand.nextInt(inimigo.getGolpes().length)];

                resolverRodadaDeAtaques(jogador, golpeEscolhido, inimigo, golpeInimigo);

            } else if (acao == 2) {
                boolean turnoConsumido = mochila.abrirMochila(jogador, this);
                if (turnoConsumido && inimigo.getHp() > 0) {
                    System.out.println("\nTurno do Inimigo:");
                    Golpe golpeInimigo = inimigo.getGolpes()[rand.nextInt(inimigo.getGolpes().length)];
                    executarGolpeComClima(inimigo, jogador, golpeInimigo);
                    processarFimDeTurno(jogador, inimigo);
                }
            }
        }

        if (jogador.getHp() > 0) {
            System.out.println("Você venceu a batalha!");
            return true;
        } else {
            System.out.println("Seu Pokémon desmaiou...");
            return false;
        }
    }
}