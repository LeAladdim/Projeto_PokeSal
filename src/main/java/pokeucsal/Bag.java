package pokeucsal;

import java.util.Scanner;

public class Bag {
    private int potion = 1;
    private int superPotion = 2;
    private int salShard = 1;

    private int itensUsd = 0;
    private final int LIM_IT = 2;

    public void resetarUsoBatalha() {
        this.itensUsd = 0;
    }

    public boolean abrirMochila(Pokemon aliado, Batalha arena) {
        if (itensUsd >= LIM_IT) {
            System.out.println("\nLimite maximo de " + LIM_IT + " itens atingido nesta batalha.");
            return false;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- MOCHILA (Itens usados: " + itensUsd + "/" + LIM_IT + ") ---");
        System.out.println("1 - Potion (Cura 15%) [Restam: " + potion + "]");
        System.out.println("2 - Super Potion (Cura 35%) [Restam: " + superPotion + "]");
        System.out.println("3 - Sal Shard (Muda o Terreno) [Restam: " + salShard + "]");
        System.out.println("0 - Voltar");

        int escolha = sc.nextInt();
        boolean itemUtilizado = false;

        if (escolha == 1 && potion > 0) {
            potion--;
            aliado.curar(0.15);
            itemUtilizado = true;
        } else if (escolha == 2 && superPotion > 0) {
            superPotion--;
            aliado.curar(0.35);
            itemUtilizado = true;
        } else if (escolha == 3 && salShard > 0) {
            salShard--;
            arena.mudarTerreno(aliado.getTipo().getNomeTipo());
            itemUtilizado = true;
        } else if (escolha == 0) {
            return false;
        } else {
            System.out.println("Item indisponivel ou escolha invalida.");
            return false;
        }

        if (itemUtilizado) {
            itensUsd++;
            return true;
        }

        return false;
    }
}