package pokeucsal;

/**
 * Tipo Água: forte contra Fogo e fraco contra Planta.
 */
public class TipoAgua implements TipoPoke {

    private static final double VANTAGEM = 2.0;
    private static final double DESVANTAGEM = 0.5;
    private static final double NEUTRO = 1.0;

    private static final int BONUS_SPD_BUFF = 10;
    private static final int PENALIDADE_ATK_BUFF = 5;
    private static final int PENALIDADE_ATK_DEBUFF = 5;

    @Override
    public String getNomeTipo() {
        return "Água";
    }

    @Override
    public double calcMult(final TipoPoke defensor) {
        final String tipoDef = defensor.getNomeTipo();
        if (tipoDef.equals("Fogo")) {
            return VANTAGEM;
        } else if (tipoDef.equals("Planta")) {
            return DESVANTAGEM;
        }
        return NEUTRO;
    }

    @Override
    public void apBf(final Pokemon usuario) {
        usuario.setSpd(usuario.getSpd() + BONUS_SPD_BUFF);
        usuario.setAtk(usuario.getAtk() - PENALIDADE_ATK_BUFF);

        System.out.println("Correnteza Ágil! A Velocidade de " + usuario.getNome()
            + " aumentou para " + usuario.getSpd() + " e o Ataque diminuiu para "
            + usuario.getAtk() + " Devido ao Esforço Para Nadar!");
        Batalha.pausar(Batalha.PAUSA_CURTA_MS);
    }

    @Override
    public void apDb(final Pokemon alvo) {
        alvo.setAtk(alvo.getAtk() - PENALIDADE_ATK_DEBUFF);

        System.out.println("O Inimigo Está se Afogando! O Ataque de " + alvo.getNome()
            + " Diminuiu para " + alvo.getAtk() + " Devido ao Sufocamento!");
        Batalha.pausar(Batalha.PAUSA_CURTA_MS);
    }
}
