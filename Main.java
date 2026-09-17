package pokeucsal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menuP();
    }
//CharSal, SquirtSal, ChikoSal, CyndaSal ou TotoSal. alguém lembra de descomentar dps plskkkkkkkk é so pra lembrar os nomes
    public static void menuP() {
        Scanner sc = new Scanner(System.in);

        Pokemon bulbasal = new Pokemon("BulbaSal", "Planta", 43, 39, 45, 45);
        Pokemon charsal = new Pokemon("CharSal", "Fogo", 54, 43, 40, 65);
        Pokemon squirtsal = new Pokemon("SquirtSal", "Água", 48, 65, 44, 43);
        Pokemon chikosal = new Pokemon("ChikoSal", "Planta", 49, 65, 45, 45);
        Pokemon cyndasal = new Pokemon("CyndaSal", "Fogo", 52, 43, 39, 65);
        Pokemon totosal = new Pokemon("TotoSal", "Água", 49, 49, 45, 45);

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
        }if (pokeEsc != null) {
            System.out.println("\nÓtima escolha! Você pegou o " + pokeEsc.getNome() + "!");
            pokeEsc.exibirSts();
        }else {
            System.out.println("Escolha Dentre as Opções");
            return;
        }
        System.out.println("\nVocê escolheu o " + pokeEsc.getNome() + "!");
    }
}


