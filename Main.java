package pokeucsal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menuP();
    }
//CharSal, SquirtSal, ChikoSal, CyndaSal ou TotoSal. alguém lembra de descomentar dps plskkkkkkkk é so pra lembrar os nomes
    public static void menuP() {
        Scanner sc = new Scanner(System.in);
        int poke = sc.nextInt();
        System.out.println("********0 Torneio Vai Começar, Escolha Seu Inicial********");
        System.out.println("********1- BulbaSal********");
        System.out.println("********2- CharSal********");
        System.out.println("********3- SquirtSal********");
        System.out.println("********4- ChikoSal********");
        System.out.println("********5- CyndaSal********");
        System.out.println("********6- TotoSal********");
        System.out.println("********Digite o Número do Seu Pokemon***********");
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
        } else {
            System.out.println("Escolha Dentre as Opções");
            return;
        }
        System.out.println("\nVocê escolheu o " + pokeEsc.getNome() + "!");
    }
}
        }
                }