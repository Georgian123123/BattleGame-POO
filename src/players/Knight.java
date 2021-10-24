package players;

import getdate.Player;

public final class Knight extends Player {
    private Integer dmg;
    private boolean isOnSpecialField = false;
    private Integer dmgSlam;
    private Float hpLimit = 0.20f;
    private static final Float CONST1 = 0.1f;
    private static final Integer CONST2 = 40;
    private static final Float CONST3 = 1.15f;
    private static final Integer CONST4 = 200;
    private static final Integer CONST5 = 250;
    private static final Integer CONST6 = 50;
    private static final Integer CONST7 = 30;
    private static final Integer CONST8 = 80;
    public Knight(final Integer dmg, final Integer dmgSlam,
                  final boolean isOnSpecialField,
                  final Integer hp, final Integer xp,
                  final Integer posX, final Integer posy) {
        super(hp, xp, "K", posX, posy);
        this.dmg = dmg;
        this.dmgSlam = dmgSlam;
        this.isOnSpecialField = isOnSpecialField;
    }
    public void setHPLIMIT(final Float hp) {
        this.hpLimit = hp;
    }

    public Float getHpLimit() {
        return hpLimit;
    }

    public Float addHpLimit() {
        return CONST1;
    }

    public Integer getDmgSlam() {
        return dmgSlam;
    }

    public void setDmgSlam(final Integer dmgSlam) {
        this.dmgSlam = dmgSlam;
    }

    public Integer getDmgSlamAfterLvl() {
        return CONST2;
    }

    public void fightWith(final Player playerFight) {
        if (playerFight instanceof Wizard) {
            playerFight.knightVsWizzard(this);
        }
        if (playerFight instanceof Rogue) {
            playerFight.knightVsRogue(this);
        }
        if (playerFight instanceof Knight) {
            playerFight.knightVsKnight(this);
        }
        if (playerFight instanceof Pyromancer) {
            playerFight.knightVsPyromancer(this);
        }
    }

    public Integer getDmgFAbility(final Float race) {
        Float fieldAmplifier;
        if (this.isOnSpecialField()) {
            fieldAmplifier = CONST3;
        } else {
            fieldAmplifier = 1f;
        }
        return Math.round(this.getDmg() * fieldAmplifier * race);
    }

    public Integer getDmgSAbility(final Float race) {
        Float fieldAmplifier;
        if (this.isOnSpecialField()) {
            fieldAmplifier = CONST3;
        } else {
            fieldAmplifier = 1f;
        }

        return Math.round(this.getDmgSlam() * fieldAmplifier * race);

    }

    public void win(final Player player) {
        Integer xPWinner = this.getXp()
                + Math.max(0, CONST4 - (this.getLvl() - player.getLvl()) * CONST2);
        this.setXp(xPWinner);
        Integer xPlevelUp = CONST5 + this.getLvl() * 50;
        if (xPlevelUp <= xPWinner) {
            this.setLvl((xPWinner - CONST5) / CONST6 + 1);
            this.setHp(this.getMaxHP() + this.getAddHpAfterLvl() * this.getLvl());
            this.setDmg(this.getDmg() + this.getAddDmgAfterLvl() * this.getLvl());
            this.setDmgSlam(this.getDmgSlamAfterLvl() * this.getLvl()
                    + this.getDmgSlam());
            this.setHPLIMIT(this.addHpLimit() * this.getLvl() + this.getHpLimit());
        }
    }

    public Integer getAddHpAfterLvl() {
        return CONST8;
    }

    public Integer getDmg() {
        return dmg;
    }

    public void setDmg(final Integer dmg) {
        this.dmg = dmg;
    }

    public Integer getAddDmgAfterLvl() {
        return CONST7;
    }

    public boolean isOnSpecialField() {
        return isOnSpecialField;
    }

    public void setOnSpecialField(final boolean onSpecialField) {
        isOnSpecialField = onSpecialField;
    }
}
