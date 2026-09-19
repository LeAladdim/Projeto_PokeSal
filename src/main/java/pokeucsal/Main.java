package pokeucsal;

import java.util.Random;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menuP();
    }

    public static void menuP() {
        final Scanner sc = new Scanner(System.in);


        final Golpe[] golpesPlanta = {
            new Golpe.Normal("Chicote", 33),
            new Golpe.Elemental("Cansanção", 30),
            new Golpe.Buff("Fotossíntese"),
            new Golpe.Debuff("Tempestade de Folhas", 1)
        };

        final Golpe[] golpesFogo = {
            new Golpe.Normal("Arranhão", 33),
            new Golpe.Elemental("Lança-Chamas", 30),
            new Golpe.Buff("Dança das Chamas"),
            new Golpe.Debuff("Superaquecer", 1)
        };

        final Golpe[] golpesAgua = {
            new Golpe.Normal("Impacto Repentino", 33),
            new Golpe.Elemental("Jato de Água", 30),
            new Golpe.Buff("Dança da Chuva"),
            new Golpe.Debuff("Gêiser Submerso", 1)
        };

        final Pokemon bulbasal = new Pokemon("BulbaSal", new TipoPlanta(), 43, 30, 45, 45,
            golpesPlanta);
        final Pokemon charsal = new Pokemon("CharSal", new TipoFogo(), 54, 33, 40, 65,
            golpesFogo);
        final Pokemon squirtsal = new Pokemon("SquirtSal", new TipoAgua(), 48, 32, 44, 43,
            golpesAgua);
        final Pokemon chikosal = new Pokemon("ChikoSal", new TipoPlanta(), 49, 29, 45, 45,
            golpesPlanta);
        final Pokemon cyndasal = new Pokemon("CyndaSal", new TipoFogo(), 52, 31, 39, 65,
            golpesFogo);
        final Pokemon totosal = new Pokemon("TotoSal", new TipoAgua(), 49, 32, 45, 45, golpesAgua);

        System.out.println(
            "******** O Torneio do Estacionamento da UCSal Esta para Comecar! ***********");
        System.out.println("Escolha o modo de jogo:");
        System.out.println("1 - Jogador vs Computador (PvE)");
        System.out.println("2 - Jogador vs Jogador (PvP)");
        final int modoJogo = sc.nextInt();

        if (modoJogo == 2) {
            System.out.println("\n--- Selecao do Jogador 1 ---");
            System.out.println(
                "1- BulbaSal | 2- CharSal | 3- SquirtSal | 4- ChikoSal | 5- CyndaSal | 6- TotoSal");
            final int p1Escolha = sc.nextInt();
            Pokemon p1 = null;
            if (p1Escolha == 1) {
                p1 = bulbasal;
            } else if (p1Escolha == 2) {
                p1 = charsal;
            } else if (p1Escolha == 3) {
                p1 = squirtsal;
            } else if (p1Escolha == 4) {
                p1 = chikosal;
            } else if (p1Escolha == 5) {
                p1 = cyndasal;
            } else if (p1Escolha == 6) {
                p1 = totosal;
            }

            System.out.println("\n--- Selecao do Jogador 2 ---");
            System.out.println(
                "1- BulbaSal | 2- CharSal | 3- SquirtSal | 4- ChikoSal | 5- CyndaSal | 6- TotoSal");
            final int p2Escolha = sc.nextInt();
            Pokemon p2 = null;
            if (p2Escolha == 1) {
                p2 = bulbasal;
            } else if (p2Escolha == 2) {
                p2 = charsal;
            } else if (p2Escolha == 3) {
                p2 = squirtsal;
            } else if (p2Escolha == 4) {
                p2 = chikosal;
            } else if (p2Escolha == 5) {
                p2 = cyndasal;
            } else if (p2Escolha == 6) {
                p2 = totosal;
            }

            if (p1 != null && p2 != null) {
                final Batalha arenaPvP = new Batalha();
                final Bag bag1 = new Bag();
                final Bag bag2 = new Bag();
                arenaPvP.iniciarCombatePvP(p1, p2, bag1, bag2);
                Batalha.pausar(1500);
            } else {
                System.out.println("Selecao de pokesal invalida.");
            }
            return;
        }

        // Se o modoJogo for 1, o fluxo continua normal para o PvE existente...

        System.out.println("************0 Torneio Está para Começar Treinador********");
        System.out.println("************* Rápido, Escolha Seu Inicial!************");
        System.out.println("****************1- BulbaSal***************************");
        System.out.println("****************2- CharSal****************************");
        System.out.println("****************3- SquirtSal**************************");
        System.out.println("****************4- ChikoSal***************************");
        System.out.println("****************5- CyndaSal***************************");
        System.out.println("****************6- TotoSal****************************");
        System.out.println("************Digite o Número do Seu Pokemon***************");

        final int poke = sc.nextInt();
        Pokemon pokeEsc = null;

        if (poke == 1) {
            pokeEsc = bulbasal;
        } else if (poke == 2) {
            pokeEsc = charsal;
        } else if (poke == 3) {
            pokeEsc = squirtsal;
        } else if (poke == 4) {
            pokeEsc = chikosal;
        } else if (poke == 5) {
            pokeEsc = cyndasal;
        } else if (poke == 6) {
            pokeEsc = totosal;
        }

        if (pokeEsc != null) {
            System.out.println("\nÓtima escolha! Você pegou o " + pokeEsc.getNome() + "!");
            Batalha.pausar(1500);
            pokeEsc.exibirSts();

            final Pokemon[] todosIniciais = {bulbasal, charsal, squirtsal, chikosal, cyndasal,
                totosal};
            final Bag mochila = new Bag();
            boolean jogando = true;

            while (jogando) {
                final Batalha arena = new Batalha();
                Pokemon inimigo = null;
                final Random rand = new Random();

                while (inimigo == null || inimigo == pokeEsc) {
                    inimigo = todosIniciais[rand.nextInt(todosIniciais.length)];
                }

                final boolean venceu = arena.iniciarCombate(pokeEsc, inimigo, mochila);

                if (!venceu) {
                    jogando = false;
                } else {
                    Batalha.pausar(2000);
                    System.out.println("\nO que deseja fazer?");
                    System.out.println("1 - Continuar procurando batalhas");
                    System.out.println("2 - Desistir e ir para casa");
                    final int esc = sc.nextInt();
                    if (esc == 2) {
                        jogando = false;
                    }
                }
            }
            System.out.println("Fim de jogo. Obrigado por jogar!");
            Batalha.pausar(2000);
        } else {
            System.out.println("Escolha Dentre as Opções");
            Batalha.pausar(2000);
        }
    }
}