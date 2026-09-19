package pokeucsal;

import java.util.Random;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menuP();
    }

    public static void menuP() {
        Scanner sc = new Scanner(System.in);

        // Fabricando os kits de golpes usando as classes aninhadas
        Golpe[] golpesPlanta = {
            new Golpe.Normal("Investida", 20),
            new Golpe.Elemental("Folha Navalha", 30),
            new Golpe.Buff("Síntese"),
            new Golpe.Debuff("Tempestade de Folhas", 1)
        };

        Golpe[] golpesFogo = {
            new Golpe.Normal("Arranhão", 20),
            new Golpe.Elemental("Lança-Chamas", 30),
            new Golpe.Buff("Dança das Espadas"),
            new Golpe.Debuff("Superaquecer", 1)
        };

        Golpe[] golpesAgua = {
            new Golpe.Normal("Investida", 20),
            new Golpe.Elemental("Jato de Água", 30),
            new Golpe.Buff("Dança da Chuva"),
            new Golpe.Debuff("Hidro Bomba", 1)
        };

        Pokemon bulbasal = new Pokemon("BulbaSal", new TipoPlanta(), 43, 30, 45, 45, golpesPlanta);
        Pokemon charsal = new Pokemon("CharSal", new TipoFogo(), 54, 33, 40, 65, golpesFogo);
        Pokemon squirtsal = new Pokemon("SquirtSal", new TipoAgua(), 48, 32, 44, 43, golpesAgua);
        Pokemon chikosal = new Pokemon("ChikoSal", new TipoPlanta(), 49, 29, 45, 45, golpesPlanta);
        Pokemon cyndasal = new Pokemon("CyndaSal", new TipoFogo(), 52, 31, 39, 65, golpesFogo);
        Pokemon totosal = new Pokemon("TotoSal", new TipoAgua(), 49, 32, 45, 45, golpesAgua);

        System.out.println("********0 Torneio Está para Começar Treinador***********");
        System.out.println("*******Rápido, Escolha Seu Inicial!********");
        System.out.println("********1- BulbaSal********");
        System.out.println("********2- CharSal********");
        System.out.println("********3- SquirtSal********");
        System.out.println("********4- ChikoSal********");
        System.out.println("********5- CyndaSal********");
        System.out.println("********6- TotoSal********");
        System.out.println("********Digite o Número do Seu Pokemon***********");

        int poke = sc.nextInt();
        Pokemon pokeEsc = null;

        if (poke == 1) {
            pokeEsc = bulbasal;
        }
        else if (poke == 2) {
            pokeEsc = charsal;
        }
        else if (poke == 3) {
            pokeEsc = squirtsal;
        }
        else if (poke == 4) {
            pokeEsc = chikosal;
        }
        else if (poke == 5) {
            pokeEsc = cyndasal;
        }
        else if (poke == 6) {
            pokeEsc = totosal;
        }

        if (pokeEsc != null) {
            System.out.println("\nÓtima escolha! Você pegou o " + pokeEsc.getNome() + "!");
            pokeEsc.exibirSts();

            Pokemon[] todosIniciais = {bulbasal, charsal, squirtsal, chikosal, cyndasal, totosal};
            Bag mochila = new Bag();
            boolean jogando = true;

            while (jogando) {
                Batalha arena = new Batalha();
                Pokemon inimigo = null;
                Random rand = new Random();

                while (inimigo == null || inimigo == pokeEsc) {
                    inimigo = todosIniciais[rand.nextInt(todosIniciais.length)];
                }

                boolean venceu = arena.iniciarCombate(pokeEsc, inimigo, mochila);

                if (!venceu) {
                    jogando = false;
                } else {
                    System.out.println("\nO que deseja fazer?");
                    System.out.println("1 - Continuar procurando batalhas");
                    System.out.println("2 - Desistir e ir para casa");
                    int esc = sc.nextInt();
                    if (esc == 2) jogando = false;
                }
            }
            System.out.println("Fim de jogo. Obrigado por jogar!");
        } else {
            System.out.println("Escolha Dentre as Opções");
        }
    }
}