package pokeucsal;

import java.util.Scanner;

public class Bag {
    private int potion = 3;

    private int superPotion = 2;

    private int salShard = 1;


    public boolean abrirMochila(Pokemon aliado, Batalha arena) {
        final Scanner sc = new Scanner(System.in);
        System.out.println("\n--- MOCHILA ---");
        System.out.println("1 - Potion (Cura 15%) [Restam: " + potion + "]");
        System.out.println("2 - Super Potion (Cura 35%) [Restam: " + superPotion + "]");
        System.out.println("3 - Sal Shard (Muda o Terreno) [Restam: " + salShard + "]");
        System.out.println("0 - Voltar");

        final int escolha = sc.nextInt();

        if (escolha == 1 && potion > 0) {
            potion--;
            aliado.curar(0.15);
            return true;
        } else if (escolha == 2 && superPotion > 0) {
            superPotion--;
            aliado.curar(0.35);
            return true;
        } else if (escolha == 3 && salShard > 0) {
            salShard--;
            arena.mudarTerreno(aliado.getTipo().getNomeTipo());
            return true;
        } else if (escolha == 0) {
            return false;
        } else {
            System.out.println("Item indisponível ou escolha inválida!");
            Batalha.pausar(1500);
            return false;
        }
    }
}