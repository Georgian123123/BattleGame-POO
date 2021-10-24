package players;

import getdate.Player;

import static java.lang.Math.min;
import static java.lang.Math.round;

public final class Wizard extends Player {
    private Integer dmg;
    private boolean isOnSpecialField = false;
    private Integer dmgDeflect;
    private static final Integer CONST1 = 5;
    private static final Integer CONST2 = 40;
    private static final Float CONST3 = 100f;
    private static final Integer CONST4 = 200;
    private static final Integer CONST5 = 250;
    private static final Integer CONST6 = 50;
    private static final Integer CONST7 = 30;
    private static final Float CONST8 = 0.1f;
    private static final Float CONST9 = 0.3f;

    public Wizard(final Integer hp, final Integer xp,
                  final Integer dmg, final Integer dmgDeflect,
                  final boolean isOnSpecialField,
                  final Integer posX, final Integer posy) {
        super(hp, xp, "W", posX, posy);
        this.dmg = dmg;
        this.dmgDeflect = dmgDeflect;
        this.isOnSpecialField = isOnSpecialField;
    }

    public void fightWith(final Player playerFight) {
        if (playerFight instanceof Wizard) {
            playerFight.wizzardVsWizzard(this);
        }
        if (playerFight instanceof Rogue) {
            playerFight.wizzardVsRogue(this);
        }
        if (playerFight instanceof Knight) {
            playerFight.wizzardVsKnight(this);
        }
        if (playerFight instanceof Pyromancer) {
            playerFight.wizzardVsPyromancer(this);
        }
    }

    public Integer getDmgDeflect() {
        return dmgDeflect;
    }

    public void setDmgDeflect(final Integer dmgSlam) {
        this.dmgDeflect = dmgSlam;
    }

    public Integer getDmgDefletAfterLvl() {
        return 2;
    }

    public Integer getAddHpAfterLvl() {
        return CONST7;
    }

    public Integer getDmg() {
        return dmg;
    }

    public void setDmg(final Integer dmg) {
        this.dmg = dmg;
    }

    public Integer getAddDmgAfterLvl() {
        return CONST1;
    }

    public Integer getWizzardDrainDmg(final Player player,
                                      final float rase) {
        Float fieldAmplification = 1f;
        Integer minHp = Math.round(min(CONST9 * player.getMaxHP(), player.getHp()));
        Integer dmg = round((this.getDmg() / CONST3) * minHp);
        if (this.isOnSpecialField) {
            fieldAmplification = 1 + 0.1f * 1;
        }
        float raseAmplification = (1 + rase * 1);
        dmg = round(dmg * fieldAmplification * raseAmplification);
        return dmg;
    }

    public Integer getWizzardDeflectDmg(final float rogAmplf,
                                        final float rase,
                                        final Integer dMg, final Integer dMg2) {
        Float fieldAmplificationRog = rogAmplf;
        Float fieldAmplification = 1f;

        Integer dmg = round(dMg * fieldAmplificationRog
                + dMg2 * fieldAmplificationRog);
        dmg = round((this.getDmgDeflect() / CONST3) * dmg);
        if (this.isOnSpecialField()) {
            fieldAmplification = 1 + CONST8 * 1;
        }
        float raseAmplification = (1 + rase * 1);
        dmg = round(dmg * fieldAmplification * raseAmplification);
        return dmg;
    }

    public void win(final Player player) {
        Integer xPWinner = this.getXp()
                + Math.max(0, CONST4 - (this.getLvl() - player.getLvl()) * CONST2);
        this.setXp(xPWinner);
        Integer xPlevelUp = CONST5 + this.getLvl() * CONST6;
        if (xPlevelUp <= xPWinner) {
            this.setLvl((xPWinner - CONST5) / CONST6 + 1);
            this.setHp(this.getMaxHP() + this.getAddHpAfterLvl() * this.getLvl());
            this.setDmg(this.getDmg() + this.getAddDmgAfterLvl() * this.getLvl());
            this.setDmgDeflect(this.getDmgDefletAfterLvl() * this.getLvl()
                    + this.getDmgDeflect());
        }
    }

    public boolean isOnSpecialField() {
        return isOnSpecialField;
    }

    public void setOnSpecialField(final boolean onSpecialField) {
        isOnSpecialField = onSpecialField;
    }
}
