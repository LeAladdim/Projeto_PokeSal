package pokeucsal;

import java.util.Random;
import java.util.Scanner;

public class Batalha {

    private String climaAtual = "Normal";

    public static void pausar(final int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void mudarTerreno(final String novoTerreno) {
        switch (novoTerreno) {
            case "Fogo":
                this.climaAtual = "Asfalto Quente";
                break;
            case "Agua":
            case "Água":
                this.climaAtual = "Piso Escorregadio";
                break;
            case "Planta":
                this.climaAtual = "Canteiro Central";
                break;
            default:
                this.climaAtual = novoTerreno;
                break;
        }
        System.out.println(
            "\nO Sal Shard brilhou! O campo de batalha agora e o " + this.climaAtual + "!");
    }

    public void processarFimDeTurno(final Pokemon p1, final Pokemon p2) {
        System.out.println("\n--- Fim de Turno ---");

        if (climaAtual.equals("Canteiro Central")) {
            if (p1.getTipo().getNomeTipo().equals("Planta") && p1.getHp() > 0) {
                p1.curar(0.05);
            }
            if (p2.getTipo().getNomeTipo().equals("Planta") && p2.getHp() > 0) {
                p2.curar(0.05);
            }
        }

        if (p1.getHp() > 0) {
            p1.processarStatusFimDeTurno();
        }
        if (p2.getHp() > 0) {
            p2.processarStatusFimDeTurno();
        }
    }

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
        Batalha.pausar(1500);
        System.out.println("\n--- Ordem de Ataque (Baseada em SPD) ---");
        System.out.println(primeiro.getNome() + " e mais rapido e age primeiro!");
        Batalha.pausar(1500);

        // Executa o primeiro e imprime o dano/efeito imediatamente
        executarGolpeComClima(primeiro, segundo, acaoPrimeiro);

        // O segundo só ataca se sobreviveu ao primeiro impacto
        if (segundo.getHp() > 0) {
            System.out.println("\nContra-ataque de " + segundo.getNome() + "!");
            Batalha.pausar(1500);
            executarGolpeComClima(segundo, primeiro, acaoSegundo);
        } else {
            System.out.println("\n" + segundo.getNome() + " desmaiou antes de conseguir atacar!");
            Batalha.pausar(1500);
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

    public boolean iniciarCombate(final Pokemon jogador, final Pokemon inimigo, final Bag mochila) {
        // Garante que o uso dos itens da mochila é totalmente resetado no início de cada luta
        mochila.resetarUsoBatalha();

        final Scanner sc = new Scanner(System.in);
        System.out.println("\nUm " + inimigo.getNome() + " adversario se aproxima!");
        Batalha.pausar(1500);

        // Exibe os status do Pokemon inimigo selvagem
        inimigo.exibirSts();
        Batalha.pausar(1500);

        final String[] climasPossiveis =
            {"Normal", "Asfalto Quente", "Piso Escorregadio", "Canteiro Central"};
        final Random rand = new Random();
        this.climaAtual = climasPossiveis[rand.nextInt(climasPossiveis.length)];

        System.out.println("Condicao do ambiente de combate: " + climaAtual + "!");
        Batalha.pausar(2000);

        while (jogador.getHp() > 0 && inimigo.getHp() > 0) {
            System.out.println("\nHP: " + jogador.getHp() + " | Inimigo: " + inimigo.getHp());
            System.out.println("1 - Atacar");
            System.out.println("2 - Mochila");
            final int acao = sc.nextInt();

            if (acao == 1) {
                System.out.println("\nEscolha um golpe:");
                final Golpe[] golpesJogador = jogador.getGolpes();
                for (int i = 0; i < golpesJogador.length; i++) {
                    System.out.println((i + 1) + " - " + golpesJogador[i].getNome());
                }
                Batalha.pausar(1500);
                int esc = sc.nextInt() - 1;
                if (esc < 0 || esc >= golpesJogador.length) {
                    esc = 0;
                }
                final Golpe golpeEscolhido = golpesJogador[esc];

                final Golpe golpeInimigo =
                    inimigo.getGolpes()[rand.nextInt(inimigo.getGolpes().length)];

                resolverRodadaDeAtaques(jogador, golpeEscolhido, inimigo, golpeInimigo);

            } else if (acao == 2) {
                final boolean turnoConsumido = mochila.abrirMochila(jogador, this);
                if (!turnoConsumido) {
                    continue;
                }
                if (inimigo.getHp() > 0) {
                    System.out.println("\nTurno do Inimigo:");
                    Batalha.pausar(2000);
                    final Golpe golpeInimigo =
                        inimigo.getGolpes()[rand.nextInt(inimigo.getGolpes().length)];
                    executarGolpeComClima(inimigo, jogador, golpeInimigo);
                    Batalha.pausar(2000);
                    processarFimDeTurno(jogador, inimigo);
                }
            }
        }

        if (jogador.getHp() > 0) {
            System.out.println("Voce venceu a batalha!");
            Batalha.pausar(2000);
            return true;
        } else {
            System.out.println("Seu Pokemon desmaiou...");
            Batalha.pausar(2000);
            return false;
        }
    }

    public void iniciarCombatePvP(final Pokemon p1, final Pokemon p2, final Bag bag1,
                                  final Bag bag2) {
        bag1.resetarUsoBatalha();
        bag2.resetarUsoBatalha();
        final Scanner sc = new Scanner(System.in);
        System.out.println(
            "\nBatalha PvP iniciada entre " + p1.getNome() + " e " + p2.getNome() + "!");
        Batalha.pausar(2000);

        // Exibe os status de ambos os Pokémon no PvP
        p1.exibirSts();
        Batalha.pausar(2000);

        p2.exibirSts();
        Batalha.pausar(2000);

        final String[] climasPossiveis =
            {"Normal", "Asfalto Quente", "Piso Escorregadio", "Canteiro Central"};
        final Random rand = new Random();
        this.climaAtual = climasPossiveis[rand.nextInt(climasPossiveis.length)];

        if (!climaAtual.equals("Normal")) {
            System.out.println("Condicao do Ambiente de Combate: " + climaAtual + "!");
        }

        while (p1.getHp() > 0 && p2.getHp() > 0) {
            Golpe golpeP1 = null;
            boolean turnoP1Consumido = false;

            while (!turnoP1Consumido && golpeP1 == null) {
                System.out.println("\n--- Turno de P1 (" + p1.getNome() + ") ---");
                System.out.println("HP P1: " + p1.getHp() + " | HP P2: " + p2.getHp());
                System.out.println("1 - Atacar");
                System.out.println("2 - Mochila");
                final int acao1 = sc.nextInt();

                if (acao1 == 1) {
                    System.out.println("\nP1, escolha um golpe:");
                    Batalha.pausar(1000);
                    final Golpe[] golpesP1 = p1.getGolpes();
                    for (int i = 0; i < golpesP1.length; i++) {
                        System.out.println((i + 1) + " - " + golpesP1[i].getNome());
                    }
                    int esc1 = sc.nextInt() - 1;
                    if (esc1 < 0 || esc1 >= golpesP1.length) {
                        esc1 = 0;
                    }
                    golpeP1 = golpesP1[esc1];
                } else if (acao1 == 2) {
                    turnoP1Consumido = bag1.abrirMochila(p1, this);
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

                if (acao2 == 1) {
                    System.out.println("\nP2, escolha um golpe:");
                    Batalha.pausar(1000);
                    final Golpe[] golpesP2 = p2.getGolpes();
                    for (int i = 0; i < golpesP2.length; i++) {
                        System.out.println((i + 1) + " - " + golpesP2[i].getNome());
                    }
                    int esc2 = sc.nextInt() - 1;
                    if (esc2 < 0 || esc2 >= golpesP2.length) {
                        esc2 = 0;
                    }
                    golpeP2 = golpesP2[esc2];
                } else if (acao2 == 2) {
                    turnoP2Consumido = bag2.abrirMochila(p2, this);
                }
            }

            if (golpeP1 != null && golpeP2 != null) {
                resolverRodadaDeAtaques(p1, golpeP1, p2, golpeP2);
            } else {
                if (golpeP1 != null && p2.getHp() > 0) {
                    System.out.println("\n" + p1.getNome() + " ataca!");
                    executarGolpeComClima(p1, p2, golpeP1);
                } else if (golpeP2 != null && p1.getHp() > 0) {
                    System.out.println("\n" + p2.getNome() + " ataca!");
                    executarGolpeComClima(p2, p1, golpeP2);
                }
                processarFimDeTurno(p1, p2);
            }
        }

        if (p1.getHp() > 0) {
            System.out.println("Jogador 1 venceu a batalha!");
            Batalha.pausar(2000);
        } else {
            System.out.println("Jogador 2 venceu a batalha!");
            Batalha.pausar(2000);
        }
    }
}