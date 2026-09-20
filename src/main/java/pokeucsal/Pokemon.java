package pokeucsal;

public class Pokemon {
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

    private int precisao = 100;

    private boolean queimado = false;

    private boolean envenenado = false;

    private boolean paralisado = false;

    private int turnosEnvenenado = 0;

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

    public void setQueimado(final boolean queimado) {
        this.queimado = queimado;
        if (queimado) {
            System.out.println(nome + " foi queimado!");
        }
    }

    public boolean isEnvenenado() {
        return envenenado;
    }

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

    public void setParalisado(final boolean paralisado) {
        this.paralisado = paralisado;
        if (paralisado) {
            this.spd = (int) (this.spd * 0.75);
            System.out.println(nome + " foi Paralisado e sua Velocidade Caiu!");
        }
    }

    public void processarStatusFimDeTurno() {
        if (queimado) {
            final int danoQueimadura = Math.max(1, maxHp / 16);
            this.hp -= danoQueimadura;
            if (this.hp < 0) {
                this.hp = 0;
            }
            System.out.println(
                nome + " Sofreu " + danoQueimadura + " de Dano por Queimadura! HP: " + this.hp +
                    "/" + maxHp);
        }
        if (envenenado) {
            turnosEnvenenado++;
            final int danoVeneno = Math.max(1, (maxHp / 16) * turnosEnvenenado);
            this.hp -= danoVeneno;
            if (this.hp < 0) {
                this.hp = 0;
            }
            System.out.println(
                nome + " Sofreu " + danoVeneno + " de Dano por Veneno Acumulado! HP: " + this.hp +
                    "/" + maxHp);
        }
    }

    public void diminuirPrecisao(final int valor) {
        this.precisao -= valor;
        if (this.precisao < 30) {
            this.precisao = 30;
        }
        System.out.println("A Precisão de " + this.nome + " Caiu Para " + this.precisao + "%!");
    }

    public void dano(final int danoBruto) {
        final int danoEfetivo = Math.max(1, danoBruto - (this.def / 4));
        final int danoMaximoPermitido = (int) (this.maxHp * 0.45);
        final int danoFinal = Math.min(danoEfetivo, Math.max(1, danoMaximoPermitido));

        this.hp -= danoFinal;
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.println(
            this.nome + " recebeu " + danoFinal + " de dano! Vida restante: " + this.hp + "/" +
                this.maxHp);
    }

    public void curarStatus() {
        this.queimado = false;
        this.envenenado = false;
        this.paralisado = false;
        this.turnosEnvenenado = 0;
    }

    public void curar(final double porcentagem) {
        if (porcentagem >= 1.0) {
            this.hp = this.maxHp;
            this.atk = this.baseAtk;
            this.def = this.baseDef;
            this.spd = this.baseSpd;
            this.precisao = 100;
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