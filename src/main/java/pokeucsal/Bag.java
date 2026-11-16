package pokeucsal;

import java.util.Scanner;

/**
 * Mochila de itens de batalha: no máximo {@value #LIMITE_ITENS} itens por luta, e cada item só pode ser usado uma vez.
 */
public class Bag {
    public static final int LIMITE_ITENS = 2;
    private int itensUsd = 0;
    private int potion = 1;
    private int superPotion = 1;
    private int antidoto = 1;
    private int salShard = 1;

    /**
     * Devolve todos os itens e zera o contador de itens usados. Chamado no início de cada batalha.
     */
    public void resetarUsoBatalha() {
        this.itensUsd = 0;
        potion = 1;
        superPotion = 1;
        antidoto = 1;
        salShard = 1;
    }

    /**
     * Abre o menu da mochila e aplica o item escolhido.
     *
     * @param aliado Pokésal que recebe o efeito do item
     * @param arena batalha em andamento (usada pela Sal Shard)
     * @param registro destino do evento de uso de item
     * @param treinador dono da mochila
     * @param sc teclado do jogador
     * @param gerador sorteio usado pela Sal Shard
     * @return true se um item foi usado (o turno é consumido); false se o jogador voltou, digitou algo inválido ou o item acabou
     * @throws LimiteDeItensExcedidoException se o limite de itens da batalha já foi atingido
     */
    public boolean abrirMochila(Pokesal aliado, Batalha arena, RegistroBatalha registro, Treinador treinador, Scanner sc, GeradorAleatorio gerador) {
        if (itensUsd >= LIMITE_ITENS) {
            throw new LimiteDeItensExcedidoException(LIMITE_ITENS);
        }
        if(!Batalha.modoTeste) {
            System.out.println("\n--- MOCHILA (Itens Usados: " + itensUsd + "/" + LIMITE_ITENS + ") ---");
            System.out.println("1 - Potion (Cura 15% de HP) [Restam: " + potion + "]");
            System.out.println("2 - Super Potion (Cura 35% de HP) [Restam: " + superPotion + "]");
            System.out.println("3 - Antídoto (Cura Status) [Restam: " + antidoto + "]");
            System.out.println("4 - Sal Shard (Muda Terreno) [Restam: " + salShard + "]");
            System.out.println("0 - Voltar");
        }

        if (!sc.hasNextInt()) {
            sc.next();
            return false;
        }
        int escolha = sc.nextInt();

        switch(escolha) {
            case 1:
                if(potion > 0) {
                    potion--;
                    aliado.curar(0.15);
                    if(!Batalha.modoTeste) {
                        System.out.println("\n>>> " + aliado.getNome() + " Usou Potion e Curou 15%!");
                    }
                    registro.registrarUsoItem(treinador.getNome(), "Potion");
                } else {
                    return false;
                }
                break;
            case 2:
                if(superPotion > 0) {
                    superPotion--;
                    aliado.curar(0.35);
                    if(!Batalha.modoTeste) {
                        System.out.println("\n>>> " + aliado.getNome() + " Usou Super Potion e Curou 35%!");
                    }
                    registro.registrarUsoItem(treinador.getNome(), "Super Potion");
                } else {
                    return false;
                }
                break;
            case 3:
                if(antidoto > 0) {
                    antidoto--;
                    aliado.curarStatus();
                    if(!Batalha.modoTeste) {
                        System.out.println("\n>>> " + aliado.getNome() + " Usou Antídoto e Limpou os Status!");
                    }
                    registro.registrarUsoItem(treinador.getNome(), "Antídoto");
                } else {
                    return false;
                }
                break;
            case 4:
                if(salShard > 0) {
                    salShard--;
                    arena.mudarTerrenoSalShard(registro, gerador);
                    registro.registrarUsoItem(treinador.getNome(), "Sal Shard");
                } else {
                    return false;
                }
                break;
            case 0:
                return false;
            default:
                return false;
        }
        itensUsd++;
        Batalha.pausar(Batalha.PAUSA_CURTA_MS);
        return true;
    }
}
