package pokeucsal;

/**
 * Contrato que todo tipo elemental (Fogo, Água e Planta) precisa cumprir.
 */
public interface TipoPoke {
    /**
     * Devolve o nome do tipo.
     *
     * @return nome do tipo, por exemplo "Fogo"
     */
    String getNomeTipo();

    /**
     * Calcula o multiplicador de dano deste tipo contra o tipo do defensor.
     *
     * @param defensor tipo de quem vai receber o golpe
     * @return 2.0 (vantagem), 0.5 (desvantagem) ou 1.0 (neutro)
     */
    double calcMult(TipoPoke defensor);

    /**
     * Aplica o efeito do golpe de Buff no próprio usuário.
     *
     * @param usuario Pokémon que usou o Buff
     */
    void apBf(Pokemon usuario);

    /**
     * Aplica o efeito do golpe de Debuff no adversário.
     *
     * @param usuario Pokémon que vai sofrer o Debuff
     */
    void apDb(Pokemon usuario);
}
