package pokeucsal;
import java.util.Random;

public abstract class Golpe {
    protected String nome;
    protected int poderBase;

    public Golpe(String nome, int poderBase) {
        this.nome = nome;
        this.poderBase = poderBase;
    }

    public String getNome() {
        return nome;
    }

    protected boolean acertou(Pokemon atacante) {
        Random rand = new Random();
        return rand.nextInt(100) < atacante.getPrecisao();
    }

    protected int calcularDanoBruto(Pokemon atacante) {
        return poderBase + (atacante.getAtk() / 10);
    }

    public abstract void executar(Pokemon atacante, Pokemon defensor);

    public static class Normal extends Golpe {
        public Normal(String nome, int poderBase) { super(nome, poderBase); }

        @Override
        public void executar(Pokemon atacante, Pokemon defensor) {
            System.out.println("\n>>> " + atacante.getNome() + " usou " + nome + "!");
            if (!acertou(atacante)) {
                System.out.println("O ataque errou!");
                return;
            }
            int dano = calcularDanoBruto(atacante);
            defensor.dano(dano);
        }
    }

    public static class Elemental extends Golpe {
        public Elemental(String nome, int poderBase) { super(nome, poderBase); }

        public void executar(Pokemon atacante, Pokemon defensor, String climaAtual) {
            System.out.println("\n>>> " + atacante.getNome() + " usou " + nome + "!");
            if (!acertou(atacante)) {
                System.out.println("O ataque errou!");
                return;
            }
            double mult = atacante.getTipo().calcMult(defensor.getTipo());
            if (mult > 1.0) System.out.println("É super efetivo!");
            else if (mult < 1.0) System.out.println("Não é muito efetivo...");

            double danoCalculado = calcularDanoBruto(atacante) * mult;


            String tipoAtacante = atacante.getTipo().getNomeTipo();
            if (climaAtual.equals("Asfalto Quente") && tipoAtacante.equals("Fogo")) {
                danoCalculado *= 1.15;
                System.out.println(" O Asfalto Quente potencializou o golpe de Fogo!");
            } else if (climaAtual.equals("Piso Escorregadio") && tipoAtacante.equals("Água")) {
                danoCalculado *= 1.10;
                System.out.println(" A Poça de Chuva amplificou o ataque de Água!");
            }

            defensor.dano((int) danoCalculado);

            if (Math.random() < 0.125) {
                if (tipoAtacante.equals("Fogo") && !defensor.isQueimado()) {
                    defensor.setQueimado(true);
                } else if (tipoAtacante.equals("Planta") && !defensor.isEnvenenado()) {
                    defensor.setEnvenenado(true);
                } else if (tipoAtacante.equals("Água") && !defensor.isParalisado()) {
                    defensor.setParalisado(true);
                }
            }
        }

        @Override
        public void executar(Pokemon atacante, Pokemon defensor) {
            executar(atacante, defensor, "Normal");
        }
    }

    public static class Buff extends Golpe {
        public Buff(String nome) { super(nome, 0); }

        @Override
        public void executar(Pokemon atacante, Pokemon defensor) {
            System.out.println("\n>>> " + atacante.getNome() + " usou " + nome + "!");
            atacante.getTipo().apBf(atacante);
            atacante.diminuirPrecisao(10);
        }
    }

    public static class Debuff extends Golpe {
        public Debuff(String nome, int poderBase) { super(nome, poderBase); }

        @Override
        public void executar(Pokemon atacante, Pokemon defensor) {
            System.out.println("\n>>> " + atacante.getNome() + " usou " + nome + "!");
            if (!acertou(atacante)) {
                System.out.println("O ataque errou!");
                return;
            }
            int dano = calcularDanoBruto(atacante);
            defensor.dano(dano);
            defensor.getTipo().apDb(defensor);
        }
    }
}