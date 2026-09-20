package pokeucsal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokemonTest {

    @Test
    public void testBuffFogoModificaAtributos() {
        final TipoFogo tipoFogo = new TipoFogo();
        final Pokemon charsal = new Pokemon("CharSal", tipoFogo, 50, 30, 40, 60, new Golpe[0]);

        final int atkInicial = charsal.getAtk();
        final int defInicial = charsal.getDef();

        // Executa o buff (aumenta ATK em +10 e reduz DEF em -5)
        tipoFogo.apBf(charsal);

        // Valida se a alteracao ocorreu corretamente
        assertEquals(atkInicial + 10, charsal.getAtk(), "O ataque deveria ter aumentado em 10.");
        assertEquals(defInicial - 5, charsal.getDef(), "A defesa deveria ter diminuido em 5.");
    }

    @Test
    public void testDebuffFogoModificaDefesaAlvo() {
        final TipoFogo tipoFogo = new TipoFogo();
        final Pokemon alvo = new Pokemon("BulbaSal", new TipoPlanta(), 40, 30, 40, 40, new Golpe[0]);

        final int defInicial = alvo.getDef();

        // Executa o debuff (reduz a defesa do alvo em -5)
        tipoFogo.apDb(alvo);

        // Valida se a defesa do oponente baixou
        assertEquals(defInicial - 5, alvo.getDef(), "A defesa do alvo deveria ter diminuido em 5.");
    }
}