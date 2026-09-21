package pokeucsal;

import java.util.Random;

/**
 * Golpe base do jogo, com nome e poder base.
 */
public abstract class Golpe {

    private static final int PRECISAO_MAXIMA = 100;


    private static final int DIVISOR_ATK = 10;

    private static final double BONUS_ASFALTO_QUENTE = 1.15;
    private static final double BONUS_POCA_DE_CHUVA = 1.10;

    private static final double CHANCE_STATUS = 0.125;

    private static final int CUSTO_PRECISAO_BUFF = 10;

    protected final String nome;

    protected final int poderBase;

    public Golpe(final String nome, final int poderBase) {
        this.nome = nome;
        this.poderBase = poderBase;
    }

    public String getNome() {
        return nome;
    }

    protected boolean errou(final Pokemon atacante) {
        final Random rand = new Random();
        return rand.nextInt(PRECISAO_MAXIMA) >= atacante.getPrecisao();
    }

    protected int calcularDanoBruto(final Pokemon atacante) {
        return poderBase + (atacante.getAtk() / DIVISOR_ATK);
    }

    public abstract void executar(Pokemon atacante, Pokemon defensor);

    /**
     * Golpe Normal: causa dano sem vantagem ou desvantagem de tipo.
     */
    public static class Normal extends Golpe {
        public Normal(final String nome, final int poderBase) {
            super(nome, poderBase);
        }

        @Override
        public void executar(final Pokemon atacante, final Pokemon defensor) {
            System.out.println("\n>>> " + atacante.getNome() + " usou " + nome + "!");
            if (errou(atacante)) {
                System.out.println("O Ataque Falhou!");
                return;
            }
            final int dano = calcularDanoBruto(atacante);
            defensor.dano(dano);
        }
    }

    /**
     * Golpe Elemental: usa a vantagem de tipo, o terreno e pode aplicar status.
     */
    public static class Elemental extends Golpe {
        public Elemental(final String nome, final int poderBase) {
            super(nome, poderBase);
        }

        /**
         * Executa o golpe levando em conta o clima (terreno) atual da batalha.
         *
         * @param atacante Pokémon que usa o golpe
         * @param defensor Pokémon que recebe o golpe
         * @param climaAtual terreno da batalha, por exemplo "Asfalto Quente"
         */
        public void executar(final Pokemon atacante, final Pokemon defensor,
                             final String climaAtual) {
            System.out.println("\n>>> " + atacante.getNome() + " usou " + nome + "!");
            if (errou(atacante)) {
                System.out.println("O Ataque Falhou!");
                return;
            }
            final double mult = atacante.getTipo().calcMult(defensor.getTipo());
            if (mult > 1.0) {
                System.out.println("É Super Efetivo!");
            } else if (mult < 1.0) {
                System.out.println("Não é Muito Efetivo...");
            }

            double danoCalculado = calcularDanoBruto(atacante) * mult;

            final String tipoAtacante = atacante.getTipo().getNomeTipo();

            if (climaAtual.equals("Asfalto Quente") && tipoAtacante.equals("Fogo")) {
                danoCalculado *= BONUS_ASFALTO_QUENTE;
                System.out.println("O Asfalto Quente Potencializou o Golpe!");
            } else if (climaAtual.equals("Piso Escorregadio")
                && (tipoAtacante.equals("Água") || tipoAtacante.equals("Agua"))) {
                danoCalculado *= BONUS_POCA_DE_CHUVA;
                System.out.println("A Poça de Chuva Amplificou o Ataque!");
            }

            defensor.dano((int) danoCalculado);

            if (Math.random() < CHANCE_STATUS) {
                if (tipoAtacante.equals("Fogo") && !defensor.isQueimado()) {
                    defensor.setQueimado(true);
                } else if (tipoAtacante.equals("Planta") && !defensor.isEnvenenado()) {
                    defensor.setEnvenenado(true);
                } else if ((tipoAtacante.equals("Água") || tipoAtacante.equals("Agua"))
                    && !defensor.isParalisado()) {
                    defensor.setParalisado(true);
                }
            }
        }

        @Override
        public void executar(final Pokemon atacante, final Pokemon defensor) {
            executar(atacante, defensor, "Normal");
        }
    }

    /**
     * Golpe de Buff: melhora o próprio Pokémon, mas custa precisão.
     */
    public static class Buff extends Golpe {
        public Buff(final String nome) {
            super(nome, 0);
        }

        @Override
        public void executar(final Pokemon atacante, final Pokemon defensor) {
            System.out.println("\n>>> " + atacante.getNome() + " usou " + nome + "!");
            atacante.getTipo().apBf(atacante);
            atacante.diminuirPrecisao(CUSTO_PRECISAO_BUFF);
        }
    }

    /**
     * Golpe de Debuff: causa dano e piora um atributo do adversário.
     */
    public static class Debuff extends Golpe {
        public Debuff(final String nome, final int poderBase) {
            super(nome, poderBase);
        }

        @Override
        public void executar(final Pokemon atacante, final Pokemon defensor) {
            System.out.println("\n>>> " + atacante.getNome() + " usou " + nome + "!");
            if (errou(atacante)) {
                System.out.println("O ataque errou!");
                return;
            }
            final int dano = calcularDanoBruto(atacante);
            defensor.dano(dano);

            atacante.getTipo().apDb(defensor);
        }
    }
}