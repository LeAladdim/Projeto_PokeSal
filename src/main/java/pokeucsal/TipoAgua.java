package pokeucsal;

public class TipoAgua implements TipoPoke {

    @Override
    public String getNomeTipo() {
        return "Água";
    }

    @Override
    public double calcMult(final TipoPoke defensor) {
        final String tipoDef = defensor.getNomeTipo();
        if (tipoDef.equals("Fogo")) {
            return 2.0;
        } else if (tipoDef.equals("Planta")) {
            return 0.5; // Água é fraco contra Água e Planta
        }
        return 1.0;
    }

    @Override
    public void apBf(final Pokemon usuario) {
        // As linhas matemáticas abaixo SÃO OBRIGATÓRIAS para o atributo mudar de verdade
        usuario.setSpd(usuario.getSpd() + 10);
        usuario.setAtk(usuario.getAtk() - 5);

        System.out.println("Correnteza agil! A Velocidade de " + usuario.getNome() +
            " aumentou para " + usuario.getSpd() + " e o Ataque diminuiu para " +
            usuario.getAtk() + " devido ao esforco para nadar!");
        Batalha.pausar(1500);
    }

    @Override
    public void apDb(final Pokemon alvo) {
        // Esta linha subtrai o ataque do oponente
        alvo.setAtk(alvo.getAtk() - 5);

        System.out.println("O inimigo Está se Afogando! O Ataque de " + alvo.getNome() +
            " Diminuiu para " + alvo.getAtk() + " Devido ao Sufocamento!");
        Batalha.pausar(1500);
    }
}