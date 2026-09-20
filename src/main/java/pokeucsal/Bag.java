package pokeucsal;

import java.util.Scanner;

public class Bag {

    private static final int LIMITE_ITENS = 2;

    private int potion = 1;

    private int superPotion = 2;

    private int salShard = 1;

    private int itensUsd = 0;

    public void resetarUsoBatalha() {
        this.itensUsd = 0;
    }

    public boolean abrirMochila(final Pokemon aliado, final Batalha arena) {
        if (itensUsd >= LIMITE_ITENS) {
            System.out.println(
                "\nLimite maximo de " + LIMITE_ITENS + " itens atingido nesta batalha.");
            Batalha.pausar(1500);
            return false;
        }

        final Scanner sc = new Scanner(System.in);
        System.out.println(
            "\n--- MOCHILA (Itens usados: " + itensUsd + "/" + LIMITE_ITENS + ") ---");
        System.out.println("1 - Potion (Cura 15%) [Restam: " + potion + "]");
        System.out.println("2 - Super Potion (Cura 35%) [Restam: " + superPotion + "]");
        System.out.println("3 - Sal Shard (Muda o Terreno) [Restam: " + salShard + "]");
        System.out.println("0 - Voltar");

        final int escolha = sc.nextInt();

        if (escolha == 1 && potion > 0) {
            potion--;
            aliado.curar(0.15);
            System.out.println("\n>>> " + aliado.getNome() + " usou uma Potion e recuperou vida!");
            Batalha.pausar(1500);
        } else if (escolha == 2 && superPotion > 0) {
            superPotion--;
            aliado.curar(0.35);
            System.out.println(
                "\n>>> " + aliado.getNome() + " usou uma Super Potion e recuperou bastante vida!");
            Batalha.pausar(1500);
        } else if (escolha == 3 && salShard > 0) {
            salShard--;
            arena.mudarTerreno(aliado.getTipo().getNomeTipo());
        } else if (escolha == 0) {
            return false;
        } else {
            System.out.println("Item indisponivel ou escolha invalida.");
            Batalha.pausar(1500);
            return false;
        }

        itensUsd++;
        return true;
    }
}