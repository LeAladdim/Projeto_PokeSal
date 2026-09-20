package pokeucsal;

public class TipoFogo implements TipoPoke {

    @Override
    public String getNomeTipo() {
        return "Fogo";
    }

    @Override
    public double calcMult(TipoPoke defensor) {
        if (defensor.getNomeTipo().equals("Planta")) {
            return 2.0;
        } else if (defensor.getNomeTipo().equals("Água")) {
            return 0.5;
        }
        return 1.0;
    }

    @Override
    public void apBf(final Pokemon usuario) {
        usuario.setAtk(usuario.getAtk() + 10);
        usuario.setDef(usuario.getDef() - 5);
        System.out.println("A Chama Cresce! O Ataque de " + usuario.getNome() + " Aumentou Para " +
            usuario.getAtk() + " e a Defesa Diminuiu para " + usuario.getDef() +
            " Devido ao Esforço Excessivo!");
        Batalha.pausar(1500);
    }

    @Override
    public void apDb(final Pokemon alvo) {
        alvo.setDef(alvo.getDef() - 5);
        System.out.println("O Inimigo está Super-Aquecido! A Defesa de " + alvo.getNome() + " " +
            "diminuiu para " +
            alvo.getDef() + " Devido ao Calor");
        Batalha.pausar(1500);
    }
}