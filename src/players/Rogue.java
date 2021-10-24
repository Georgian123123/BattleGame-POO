package players;

import getdate.Player;

import static java.lang.Math.round;

public final class Rogue extends Player {
    private Integer dmg;
    private boolean isOnSpecialField;
    private Integer dmgParalysis;
    private Integer howManyHits = 0;
    private static final Integer CONST1 = 10;
    private static final Integer CONST2 = 40;
    private static final Float CONST3 = 1.15f;
    private static final Integer CONST4 = 200;
    private static final Integer CONST5 = 250;
    private static final Integer CONST6 = 50;
    private static final Integer CONST7 = 20;
    public Rogue(final Integer hp, final Integer xp,
                 final Integer dmg, final Integer dmgParalysis,
                 final boolean isOnSpecialField, final Integer posX,
                 final Integer posy) {
        super(hp, xp, "R", posX, posy);
        this.dmg = dmg;
        this.dmgParalysis = dmgParalysis;
        this.isOnSpecialField = isOnSpecialField;
    }

    public Integer getHowManyHits() {
        return howManyHits;
    }

    public void setHowManyHits(final Integer howManyHits) {
        this.howManyHits = howManyHits;
    }

    public Integer getDmgParalysis() {
        return dmgParalysis;
    }

    public void setDmgParalysis(final Integer dmgParalysis) {
        this.dmgParalysis = dmgParalysis;
    }

    public void fightWith(final Player playerFight) {
        if (playerFight instanceof Wizard) {
            playerFight.rogueVsWizzard(this);
        }
        if (playerFight instanceof Rogue) {
            playerFight.rogueVsRogue(this);
        }
        if (playerFight instanceof Knight) {
            playerFight.rogueVsKnight(this);
        }
        if (playerFight instanceof Pyromancer) {
            playerFight.rogueVsPyromancer(this);
        }
    }

    public Integer rDmgBackstabAbility(final Float race) {
        Float fieldAmplification;
        Float crit = 1f;
        if (this.getLand() == 'W' && this.getHowManyHits() % 3 == 0) {
            fieldAmplification = CONST3;
            crit = 1.5f;
        } else if (this.getLand() == 'W') {
            fieldAmplification = CONST3;
        } else {
            fieldAmplification = 1f;
        }
        Float raseAmplification = 1 + race;
        Integer dmg = round(this.getDmg() * fieldAmplification
                * crit * raseAmplification);
        this.setHowManyHits(this.getHowManyHits() + 1);
        return dmg;
    }

    public Integer rDmgParalysisAbility(final Float race) {
        Float fieldAmplification;
        if (this.getLand() == 'W') {
            fieldAmplification = CONST3;
        } else {
            fieldAmplification = 1f;
        }
        Float raseAmplification = 1 + race;
        Integer dmg = round(this.getDmgParalysis()
                * fieldAmplification * raseAmplification);
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
            this.setDmgParalysis(this.getDmgParalysis()
                    + this.getDmgParalysisAfterLvl() * this.getLvl());
        }
    }

    public Integer getDmgParalysisAfterLvl() {
        return CONST1;
    }

    public Integer getAddHpAfterLvl() {
        return CONST2;
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
