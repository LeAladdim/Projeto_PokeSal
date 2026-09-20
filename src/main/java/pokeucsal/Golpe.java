package pokeucsal;

import java.util.Random;

public abstract class Golpe {

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
        return rand.nextInt(100) >= atacante.getPrecisao();
    }

    protected int calcularDanoBruto(final Pokemon atacante) {
        return poderBase + (atacante.getAtk() / 10);
    }

    public abstract void executar(Pokemon atacante, Pokemon defensor);

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

    public static class Elemental extends Golpe {
        public Elemental(final String nome, final int poderBase) {
            super(nome, poderBase);
        }

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
                danoCalculado *= 1.15;
                System.out.println("O Asfalto Quente Potencializou o Golpe!");
            } else if (climaAtual.equals("Piso Escorregadio") &&
                (tipoAtacante.equals("Água") || tipoAtacante.equals("Agua"))) {
                danoCalculado *= 1.10;
                System.out.println("A Poça de Chuva Amplificou o Ataque!");
            }

            defensor.dano((int) danoCalculado);

            if (Math.random() < 0.125) {
                if (tipoAtacante.equals("Fogo") && !defensor.isQueimado()) {
                    defensor.setQueimado(true);
                } else if (tipoAtacante.equals("Planta") && !defensor.isEnvenenado()) {
                    defensor.setEnvenenado(true);
                } else if ((tipoAtacante.equals("Água") || tipoAtacante.equals("Agua")) &&
                    !defensor.isParalisado()) {
                    defensor.setParalisado(true);
                }
            }
        }

        @Override
        public void executar(final Pokemon atacante, final Pokemon defensor) {
            executar(atacante, defensor, "Normal");
        }
    }

    public static class Buff extends Golpe {
        public Buff(final String nome) {
            super(nome, 0);
        }

        @Override
        public void executar(final Pokemon atacante, final Pokemon defensor) {
            System.out.println("\n>>> " + atacante.getNome() + " usou " + nome + "!");
            atacante.getTipo().apBf(atacante);
            atacante.diminuirPrecisao(10);
        }
    }

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