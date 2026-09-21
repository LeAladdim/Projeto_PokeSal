package pokeucsal;

import java.util.Random;
import java.util.Scanner;

/**
 * Controla as batalhas (PvE e PvP), o terreno e a ordem dos ataques em cada turno.
 */
public class Batalha {

    public static final int PAUSA_CURTA_MS = 1500;
    public static final int PAUSA_LONGA_MS = 2000;

    private static final double CURA_CANTEIRO_CENTRAL = 0.05;

    private String climaAtual = "Asfalto Quente";

    /**
     * Pausa a execução para o jogador conseguir ler as mensagens.
     *
     * @param milissegundos tempo da pausa
     */
    public static void pausar(final int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Sorteia um terreno diferente do atual (efeito do item Sal Shard).
     */
    public void mudarTerreno() {
        final String[] climasPossiveis =
            {"Asfalto Quente", "Piso Escorregadio", "Canteiro Central"};
        final String[] opcoes = new String[2];
        int idx = 0;

        for (final String clima : climasPossiveis) {
            if (!clima.equals(this.climaAtual)) {
                opcoes[idx] = clima;
                idx++;
            }
        }

        final Random rand = new Random();
        this.climaAtual = opcoes[rand.nextInt(2)];

        switch (this.climaAtual) {
            case "Asfalto Quente":
                System.out.print("\u001B[31m");
                break;
            case "Piso Escorregadio":
                System.out.print("\u001B[34m");
                break;
            case "Canteiro Central":
                System.out.print("\u001B[32m");
                break;
            default:
                break;
        }
        System.out.println(
            "\nA Sal Shard brilhou! O Campo de Batalha Agora é O " + this.climaAtual + "!");
    }

    /**
     * Aplica os efeitos do fim do turno: cura do Canteiro Central e dano de status.
     *
     * @param p1 primeiro PokéSal da batalha
     * @param p2 segundo PokéSal da batalha
     */
    public void processarFimDeTurno(final Pokemon p1, final Pokemon p2) {
        System.out.println("\n--- Fim de Turno ---");

        if (climaAtual.equals("Canteiro Central")) {
            if (p1.getTipo().getNomeTipo().equals("Planta") && p1.getHp() > 0) {
                p1.curar(CURA_CANTEIRO_CENTRAL);
                System.out.println("O Canteiro Central Restaurou a Vida de " + p1.getNome() + "!");
            }
            if (p2.getTipo().getNomeTipo().equals("Planta") && p2.getHp() > 0) {
                p2.curar(CURA_CANTEIRO_CENTRAL);
                System.out.println("O Canteiro Central Restaurou a Vida De " + p2.getNome() + "!");
            }
        }

        if (p1.getHp() > 0) {
            p1.processarStatusFimDeTurno();
        }
        if (p2.getHp() > 0) {
            p2.processarStatusFimDeTurno();
        }
    }

    /**
     * Executa os golpes do turno. Quem tem mais velocidade age primeiro.
     *
     * @param jogador PokéSal do jogador
     * @param golpeJogador golpe escolhido pelo jogador
     * @param inimigo PokéSal adversário
     * @param golpeInimigo golpe escolhido pelo adversário
     */
    public void resolverRodadaDeAtaques(final Pokemon jogador, final Golpe golpeJogador,
                                        final Pokemon inimigo, final Golpe golpeInimigo) {
        final Pokemon primeiro;
        final Pokemon segundo;
        final Golpe acaoPrimeiro;
        final Golpe acaoSegundo;

        if (jogador.getSpd() >= inimigo.getSpd()) {
            primeiro = jogador;
            acaoPrimeiro = golpeJogador;
            segundo = inimigo;
            acaoSegundo = golpeInimigo;
        } else {
            primeiro = inimigo;
            acaoPrimeiro = golpeInimigo;
            segundo = jogador;
            acaoSegundo = golpeJogador;
        }
        Batalha.pausar(PAUSA_CURTA_MS);
        System.out.println("\n--- Ordem de Ataque ---");
        System.out.println(primeiro.getNome() + " é Mais Rápido e Toma a Frente!");
        Batalha.pausar(PAUSA_CURTA_MS);

        executarGolpeComClima(primeiro, segundo, acaoPrimeiro);

        if (segundo.getHp() > 0) {
            System.out.println("\nContra-ataque de " + segundo.getNome() + "!");
            Batalha.pausar(PAUSA_CURTA_MS);
            executarGolpeComClima(segundo, primeiro, acaoSegundo);
        } else {
            System.out.println("\n" + segundo.getNome() + " Desmaiou Antes de Conseguir Atacar!");
            Batalha.pausar(PAUSA_CURTA_MS);
        }

        processarFimDeTurno(jogador, inimigo);
    }

    private void executarGolpeComClima(final Pokemon atacante, final Pokemon defensor,
                                       final Golpe golpe) {
        if (golpe instanceof Golpe.Elemental) {
            ((Golpe.Elemental) golpe).executar(atacante, defensor, climaAtual);
        } else {
            golpe.executar(atacante, defensor);
        }
    }

    /**
     * Roda uma batalha do modo PvE (jogador contra computador).
     *
     * @param jogador PokéSal do jogador
     * @param inimigo PokéSal adversário
     * @param mochila mochila de itens do jogador
     * @return {@code true} se o jogador venceu, senão {@code false}
     */
    public boolean iniciarCombate(final Pokemon jogador, final Pokemon inimigo, final Bag mochila) {
        mochila.resetarUsoBatalha();

        final Scanner sc = new Scanner(System.in);
        System.out.println("\nUm " + inimigo.getNome() + " Adversário se Aproxima!");
        Batalha.pausar(PAUSA_CURTA_MS);

        inimigo.curar(1.0);
        inimigo.exibirSts();
        Batalha.pausar(PAUSA_CURTA_MS);

        final String[] climasPossiveis =
            {"Asfalto Quente", "Piso Escorregadio", "Canteiro Central"};
        final Random rand = new Random();
        this.climaAtual = climasPossiveis[rand.nextInt(climasPossiveis.length)];

        switch (this.climaAtual) {
            case "Asfalto Quente":
                System.out.print("\u001B[31m");
                break;
            case "Piso Escorregadio":
                System.out.print("\u001B[34m");
                break;
            case "Canteiro Central":
                System.out.print("\u001B[32m");
                break;
            default:
                break;
        }

        System.out.println("Condição do Ambiente de Combate: " + climaAtual + "!");
        Batalha.pausar(PAUSA_LONGA_MS);

        while (jogador.getHp() > 0 && inimigo.getHp() > 0) {
            System.out.println("\nHP: " + jogador.getHp() + " | Inimigo: " + inimigo.getHp());
            System.out.println("1 - Atacar");
            System.out.println("2 - Mochila");
            final int acao = sc.nextInt();

            switch (acao) {
                case 1:
                    System.out.println("\nEscolha um Golpe:");
                    final Golpe[] golpesJogador = jogador.getGolpes();
                    for (int i = 0; i < golpesJogador.length; i++) {
                        System.out.println((i + 1) + " - " + golpesJogador[i].getNome());
                    }

                    int esc = sc.nextInt() - 1;
                    while (esc < 0 || esc >= golpesJogador.length) {
                        System.out.println(
                            "Opção Inválida! Digite um número entre 1 e " + golpesJogador.length
                                + ":");
                        esc = sc.nextInt() - 1;
                    }
                    final Golpe golpeEscolhido = golpesJogador[esc];

                    final Golpe golpeInimigo =
                        inimigo.getGolpes()[rand.nextInt(inimigo.getGolpes().length)];

                    resolverRodadaDeAtaques(jogador, golpeEscolhido, inimigo, golpeInimigo);
                    break;
                case 2:
                    final boolean turnoConsumido = mochila.abrirMochila(jogador, this);
                    if (!turnoConsumido) {
                        continue;
                    }
                    if (inimigo.getHp() > 0) {
                        System.out.println("\n--- Turno de " + inimigo.getNome() + " ---");
                        Batalha.pausar(PAUSA_LONGA_MS);
                        final Golpe golpeResposta =
                            inimigo.getGolpes()[rand.nextInt(inimigo.getGolpes().length)];
                        executarGolpeComClima(inimigo, jogador, golpeResposta);
                        Batalha.pausar(PAUSA_LONGA_MS);
                        processarFimDeTurno(jogador, inimigo);
                    }
                    break;
                default:
                    break;
            }
        }

        if (jogador.getHp() > 0) {
            System.out.println("Você Venceu a Batalha!");
            Batalha.pausar(PAUSA_LONGA_MS);
            System.out.print("\u001B[32m");
            return true;
        } else {
            System.out.println("Seu PokeSal Desmaiou...");
            Batalha.pausar(PAUSA_LONGA_MS);
            System.out.print("\u001B[32m");
            return false;
        }
    }

    /**
     * Roda uma batalha do modo PvP (jogador contra jogador).
     *
     * @param p1 PokéSal do jogador 1
     * @param p2 PokéSal do jogador 2
     * @param bag1 mochila do jogador 1
     * @param bag2 mochila do jogador 2
     */
    public void iniciarCombatePvP(final Pokemon p1, final Pokemon p2, final Bag bag1,
                                  final Bag bag2) {
        bag1.resetarUsoBatalha();
        bag2.resetarUsoBatalha();
        final Scanner sc = new Scanner(System.in);
        System.out.println(
            "\nBatalha PvP iniciada entre " + p1.getNome() + " e " + p2.getNome() + "!");
        Batalha.pausar(PAUSA_LONGA_MS);

        p1.exibirSts();
        Batalha.pausar(PAUSA_LONGA_MS);

        p2.exibirSts();
        Batalha.pausar(PAUSA_LONGA_MS);

        final String[] climasPossiveis =
            {"Asfalto Quente", "Piso Escorregadio", "Canteiro Central"};
        final Random rand = new Random();
        this.climaAtual = climasPossiveis[rand.nextInt(climasPossiveis.length)];

        switch (this.climaAtual) {
            case "Asfalto Quente":
                System.out.print("\u001B[31m");
                break;
            case "Piso Escorregadio":
                System.out.print("\u001B[34m");
                break;
            case "Canteiro Central":
                System.out.print("\u001B[32m");
                break;
            default:
                break;
        }

        System.out.println("Condição do Ambiente de Combate: " + climaAtual + "!");

        while (p1.getHp() > 0 && p2.getHp() > 0) {
            Golpe golpeP1 = null;
            boolean turnoP1Consumido = false;

            while (!turnoP1Consumido && golpeP1 == null) {
                System.out.println("\n--- Turno de P1 (" + p1.getNome() + ") ---");
                System.out.println("HP P1: " + p1.getHp() + " | HP P2: " + p2.getHp());
                System.out.println("1 - Atacar");
                System.out.println("2 - Mochila");
                final int acao1 = sc.nextInt();

                switch (acao1) {
                    case 1:
                        System.out.println("\nP1, Escolha um Golpe:");
                        final Golpe[] golpesP1 = p1.getGolpes();
                        for (int i = 0; i < golpesP1.length; i++) {
                            System.out.println((i + 1) + " - " + golpesP1[i].getNome());
                        }

                        int esc1 = sc.nextInt() - 1;
                        while (esc1 < 0 || esc1 >= golpesP1.length) {
                            System.out.println(
                                "Opção Inválida! Digite um número entre 1 e " + golpesP1.length
                                    + ":");
                            esc1 = sc.nextInt() - 1;
                        }
                        golpeP1 = golpesP1[esc1];
                        break;
                    case 2:
                        turnoP1Consumido = bag1.abrirMochila(p1, this);
                        break;
                    default:
                        break;
                }
            }

            if (p2.getHp() <= 0) {
                break;
            }

            Golpe golpeP2 = null;
            boolean turnoP2Consumido = false;

            while (!turnoP2Consumido && golpeP2 == null) {
                System.out.println("\n--- Turno de P2 (" + p2.getNome() + ") ---");
                System.out.println("HP P1: " + p1.getHp() + " | HP P2: " + p2.getHp());
                System.out.println("1 - Atacar");
                System.out.println("2 - Mochila");
                final int acao2 = sc.nextInt();

                switch (acao2) {
                    case 1:
                        System.out.println("\nP2, Escolha um Golpe:");
                        final Golpe[] golpesP2 = p2.getGolpes();
                        for (int i = 0; i < golpesP2.length; i++) {
                            System.out.println((i + 1) + " - " + golpesP2[i].getNome());
                        }

                        int esc2 = sc.nextInt() - 1;
                        while (esc2 < 0 || esc2 >= golpesP2.length) {
                            System.out.println(
                                "Opção Inválida! Digite um número entre 1 e " + golpesP2.length
                                    + ":");
                            esc2 = sc.nextInt() - 1;
                        }
                        golpeP2 = golpesP2[esc2];
                        break;
                    case 2:
                        turnoP2Consumido = bag2.abrirMochila(p2, this);
                        break;
                    default:
                        break;
                }
            }

            if (golpeP1 != null && golpeP2 != null) {
                resolverRodadaDeAtaques(p1, golpeP1, p2, golpeP2);
            } else {
                if (golpeP1 != null && p2.getHp() > 0) {
                    System.out.println("\n--- Turno de " + p1.getNome() + " ---");
                    Batalha.pausar(PAUSA_CURTA_MS);
                    executarGolpeComClima(p1, p2, golpeP1);
                } else if (golpeP2 != null && p1.getHp() > 0) {
                    System.out.println("\n--- Turno de " + p2.getNome() + " ---");
                    Batalha.pausar(PAUSA_CURTA_MS);
                    executarGolpeComClima(p2, p1, golpeP2);
                }
                processarFimDeTurno(p1, p2);
            }
        }

        if (p1.getHp() > 0) {
            System.out.println("Jogador 1 Venceu a Batalha!");
            Batalha.pausar(PAUSA_LONGA_MS);
        } else {
            System.out.println("Jogador 2 Venceu a Batalha!");
            Batalha.pausar(PAUSA_LONGA_MS);
        }
        System.out.print("\u001B[32m");
    }
}
