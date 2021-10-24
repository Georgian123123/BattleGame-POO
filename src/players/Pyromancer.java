package players;

import getdate.Player;

public final class Pyromancer extends Player {
    private Integer dmg;
    private Integer ignite;
    private Integer dmgIgnitePerRound;
    private boolean isOnSpecialField = false;
    private static final Integer CONST2 = 40;
    private static final Float CONST3 = 1.25f;
    private static final Integer CONST4 = 200;
    private static final Integer CONST5 = 250;
    private static final Integer CONST6 = 50;
    private static final Integer CONST7 = 30;

    public Pyromancer(final Integer hp,
                      final Integer xp, final Integer dmg,
                      final Integer ignite, final Integer dmgIgnitePerRound,
                      final boolean isOnSpecialField,
                      final Integer posX, final Integer posy) {
        super(hp, xp, "P", posX, posy);
        this.dmg = dmg;
        this.ignite = ignite;
        this.dmgIgnitePerRound = dmgIgnitePerRound;
        this.isOnSpecialField = isOnSpecialField;
    }

    public Integer getAddHpAfterLvl() {
        return CONST6;
    }

    public Integer getDmg() {
        return dmg;
    }

    public void setDmg(final Integer dmg) {
        this.dmg = dmg;
    }

    public Integer getAddDmgAfterLvl() {
        return CONST6;
    }

    public Integer getIgnite() {
        return ignite;
    }

    public void setIgnite(final Integer ignite) {
        this.ignite = ignite;
    }

    public Integer getAddIgnite() {
        return 20;
    }

    public Integer getDmgIgnitePerRound() {
        return dmgIgnitePerRound;
    }

    public void setDmgIgnitePerRound(final Integer dmgIgnitePerRound) {
        this.dmgIgnitePerRound = dmgIgnitePerRound;
    }

    public void fightWith(final Player playerFight) {
        if (playerFight instanceof Wizard) {
            playerFight.pyromancerVsWiizard(this);
        }
        if (playerFight instanceof Rogue) {
            playerFight.pyromancerVsRogue(this);
        }
        if (playerFight instanceof Knight) {
            playerFight.pyromancerVsKnight(this);
        }
        if (playerFight instanceof Pyromancer) {
            playerFight.pyromancerVsPyromancer(this);
        }
    }

    public Integer getDmgFirstAbility(final Float race) {
        Float fieldAmplifier;
        if (this.isOnSpecialField()) {
            fieldAmplifier = CONST3;
        } else {
            fieldAmplifier = 1f;
        }
        return Math.round(this.getDmg() * fieldAmplifier * race);
    }

    public Integer getDmgSecondAbility(final Float race) {
        Float fieldAmplifier;
        if (this.isOnSpecialField()) {
            fieldAmplifier = CONST3;
        } else {
            fieldAmplifier = 1f;
        }
        return Math.round(this.getIgnite() * race * fieldAmplifier);
    }

    public void win(final Player player) {
        Integer xPWinner = this.getXp()
                + Math.max(0, CONST4 - (this.getLvl() - player.getLvl()) * CONST2);
        this.setXp(xPWinner);
        Integer xPlevelUp = CONST5 + this.getLvl() * CONST6;
        if (xPlevelUp <= xPWinner) {
            this.setLvl((xPWinner - CONST5) / CONST6 + 1);
            this.setHp(this.getMaxHP()
                    + this.getAddHpAfterLvl() * this.getLvl());
            this.setDmg(this.getDmg()
                    + this.getAddDmgAfterLvl() * this.getLvl());
            this.setIgnite(this.getAddIgnite() * this.getLvl()
                    + this.getIgnite());
            this.setDmgIgnitePerRound(this.getAddIgniteAfterLvl() * this.getLvl()
                    + this.getDmgIgnitePerRound());
        }
    }

    public Integer getAddIgniteAfterLvl() {
        return CONST7;
    }

    public boolean isOnSpecialField() {
        return isOnSpecialField;
    }

    public void setOnSpecialField(final boolean onSpecialField) {
        isOnSpecialField = onSpecialField;
    }
}
