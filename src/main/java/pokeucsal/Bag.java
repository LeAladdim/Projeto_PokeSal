package pokeucsal;

import java.util.Scanner;

/**
 * Mochila do treinador com os itens de batalha (Potion, Super Potion, Antídoto e Sal Shard).
 */
public class Bag {

    private static final int LIMITE_ITENS = 2;

    // Porcentagem de cura de cada poção .
    private static final double CURA_POTION = 0.15;
    private static final double CURA_SUPER_POTION = 0.35;

    // Números do menu para os itens que não são 1 nem 2 .
    private static final int OPCAO_ANTIDOTO = 3;
    private static final int OPCAO_SAL_SHARD = 4;

    private int potion = 1;

    private int superPotion = 1;

    private int antidoto = 1;

    private int salShard = 1;

    private int itensUsd = 0;

    /**
     * Zera o contador de itens usados e repõe o estoque para uma nova batalha.
     */
    public void resetarUsoBatalha() {
        this.itensUsd = 0;
        this.potion = 1;
        this.superPotion = 1;
        this.antidoto = 1;
        this.salShard = 1;
    }

    /**
     * Mostra o menu da mochila e aplica o item escolhido.
     *
     * @param aliado PokéSal do jogador que recebe o efeito do item
     * @param arena batalha atual, usada pelo Sal Shard para trocar o terreno
     * @return {@code true} se um item foi usado (o turno é consumido), senão {@code false}
     */
    public boolean abrirMochila(final Pokemon aliado, final Batalha arena) {
        if (itensUsd >= LIMITE_ITENS) {
            System.out.println(
                "\nLimite Máximo De " + LIMITE_ITENS + " itens Atingido Nesta Batalha.");
            Batalha.pausar(Batalha.PAUSA_CURTA_MS);
            return false;
        }

        final Scanner sc = new Scanner(System.in);
        System.out.println(
            "\n--- MOCHILA (Itens Usados: " + itensUsd + "/" + LIMITE_ITENS + ") ---");
        System.out.println("1 - Potion (Cura 15% de HP) [Restam: " + potion + "]");
        System.out.println("2 - Super Potion (Cura 35% de HP) [Restam: " + superPotion + "]");
        System.out.println("3 - Antídoto (Cura Status Negativos) [Restam: " + antidoto + "]");
        System.out.println("4 - Sal Shard (Muda o Terreno) [Restam: " + salShard + "]");
        System.out.println("0 - Voltar");

        final int escolha = sc.nextInt();

        switch (escolha) {
            case 1:
                if (potion > 0) {
                    potion--;
                    aliado.curar(CURA_POTION);
                    System.out.println(
                        "\n>>> " + aliado.getNome() + " Usou uma Potion e Recuperou 15% da vida!");
                    Batalha.pausar(Batalha.PAUSA_CURTA_MS);
                } else {
                    System.out.println("Item Indisponível ou Escolha Inválida.");
                    Batalha.pausar(Batalha.PAUSA_CURTA_MS);
                    return false;
                }
                break;
            case 2:
                if (superPotion > 0) {
                    superPotion--;
                    aliado.curar(CURA_SUPER_POTION);
                    System.out.println(
                        "\n>>> " + aliado.getNome()
                            + " Usou uma Super Potion e Recuperou 35% da Vida!");
                    Batalha.pausar(Batalha.PAUSA_CURTA_MS);
                } else {
                    System.out.println("Item Indisponível ou Escolha Inválida.");
                    Batalha.pausar(Batalha.PAUSA_CURTA_MS);
                    return false;
                }
                break;
            case OPCAO_ANTIDOTO:
                if (antidoto > 0) {
                    antidoto--;
                    aliado.curarStatus();
                    System.out.println(
                        "\n>>> " + aliado.getNome()
                            + " Usou um Antídoto e Curou os Status Negativos!");
                    Batalha.pausar(Batalha.PAUSA_CURTA_MS);
                } else {
                    System.out.println("Item Indisponível ou Escolha Inválida.");
                    Batalha.pausar(Batalha.PAUSA_CURTA_MS);
                    return false;
                }
                break;
            case OPCAO_SAL_SHARD:
                if (salShard > 0) {
                    salShard--;
                    arena.mudarTerreno();
                } else {
                    System.out.println("Item Indisponível ou Escolha Inválida.");
                    Batalha.pausar(Batalha.PAUSA_CURTA_MS);
                    return false;
                }
                break;
            case 0:
                return false;
            default:
                System.out.println("Item Indisponível ou Escolha Inválida.");
                Batalha.pausar(Batalha.PAUSA_CURTA_MS);
                return false;
        }

        itensUsd++;
        return true;
    }
}