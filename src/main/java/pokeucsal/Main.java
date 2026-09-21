package pokeucsal;

import java.util.Random;
import java.util.Scanner;

/**
 * Ponto de entrada do jogo: menus de escolha de modo (PvE ou PvP) e de Pokémon.
 */
public class Main {

    // Números do menu de escolha do Pokémon .
    private static final int ESCOLHA_BULBASAL = 1;
    private static final int ESCOLHA_CHARSAL = 2;
    private static final int ESCOLHA_SQUIRTSAL = 3;
    private static final int ESCOLHA_CHIKOSAL = 4;
    private static final int ESCOLHA_CYNDASAL = 5;
    private static final int ESCOLHA_TOTOSAL = 6;

    // Opção "Desistir e ir para casa" .
    private static final int OPCAO_DESISTIR = 3;

    /**
     * Inicia o jogo.
     */
    public static void main(final String[] args) {
        menuP();
    }

    /**
     * Mostra o menu principal e controla o fluxo dos modos PvE e PvP.
     */
    public static void menuP() {
        final Scanner sc = new Scanner(System.in);

        final Golpe[] golpesPlanta = {
            new Golpe.Normal("Chicote", 22),
            new Golpe.Elemental("Cansanção", 30),
            new Golpe.Buff("Fotossíntese"),
            new Golpe.Debuff("Tempestade de Folhas", 1)
        };

        final Golpe[] golpesFogo = {
            new Golpe.Normal("Arranhão", 22),
            new Golpe.Elemental("Lança-Chamas", 30),
            new Golpe.Buff("Dança das Chamas"),
            new Golpe.Debuff("Superaquecer", 1)
        };

        final Golpe[] golpesAgua = {
            new Golpe.Normal("Impacto Repentino", 22),
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
                "╔══════════════════════════════════════════════════════════╗\n"
                +"║    *** O TORNEIO DO ESTACIONAMENTO DA UCSAL ***          ║\n"
                +"║                 ESTÁ PARA COMEÇAR!                       ║\n"
                +"╚══════════════════════════════════════════════════════════╝\n"
                +"\n"
                +"  1 - Jogador vs Computador (PvE)\n"
                +"  2 - Jogador vs Jogador (PvP)\n"
                +"\n"
                +"───────────────────────────────────────────────────────────\n"
                +"> Escolha o modo de jogo:");
            final int modoJogo = sc.nextInt();

            if (modoJogo == 2) {
                System.out.println("" +
                         "┌─────────────────────────────────────────────────────────┐\n"
                        +"│                  SELEÇÃO DO JOGADOR 1                   │\n"
                        +"└─────────────────────────────────────────────────────────┘\n"
                        +"   1. BulbaSal                   4. ChikoSal\n"
                        +"   2. CharSal                    5. CyndaSal\n"
                        +"   3. SquirtSal                  6. TotoSal\n"
                        +"───────────────────────────────────────────────────────────\n"
                        +"> Escolha seu Pokésal:");
                final int p1Escolha = sc.nextInt();
                Pokemon p1 = null;
                if (p1Escolha == ESCOLHA_BULBASAL) {
                    p1 = bulbasal;
                } else if (p1Escolha == ESCOLHA_CHARSAL) {
                    p1 = charsal;
                } else if (p1Escolha == ESCOLHA_SQUIRTSAL) {
                    p1 = squirtsal;
                } else if (p1Escolha == ESCOLHA_CHIKOSAL) {
                    p1 = chikosal;
                } else if (p1Escolha == ESCOLHA_CYNDASAL) {
                    p1 = cyndasal;
                } else if (p1Escolha == ESCOLHA_TOTOSAL) {
                    p1 = totosal;
                }

                System.out.println("" +
                         "┌─────────────────────────────────────────────────────────┐\n"
                        +"│                  SELEÇÃO DO JOGADOR 2                  │\n"
                        +"└─────────────────────────────────────────────────────────┘\n"
                        +"   1. BulbaSal                   4. ChikoSal\n"
                        +"   2. CharSal                    5. CyndaSal\n"
                        +"   3. SquirtSal                  6. TotoSal\n"
                        +"───────────────────────────────────────────────────────────\n"
                        +"> Escolha seu Pokésal:");
                final int p2Escolha = sc.nextInt();
                Pokemon p2 = null;
                if (p2Escolha == ESCOLHA_BULBASAL) {
                    p2 = bulbasal;
                } else if (p2Escolha == ESCOLHA_CHARSAL) {
                    p2 = charsal;
                } else if (p2Escolha == ESCOLHA_SQUIRTSAL) {
                    p2 = squirtsal;
                } else if (p2Escolha == ESCOLHA_CHIKOSAL) {
                    p2 = chikosal;
                } else if (p2Escolha == ESCOLHA_CYNDASAL) {
                    p2 = cyndasal;
                } else if (p2Escolha == ESCOLHA_TOTOSAL) {
                    p2 = totosal;
                }

                if (p1 != null && p2 != null) {
                    final Batalha arenaPvP = new Batalha();
                    final Bag bag1 = new Bag();
                    final Bag bag2 = new Bag();
                    arenaPvP.iniciarCombatePvP(p1, p2, bag1, bag2);
                    Batalha.pausar(Batalha.PAUSA_CURTA_MS);
                } else {
                    System.out.println("Seleção de PokeSal invalida.");
                }

                System.out.println("\nDeseja voltar ao menu principal?");
                System.out.println("1 - Sim (Escolher outro modo ou PokeSal)");
                System.out.println("2 - Nao (Sair)");
                final int fimPvP = sc.nextInt();
                if (fimPvP == 2) {
                    rodandoJogo = false;
                }

            } else if (modoJogo == 1) {
                System.out.println("" +
                        "*====================================================\n"
                        +"        O TORNEIO ESTÁ PARA COMEÇAR, TREINADOR!     \n"
                        +"            Rápido, escolha seu inicial:            \n"
                        +"====================================================\n"
                        +"  [1] BulbaSal              [4] ChikoSal\n"
                        +"  [2] CharSal               [5] CyndaSal\n"
                        +"  [3] SquirtSal             [6] TotoSal\n"
                        +"----------------------------------------------------\n"
                        +">> Digite o número do seu Pokésal:");

                final int poke = sc.nextInt();
                Pokemon pokeEsc = null;

                if (poke == ESCOLHA_BULBASAL) {
                    pokeEsc = bulbasal;
                } else if (poke == ESCOLHA_CHARSAL) {
                    pokeEsc = charsal;
                } else if (poke == ESCOLHA_SQUIRTSAL) {
                    pokeEsc = squirtsal;
                } else if (poke == ESCOLHA_CHIKOSAL) {
                    pokeEsc = chikosal;
                } else if (poke == ESCOLHA_CYNDASAL) {
                    pokeEsc = cyndasal;
                } else if (poke == ESCOLHA_TOTOSAL) {
                    pokeEsc = totosal;
                }

                if (pokeEsc != null) {
                    System.out.println("\nÓtima escolha! Você pegou o " + pokeEsc.getNome() + "!");
                    Batalha.pausar(Batalha.PAUSA_CURTA_MS);
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
                            Batalha.pausar(Batalha.PAUSA_LONGA_MS);
                            System.out.println("\n Não Desanime Ainda... O que deseja fazer?");
                            System.out.println("1 - Tentar De Novo (Mesmo PokeSal)");
                            System.out.println("2 - Escolher outro PokeSal (Voltar ao inicio)");
                            System.out.println("3 - Desistir e ir para casa");
                            final int esc = sc.nextInt();
                            if (esc == 2) {
                                jogando = false;
                            } else if (esc == OPCAO_DESISTIR) {
                                jogando = false;
                                rodandoJogo = false;
                            }
                        } else {
                            Batalha.pausar(Batalha.PAUSA_LONGA_MS);
                            System.out.println("\nO que deseja fazer?");
                            System.out.println("1 - Continuar procurando batalhas (Mesmo PokeSal)");
                            System.out.println("2 - Escolher outro PokeSal (Voltar ao Inicio)");
                            System.out.println("3 - Desistir e ir para Casa");
                            final int esc = sc.nextInt();
                            if (esc == 2) {
                                jogando = false;
                            } else if (esc == OPCAO_DESISTIR) {
                                jogando = false;
                                rodandoJogo = false;
                            }
                        }
                    }
                } else {
                    System.out.println("Escolha Dentre as Opções");
                    Batalha.pausar(Batalha.PAUSA_LONGA_MS);
                }
            } else {
                System.out.println("Modo Invalido.");
                Batalha.pausar(Batalha.PAUSA_LONGA_MS);
            }
        }

        System.out.println("Fim de jogo. Obrigado por jogar!");
        Batalha.pausar(Batalha.PAUSA_LONGA_MS);
    }
}
