package pokeucsal;

import java.util.Random;
import java.util.Scanner;

public class Batalha {

    private String terrenoAtual = "Normal";

    private String climaAtual = "Normal";

    public static void pausar(final int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void mudarTerreno(final String novoTerreno) {
        this.terrenoAtual = novoTerreno;
        System.out.println(
            "\nO Sal Shard brilhou! O campo de batalha agora favorece o tipo " + novoTerreno + "!");
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
        mochila.resetarUsoBatalha();
        final Scanner sc = new Scanner(System.in);
        System.out.println("\nUm " + inimigo.getNome() + " adversario se aproxima!");
        Batalha.pausar(1500);

        final String[] climasPossiveis =
            {"Normal", "Asfalto Quente", "Piso Escorregadio", "Canteiro Central"};
        final Random rand = new Random();
        this.climaAtual = climasPossiveis[rand.nextInt(climasPossiveis.length)];

        if (!climaAtual.equals("Normal")) {
            System.out.println("Condicao do ambiente de combate: " + climaAtual + "!");
            Batalha.pausar(2000);
        }

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
                if (turnoConsumido && inimigo.getHp() > 0) {
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

    public boolean iniciarCombatePvP(final Pokemon p1, final Pokemon p2, final Bag bag1,
                                     final Bag bag2) {
        bag1.resetarUsoBatalha();
        bag2.resetarUsoBatalha();
        final Scanner sc = new Scanner(System.in);
        System.out.println(
            "\nBatalha PvP iniciada entre " + p1.getNome() + " e " + p2.getNome() + "!");
        Batalha.pausar(2000);

        final String[] climasPossiveis =
            {"Normal", "Asfalto Quente", "Piso Escorregadio", "Canteiro Central"};
        final Random rand = new Random();
        this.climaAtual = climasPossiveis[rand.nextInt(climasPossiveis.length)];

        if (!climaAtual.equals("Normal")) {
            System.out.println("Condicao do ambiente de combate: " + climaAtual + "!");
        }

        while (p1.getHp() > 0 && p2.getHp() > 0) {
            System.out.println("\n--- Turno de P1 (" + p1.getNome() + ") ---");
            System.out.println("HP P1: " + p1.getHp() + " | HP P2: " + p2.getHp());
            System.out.println("1 - Atacar");
            System.out.println("2 - Mochila");
            final int acao1 = sc.nextInt();

            Golpe golpeP1 = null;
            boolean acaoP1Valida = false;

            if (acao1 == 1) {
                System.out.println("\nP1, escolha um golpe:");
                Batalha.pausar(2000);
                final Golpe[] golpesP1 = p1.getGolpes();
                for (int i = 0; i < golpesP1.length; i++) {
                    System.out.println((i + 1) + " - " + golpesP1[i].getNome());
                }
                final int esc1 = sc.nextInt() - 1;
                final int idx1 = (esc1 < 0 || esc1 >= golpesP1.length) ? 0 : esc1;
                golpeP1 = golpesP1[idx1];
                acaoP1Valida = true;
            } else if (acao1 == 2) {
                acaoP1Valida = bag1.abrirMochila(p1, this);
                if (!acaoP1Valida) {
                    continue;
                }
            } else {
                continue;
            }

            if (p2.getHp() <= 0) {
                break;
            }

            System.out.println("\n--- Turno de P2 (" + p2.getNome() + ") ---");
            System.out.println("HP P1: " + p1.getHp() + " | HP P2: " + p2.getHp());
            System.out.println("1 - Atacar");
            System.out.println("2 - Mochila");
            final int acao2 = sc.nextInt();

            Golpe golpeP2 = null;
            boolean acaoP2Valida = false;

            if (acao2 == 1) {
                System.out.println("\nP2, escolha um golpe:");
                final Golpe[] golpesP2 = p2.getGolpes();
                for (int i = 0; i < golpesP2.length; i++) {
                    System.out.println((i + 1) + " - " + golpesP2[i].getNome());
                }
                final int esc2 = sc.nextInt() - 1;
                final int idx2 = (esc2 < 0 || esc2 >= golpesP2.length) ? 0 : esc2;
                golpeP2 = golpesP2[idx2];
                acaoP2Valida = true;
            } else if (acao2 == 2) {
                acaoP2Valida = bag2.abrirMochila(p2, this);
                if (!acaoP2Valida) {
                    continue;
                }
            } else {
                continue;
            }

            if (acaoP1Valida && acaoP2Valida && golpeP1 != null && golpeP2 != null) {
                resolverRodadaDeAtaques(p1, golpeP1, p2, golpeP2);
            }
        }

        if (p1.getHp() > 0) {
            System.out.println("Jogador 1 venceu a batalha!");
            return true;
        } else {
            System.out.println("Jogador 2 venceu a batalha!");
            return false;
        }
    }
}