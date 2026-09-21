package pokeucsal;

/**
 * Representa um Pokésal em batalha, com atributos, golpes, status e precisão.
 */
public class Pokemon {
    // Precisão máxima e mínima em %.
    private static final int PRECISAO_MAXIMA = 100;
    private static final int PRECISAO_MINIMA = 30;

    // Paralisia deixa a velocidade em 75% do valor.
    private static final double FATOR_PARALISIA = 0.75;

    private final String nome;

    private final TipoPoke tipo;

    private final int maxHp;

    private final Golpe[] golpes;

    private final int baseAtk;

    private final int baseDef;

    private final int baseSpd;

    private int atk;

    private int def;

    private int hp;

    private int spd;

    private int precisao = PRECISAO_MAXIMA;

    private boolean queimado = false;

    private boolean envenenado = false;

    private boolean paralisado = false;

    private int turnosEnvenenado = 0;

    /**
     * Cria um PokéSal e guarda os atributos base, usados para restaurá-lo depois.
     *
     * @param nome nome do PokéSal
     * @param tipo tipo elemental (Fogo, Água ou Planta)
     * @param atk ataque inicial
     * @param def defesa inicial
     * @param hp vida inicial (também é a vida máxima)
     * @param spd velocidade inicial
     * @param golpes os golpes que o Pokémon sabe usar
     */
    public Pokemon(final String nome, final TipoPoke tipo, final int atk, final int def,
                   final int hp, final int spd, final Golpe[] golpes) {
        this.nome = nome;
        this.tipo = tipo;
        this.atk = atk;
        this.def = def;
        this.hp = hp;
        this.maxHp = hp;
        this.spd = spd;
        this.golpes = golpes;
        this.baseAtk = atk;
        this.baseDef = def;
        this.baseSpd = spd;
    }

    public String getNome() {
        return nome;
    }

    public TipoPoke getTipo() {
        return tipo;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(final int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(final int def) {
        this.def = def;
    }

    public int getHp() {
        return hp;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(final int spd) {
        this.spd = spd;
    }

    public int getPrecisao() {
        return precisao;
    }

    public Golpe[] getGolpes() {
        return golpes;
    }


    public boolean isQueimado() {
        return queimado;
    }

    /**
     * Define se o PokéSal está queimado.
     *
     * @param queimado para aplicar a queimadura
     */
    public void setQueimado(final boolean queimado) {
        this.queimado = queimado;
        if (queimado) {
            System.out.println(nome + " foi queimado!");
        }
    }

    public boolean isEnvenenado() {
        return envenenado;
    }

    /**
     * Define se o PokéSal está envenenado.
     *
     * @param envenenado para aplicar o veneno
     */
    public void setEnvenenado(final boolean envenenado) {
        this.envenenado = envenenado;
        if (envenenado) {
            this.turnosEnvenenado = 0;
            System.out.println(nome + " foi envenenado!");
        }
    }

    public boolean isParalisado() {
        return paralisado;
    }

    /**
     * Define se o PokéSal está paralisado (a velocidade cai).
     *
     * @param paralisado para aplicar a paralisia
     */
    public void setParalisado(final boolean paralisado) {
        this.paralisado = paralisado;
        if (paralisado) {
            this.spd = (int) (this.spd * FATOR_PARALISIA);
            System.out.println(nome + " foi Paralisado e sua Velocidade Caiu!");
        }
    }

    /**
     * Aplica no fim do turno o dano de queimadura e de veneno, se houver.
     */
    public void processarStatusFimDeTurno() {
        if (queimado) {
            final int danoQueimadura = Math.max(1, maxHp / 16);
            this.hp -= danoQueimadura;
            if (this.hp < 0) {
                this.hp = 0;
            }
            System.out.println(
                nome + " Sofreu " + danoQueimadura + " de Dano por Queimadura! HP: " + this.hp
                    + "/" + maxHp);
        }
        if (envenenado) {
            turnosEnvenenado++;
            final int danoVeneno = Math.max(1, (maxHp / 16) * turnosEnvenenado);
            this.hp -= danoVeneno;
            if (this.hp < 0) {
                this.hp = 0;
            }
            System.out.println(
                nome + " Sofreu " + danoVeneno + " de Dano por Veneno Acumulado! HP: " + this.hp
                    + "/" + maxHp);
        }
    }

    /**
     * Diminui a precisão, sem passar do mínimo permitido.
     *
     * @param valor pontos de precisão a diminuir
     */
    public void diminuirPrecisao(final int valor) {
        this.precisao -= valor;
        if (this.precisao < PRECISAO_MINIMA) {
            this.precisao = PRECISAO_MINIMA;
        }
        System.out.println("A Precisão de " + this.nome + " Caiu Para " + this.precisao + "%!");
    }

    /**
     * Aplica dano ao Pokémon, descontando parte da defesa e limitando o dano por golpe.
     *
     * @param danoBruto dano do golpe antes de descontar a defesa
     */
    public void dano(final int danoBruto) {
        final int danoEfetivo = Math.max(1, danoBruto - (this.def / 4));
        final int danoMaximoPermitido = (int) (this.maxHp * 0.45);
        final int danoFinal = Math.clamp(danoEfetivo, 1, danoMaximoPermitido);

        this.hp -= danoFinal;
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.println(
            this.nome + " recebeu " + danoFinal + " de dano! Vida restante: " + this.hp + "/"
                + this.maxHp);
    }

    /**
     * Remove todos os status negativos (queimado, envenenado e paralisado).
     */
    public void curarStatus() {
        this.queimado = false;
        this.envenenado = false;
        this.paralisado = false;
        this.turnosEnvenenado = 0;
    }

    /**
     * Recupera vida em porcentagem do HP máximo. Com 1.0 restaura também os atributos.
     *
     * @param porcentagem valor entre 0.0 e 1.0 (1.0 restaura tudo)
     */
    public void curar(final double porcentagem) {
        if (porcentagem >= 1.0) {
            this.hp = this.maxHp;
            this.atk = this.baseAtk;
            this.def = this.baseDef;
            this.spd = this.baseSpd;
            this.precisao = PRECISAO_MAXIMA;
        } else {
            this.hp += (int) (this.maxHp * porcentagem);
            if (this.hp > this.maxHp) {
                this.hp = this.maxHp;
            }
        }
        this.queimado = false;
        this.envenenado = false;
        this.paralisado = false;
        this.turnosEnvenenado = 0;
    }

    /**
     * Mostra no console os atributos atuais do PokéSal.
     */
    public void exibirSts() {
        System.out.println("--- Status de " + nome + " ---");
        System.out.println("Tipo: " + tipo.getNomeTipo());
        System.out.println("Vida: " + hp + "/" + maxHp);
        System.out.println("Ataque: " + atk);
        System.out.println("Defesa: " + def);
        System.out.println("Velocidade: " + spd);
        System.out.println("--------------------------");
    }
}
