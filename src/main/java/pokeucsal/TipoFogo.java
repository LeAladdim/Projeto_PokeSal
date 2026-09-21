package pokeucsal;

/**
 * Tipo Fogo: forte contra Planta e fraco contra Água.
 */
public class TipoFogo implements TipoPoke {

    private static final double VANTAGEM = 2.0;
    private static final double DESVANTAGEM = 0.5;
    private static final double NEUTRO = 1.0;

    private static final int BONUS_ATK_BUFF = 10;
    private static final int PENALIDADE_DEF_BUFF = 5;
    private static final int PENALIDADE_DEF_DEBUFF = 5;

    @Override
    public String getNomeTipo() {
        return "Fogo";
    }

    @Override
    public double calcMult(TipoPoke defensor) {
        if (defensor.getNomeTipo().equals("Planta")) {
            return VANTAGEM;
        } else if (defensor.getNomeTipo().equals("Água")) {
            return DESVANTAGEM;
        }
        return NEUTRO;
    }

    @Override
    public void apBf(final Pokemon usuario) {
        usuario.setAtk(usuario.getAtk() + BONUS_ATK_BUFF);
        usuario.setDef(usuario.getDef() - PENALIDADE_DEF_BUFF);
        System.out.println("A Chama Cresce! O Ataque de " + usuario.getNome() + " Aumentou Para "
            + usuario.getAtk() + " e a Defesa Diminuiu para " + usuario.getDef()
            + " Devido ao Esforço Excessivo!");
        Batalha.pausar(Batalha.PAUSA_CURTA_MS);
    }

    @Override
    public void apDb(final Pokemon alvo) {
        alvo.setDef(alvo.getDef() - PENALIDADE_DEF_DEBUFF);
        System.out.println("O Inimigo está Super-Aquecido! A Defesa de " + alvo.getNome() + " "
            + "diminuiu para "
            + alvo.getDef() + " Devido ao Calor");
        Batalha.pausar(Batalha.PAUSA_CURTA_MS);
    }
}