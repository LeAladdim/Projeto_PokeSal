package pokeucsal;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(final String[] args) {
        System.out.print("\u001B[32m");
        menuP();
    }

    public static void menuP() {
        final Scanner sc = new Scanner(System.in);

        final Golpe[] golpesPlanta = {
            new Golpe.Normal("Chicote", 18),
            new Golpe.Elemental("Cansanção", 30),
            new Golpe.Buff("Fotossíntese"),
            new Golpe.Debuff("Tempestade de Folhas", 1)
        };

        final Golpe[] golpesFogo = {
            new Golpe.Normal("Arranhão", 18),
            new Golpe.Elemental("Lança-Chamas", 30),
            new Golpe.Buff("Dança das Chamas"),
            new Golpe.Debuff("Superaquecer", 1)
        };

        final Golpe[] golpesAgua = {
            new Golpe.Normal("Impacto Repentino", 18),
            new Golpe.Elemental("Jato de Água", 30),
            new Golpe.Buff("Dança da Chuva"),
            new Golpe.Debuff("Gêiser Submerso", 1)
        };

        final Pokemon bulbasal = new Pokemon("BulbaSal", new TipoPlanta(), 49, 33, 45, 45,
            golpesPlanta);
        final Pokemon charsal = new Pokemon("CharSal", new TipoFogo(), 51, 32, 40, 42,
            golpesFogo);
        final Pokemon squirtsal = new Pokemon("SquirtSal", new TipoAgua(), 48, 32, 44, 43,
            golpesAgua);
        final Pokemon chikosal = new Pokemon("ChikoSal", new TipoPlanta(), 49, 34, 45, 45,
            golpesPlanta);
        final Pokemon cyndasal = new Pokemon("CyndaSal", new TipoFogo(), 52, 31, 39, 41,
            golpesFogo);
        final Pokemon totosal = new Pokemon("TotoSal", new TipoAgua(), 49, 32, 45, 45,
            golpesAgua);

        final Pokemon[] todosIniciais = {bulbasal, charsal, squirtsal, chikosal, cyndasal,
            totosal};

        boolean rodandoJogo = true;

        while (rodandoJogo) {
            for (final Pokemon p : todosIniciais) {
                p.curar(1.0);
            }

            System.out.println(
                "\n*** O Torneio do Estacionamento da UCSal Está Para Começar! ***");
            System.out.println("Escolha o Modo de Jogo:");
            System.out.println("1 - Jogador vs Computador (PvE)");
            System.out.println("2 - Jogador vs Jogador (PvP)");
            final int modoJogo = sc.nextInt();

            switch (modoJogo) {
                case 2:
                    System.out.println(
                        "\n****************** Seleção do Jogador 1 ******************");
                    System.out.println("(1 - BulbaSal)");
                    System.out.println("(2 - CharSal)");
                    System.out.println("(3 - SquirtSal)");
                    System.out.println("(4 - ChikoSal)");
                    System.out.println("(5 - CyndaSal)");
                    System.out.println("(6 - TotoSal)");
                    System.out.println("(Digite o Número do Seu PokeSal)");
                    final int p1Escolha = sc.nextInt();
                    Pokemon p1 = null;
                    switch (p1Escolha) {
                        case 1:
                            p1 = new Pokemon("BulbaSal", new TipoPlanta(), 49, 33, 45, 45,
                                golpesPlanta);
                            break;
                        case 2:
                            p1 = new Pokemon("CharSal", new TipoFogo(), 51, 32, 40, 42, golpesFogo);
                            break;
                        case 3:
                            p1 = new Pokemon("SquirtSal", new TipoAgua(), 48, 32, 44, 43,
                                golpesAgua);
                            break;
                        case 4:
                            p1 = new Pokemon("ChikoSal", new TipoPlanta(), 49, 34, 45, 45,
                                golpesPlanta);
                            break;
                        case 5:
                            p1 =
                                new Pokemon("CyndaSal", new TipoFogo(), 52, 31, 39, 41, golpesFogo);
                            break;
                        case 6:
                            p1 = new Pokemon("TotoSal", new TipoAgua(), 49, 32, 45, 45, golpesAgua);
                            break;
                        default:
                            break;
                    }

                    System.out.println(
                        "\n****************** Seleção do Jogador 2 ******************");
                    System.out.println("(1 - BulbaSal)");
                    System.out.println("(2 - CharSal)");
                    System.out.println("(3 - SquirtSal)");
                    System.out.println("(4 - ChikoSal)");
                    System.out.println("(5 - CyndaSal)");
                    System.out.println("(6 - TotoSal)");
                    System.out.println("(Digite o Número do Seu PokeSal)");
                    final int p2Escolha = sc.nextInt();
                    Pokemon p2 = null;
                    switch (p2Escolha) {
                        case 1:
                            p2 = new Pokemon("BulbaSal", new TipoPlanta(), 49, 33, 45, 45,
                                golpesPlanta);
                            break;
                        case 2:
                            p2 = new Pokemon("CharSal", new TipoFogo(), 51, 32, 40, 42, golpesFogo);
                            break;
                        case 3:
                            p2 = new Pokemon("SquirtSal", new TipoAgua(), 48, 32, 44, 43,
                                golpesAgua);
                            break;
                        case 4:
                            p2 = new Pokemon("ChikoSal", new TipoPlanta(), 49, 34, 45, 45,
                                golpesPlanta);
                            break;
                        case 5:
                            p2 =
                                new Pokemon("CyndaSal", new TipoFogo(), 52, 31, 39, 41, golpesFogo);
                            break;
                        case 6:
                            p2 = new Pokemon("TotoSal", new TipoAgua(), 49, 32, 45, 45, golpesAgua);
                            break;
                        default:
                            break;
                    }

                    if (p1 != null && p2 != null) {
                        final Batalha arenaPvP = new Batalha();
                        final Bag bag1 = new Bag();
                        final Bag bag2 = new Bag();
                        arenaPvP.iniciarCombatePvP(p1, p2, bag1, bag2);
                        Batalha.pausar(1500);
                    } else {
                        System.out.println("\nSeleção de PokeSal inválida.");
                    }

                    System.out.println("\n Deseja voltar ao menu principal?");
                    System.out.println("1 - Sim (Escolher outro modo ou PokeSal)");
                    System.out.println("2 - Não (Sair)");
                    final int fimPvP = sc.nextInt();
                    switch (fimPvP) {
                        case 2:
                            rodandoJogo = false;
                            break;
                        default:
                            break;
                    }
                    break;
                case 1:
                    System.out.println(
                        "\n ******** O Torneio Está Para Começar Treinador! *********");
                    System.out.println(
                        "********** Rápido, Escolha Seu Inicial! ****************");
                    System.out.println("(1 - BulbaSal)");
                    System.out.println("(2 - CharSal)");
                    System.out.println("(3 - SquirtSal)");
                    System.out.println("(4 - ChikoSal)");
                    System.out.println("(5 - CyndaSal)");
                    System.out.println("(6 - TotoSal)");
                    System.out.println("     (Digite o Número do Seu PokeSal)");

                    final int poke = sc.nextInt();
                    Pokemon pokeEsc = null;

                    switch (poke) {
                        case 1:
                            pokeEsc = bulbasal;
                            break;
                        case 2:
                            pokeEsc = charsal;
                            break;
                        case 3:
                            pokeEsc = squirtsal;
                            break;
                        case 4:
                            pokeEsc = chikosal;
                            break;
                        case 5:
                            pokeEsc = cyndasal;
                            break;
                        case 6:
                            pokeEsc = totosal;
                            break;
                        default:
                            break;
                    }

                    if (pokeEsc != null) {
                        System.out.println(
                            "\nÓtima escolha! Você pegou o " + pokeEsc.getNome() + "!");
                        Batalha.pausar(1500);
                        pokeEsc.exibirSts();

                        final Bag mochila = new Bag();
                        boolean jogando = true;

                        while (jogando) {
                            final Batalha arena = new Batalha();
                            Pokemon inimigo = null;
                            final Random rand = new Random();

                            while (inimigo == null || inimigo == pokeEsc) {
                                inimigo = todosIniciais[rand.nextInt(todosIniciais.length)];
                            }

                            pokeEsc.curar(1.0);

                            final boolean venceu = arena.iniciarCombate(pokeEsc, inimigo, mochila);

                            if (!venceu) {
                                Batalha.pausar(2000);
                                System.out.println("\nNão Desanime Ainda... O Que Deseja Fazer?");
                                System.out.println("1 - Tentar De Novo (Mesmo PokeSal)");
                                System.out.println("2 - Escolher Outro PokeSal (Voltar ao Início)");
                                System.out.println("3 - Desistir e Ir Para Casa");
                                final int esc = sc.nextInt();
                                switch (esc) {
                                    case 2:
                                        jogando = false;
                                        break;
                                    case 3:
                                        jogando = false;
                                        rodandoJogo = false;
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                Batalha.pausar(2000);
                                System.out.println("\n O que Deseja Fazer?");
                                System.out.println(
                                    "1 - Continuar Procurando Batalhas (Mesmo PokeSal)");
                                System.out.println("2 - Escolher Outro PokeSal (Voltar ao Início)");
                                System.out.println("3 - Desistir e ir Para Casa");
                                final int esc = sc.nextInt();
                                switch (esc) {
                                    case 2:
                                        jogando = false;
                                        break;
                                    case 3:
                                        jogando = false;
                                        rodandoJogo = false;
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    } else {
                        System.out.println("\nEscolha Dentre as Opções Válidas!");
                        Batalha.pausar(2000);
                    }
                    break;
                default:
                    System.out.println("\nModo Inválido.");
                    Batalha.pausar(2000);
                    break;
            }
        }

        System.out.println("\nFim de jogo. Obrigado por jogar!");
        Batalha.pausar(2000);
    }
}