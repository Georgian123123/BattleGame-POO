package getdate;
import constants.Wizzardconst;
import players.Knight;
import players.Pyromancer;
import players.Rogue;
import players.Wizard;

import java.util.ArrayList;

public class Player {
    private Integer lvl = 0;
    private Integer hp;
    private Integer xp;
    private String typeOfCharacter;
    private Integer initialPositionX;
    private Integer initialPositionY;
    private ArrayList<String> moves = new ArrayList<String>();
    private Character land;
    private boolean canMove = true;
    private boolean fought = false;
    private boolean isAlive = true;
    private Integer maxHP;
    private Integer howManyRounds = 0;
    private Integer extraDmg = 0;
    public static final Wizzardconst WZ = new Wizzardconst();
    public static final int NUMBER  = 3;
    public static final int NUMBER2  = 6;
    public Player() {
    }

    public Player(final String typeOfCharacter,
                  final Integer initialPositionX,
                  final Integer initialPositionY) {
        this.typeOfCharacter = typeOfCharacter;
        this.initialPositionX = initialPositionX;
        this.initialPositionY = initialPositionY;
    }

    public Player(final Integer hp,
                  final Integer xp,
                  final String typeOfCharacter,
                  final Integer initialPositionX,
                  final Integer initialPositionY) {
        this.hp = hp;
        this.xp = xp;
        this.typeOfCharacter = typeOfCharacter;
        this.initialPositionX = initialPositionX;
        this.initialPositionY = initialPositionY;
        maxHP = this.hp;
    }

    public final void fight(final Wizard playerToFight) {
        playerToFight.fightWith(this);
    }

    public final void fight(final Rogue playerToFight) {
        playerToFight.fightWith(this);
    }

    public final void fight(final Knight playerToFight) {
        playerToFight.fightWith(this);
    }

    public final void fight(final Pyromancer playerToFight) {
        playerToFight.fightWith(this);
    }

    public final void wizzardVsWizzard(final Wizard player) {
        Integer w1DmgDrain;
        Integer w2DmgDrain;
        if (this.getLand() == 'D') {
            ((Wizard) this).setOnSpecialField(true);
        }
        if (player.getLand() == 'D') {
            player.setOnSpecialField(true);
        }
        w2DmgDrain = ((Wizard) this).getWizzardDrainDmg(player, WZ.WIZZARD1);

        w1DmgDrain = player.getWizzardDrainDmg(this, WZ.WIZZARD1);
        Integer w1Total = w1DmgDrain;
        Integer w2Total = w2DmgDrain;

        this.setHp(this.getHp() - w1Total);
        player.setHp(player.getHp() - w2Total);

        if (this.getHp() <= 0 && player.getHp() <= 0) {
            this.isAlive = false;
            ((Player) player).isAlive = false;
        } else if (this.getHp() <= 0) {
            player.win(this);
            this.isAlive = false;
        } else if (player.getHp() <= 0) {
            ((Wizard) this).win(player);
            ((Player) player).isAlive = false;
        }
        player.setOnSpecialField(false);
        ((Wizard) this).setOnSpecialField(false);
    }

    public final void wizzardVsRogue(final Wizard player) {
        Integer wDmgDeflectAbility;
        if (this.isAlive && player.isAlive()) {
            if (player.getLand() == 'D') {
                player.setOnSpecialField(true);
            }
            if (this.getLand() == 'W') {
                ((Rogue) this).setOnSpecialField(true);
            }
            Rogue player2 = (Rogue) this;
            Integer wDmgDrainAbility =
                    player.getWizzardDrainDmg(player2, -WZ.KNIGHT1);
            if (((Rogue) this).isOnSpecialField()
                    && ((Rogue) this).getHowManyHits() % NUMBER == 0) {
                wDmgDeflectAbility =
                        player.getWizzardDeflectDmg(WZ.ROGAMPLF,
                                WZ.KNIGHT1,
                                Math.round(player2.getDmg() * WZ.ROGBONUS),
                                player2.getDmgParalysis());
            } else if (((Rogue) this).isOnSpecialField()) {
                wDmgDeflectAbility =
                        player.getWizzardDeflectDmg(WZ.ROGAMPLF,
                                WZ.KNIGHT1, player2.getDmg(),
                                player2.getDmgParalysis());
            } else {
                wDmgDeflectAbility =
                        player.getWizzardDeflectDmg(1f,
                                WZ.KNIGHT1, player2.getDmg(),
                                player2.getDmgParalysis());
            }
            Integer wizzardTotalDmg = wDmgDeflectAbility + wDmgDrainAbility;

            Integer rDmgBackstabAbility =
                    player2.rDmgBackstabAbility(WZ.PYROCONST);
            Integer rDmgParalysisAbility =
                    player2.rDmgParalysisAbility(WZ.PYROCONST);
            Integer rogueTotalDmg =
                    rDmgBackstabAbility + rDmgParalysisAbility;
            if (this.getLand() == 'W') {
                player.setHowManyRounds(NUMBER2);
                player.setCanMove(false);
            } else {
                player.setHowManyRounds(NUMBER);
                player.setCanMove(false);
            }
            player.setExtraDmg(((Rogue) this).getDmgParalysis());

            player.setHp(player.getHp() - rogueTotalDmg);
            this.setHp(this.getHp() - wizzardTotalDmg);

            if (this.getHp() <= 0 && player.getHp() <= 0) {
                this.isAlive = false;
                ((Player) player).isAlive = false;
            } else if (this.getHp() <= 0) {
                player.win(this);
                this.isAlive = false;
            } else if (player.getHp() <= 0) {
                ((Rogue) this).win(player);
                ((Player) player).isAlive = false;
            }
            player.setOnSpecialField(false);
            ((Rogue) this).setOnSpecialField(false);
        }
    }

    public final void wizzardVsKnight(final Wizard player) {
        Integer wDmgDeflectAbility;
        if (this.isAlive && player.isAlive()) {
            if (player.getLand() == 'D') {
                player.setOnSpecialField(true);
            }
            if (this.getLand() == 'L') {
                ((Knight) this).setOnSpecialField(true);
            }
            Knight player2 = (Knight) this;
            Integer wDmgDrainAbility =
                    player.getWizzardDrainDmg(player2, WZ.KNIGHT1);
            if (((Knight) this).isOnSpecialField()) {
                wDmgDeflectAbility = player.getWizzardDeflectDmg(WZ.ROGAMPLF,
                        WZ.PYROCONST2, player2.getDmg(), player2.getDmgSlam());
            } else {
                wDmgDeflectAbility =
                        player.getWizzardDeflectDmg(1f,
                                WZ.PYROCONST2, player2.getDmg(),
                                player2.getDmgSlam());
            }
            Integer wizzardTotalDmg = wDmgDeflectAbility + wDmgDrainAbility;
            Integer hpLimit =
                    Math.round((player.getMaxHP()
                            + (player.getLvl() * player.getAddHpAfterLvl()))
                            * ((Knight) this).getHpLimit());
            Integer kDmg1;
            if (player.getHp() < hpLimit) {
                kDmg1 = player.getHp();
            } else {
                kDmg1 = ((Knight) this).getDmgFAbility(1f - WZ.ROG1);
            }
            Integer kDmg2 = ((Knight) this).getDmgSAbility(1f + WZ.WIZZARD1);
            Integer knightDmg = kDmg1 + kDmg2;
            player.setHowManyRounds(1);
            player.setExtraDmg(0);
            player.setCanMove(false);
            this.setHp(this.getHp() - wizzardTotalDmg);
            player.setHp(player.getHp() - knightDmg);
            if (this.getHp() <= 0 && player.getHp() <= 0) {
                this.isAlive = false;
                ((Player) player).isAlive = false;
            } else if (this.getHp() <= 0) {
                player.win(this);
                this.isAlive = false;
            } else if (player.getHp() <= 0) {
                ((Knight) this).win(player);
                ((Player) player).isAlive = false;
            }
            player.setOnSpecialField(false);
            ((Knight) this).setOnSpecialField(false);
        }
    }

    public final void wizzardVsPyromancer(final Wizard player) {
        Integer wDmgDeflectAbility;
        float add = 1f;
        if (this.isAlive && player.isAlive()) {
            if (player.getLand() == 'D') {
                player.setOnSpecialField(true);
            }
            if (this.getLand() == 'V') {
                ((Pyromancer) this).setOnSpecialField(true);
                add = WZ.WZCONST;
            }
            Pyromancer player2 = (Pyromancer) this;
            Integer wDmgDrainAbility =
                    player.getWizzardDrainDmg(player2, -WZ.BONUS);
            if (((Pyromancer) this).isOnSpecialField()) {
                wDmgDeflectAbility = player.getWizzardDeflectDmg(WZ.WZCONST,
                        WZ.PYRO, player2.getDmg(), player2.getIgnite());
            } else {
                wDmgDeflectAbility =
                        player.getWizzardDeflectDmg(1f, WZ.PYRO,
                                player2.getDmg(), player2.getIgnite());
            }
            Integer wizzardTotalDmg = wDmgDeflectAbility + wDmgDrainAbility;

            Integer pDmgF = ((Pyromancer) this).getDmgFirstAbility(1f + WZ.WIZZARD1);
            Integer pDmgS = ((Pyromancer) this).getDmgSecondAbility(1f + WZ.WIZZARD1);
            Integer pyroTotal = pDmgF + pDmgS;

            player.setHp(player.getHp() - pyroTotal);
            this.setHp(this.getHp() - wizzardTotalDmg);

            player.setHowManyRounds(2);
            player.setExtraDmg(Math.round(((Pyromancer) this).getDmgIgnitePerRound()
                    * (1f + WZ.WIZZARD1) * add));

            if (this.getHp() <= 0 && player.getHp() <= 0) {
                this.isAlive = false;
                ((Player) player).isAlive = false;
            } else if (this.getHp() <= 0) {
                player.win(this);
                this.isAlive = false;
            } else if (player.getHp() <= 0) {
                ((Pyromancer) this).win(player);
                ((Player) player).isAlive = false;
            }
            player.setOnSpecialField(false);
            ((Pyromancer) this).setOnSpecialField(false);
        }
    }

    public final void rogueVsRogue(final Rogue player) {
        Integer r1dmg1 = player.rDmgBackstabAbility(-WZ.KNIGHT1);
        Integer r1dmg2 = player.rDmgParalysisAbility(-WZ.BONUS);
        Integer r1Total = r1dmg1 + r1dmg2;

        Integer r2dmg1 = ((Rogue) this).rDmgBackstabAbility(WZ.KNIGHT1);
        Integer r2dmg2 = ((Rogue) this).rDmgParalysisAbility(-WZ.PYRO1);
        Integer r2Total = r2dmg1 + r2dmg2;

        if (player.getLand() == 'W') {
            this.setExtraDmg(r2dmg2);
            this.setHowManyRounds(NUMBER2);
        } else {
            this.setExtraDmg(r2dmg2);
            this.setHowManyRounds(NUMBER);
        }

        if (this.getLand() == 'W') {
            player.setExtraDmg(r1dmg2);
            player.setHowManyRounds(NUMBER2);
        } else {
            player.setExtraDmg(r1dmg2);
            player.setHowManyRounds(NUMBER);
        }

        player.setHp(player.getHp() - r2Total);
        this.setHp(this.getHp() - r1Total);

        player.setCanMove(false);
        this.setCanMove(false);
        if (this.getHp() <= 0 && player.getHp() <= 0) {
            this.isAlive = false;
            ((Player) player).isAlive = false;
        } else if (this.getHp() <= 0) {
            player.win(this);
            this.isAlive = false;
        } else if (player.getHp() <= 0) {
            ((Rogue) this).win(player);
            ((Player) player).isAlive = false;
        }

        player.setOnSpecialField(false);
        ((Rogue) this).setOnSpecialField(false);

    }

    public final void rogueVsKnight(final Rogue player) {
        if (player.getLand() == 'W') {
            player.setOnSpecialField(true);
        }
        Integer r1dmg1 = player.rDmgBackstabAbility(-WZ.BONUS);
        Integer r1dmg2 = player.rDmgParalysisAbility(-WZ.KNIGHT1);
        Integer r1Total = r1dmg1 + r1dmg2;
        Integer hpLimit = Math.round((player.getMaxHP()
                + (player.getLvl() * player.getAddHpAfterLvl()))
                * ((Knight) this).getHpLimit());
        Integer kDmg1;
        if (player.getHp() < hpLimit) {
            kDmg1 = player.getHp();
        } else {
            kDmg1 = ((Knight) this).getDmgFAbility(WZ.ROGAMPLF);
        }
        Integer kDmg2 = ((Knight) this).getDmgSAbility(1f - WZ.KNIGHT1);
        Integer knightDmg = kDmg1 + kDmg2;
        player.setHowManyRounds(1);
        player.setExtraDmg(0);
        player.setCanMove(false);
        if (player.getLand() == 'W') {
            this.setHowManyRounds(NUMBER2);
            this.setCanMove(false);
        } else {
            this.setHowManyRounds(NUMBER);
            this.setCanMove(false);
        }
        this.setExtraDmg(r1dmg2);

        this.setHp(this.getHp() - r1Total);
        player.setHp(player.getHp() - knightDmg);

        if (this.getHp() <= 0 && player.getHp() <= 0) {
            this.isAlive = false;
            ((Player) player).isAlive = false;
        } else if (this.getHp() <= 0) {
            player.win(this);
            this.isAlive = false;
        } else if (player.getHp() <= 0) {
            ((Knight) this).win(player);
            ((Player) player).isAlive = false;
        }

        player.setOnSpecialField(false);
        ((Knight) this).setOnSpecialField(false);

    }

    public final void rogueVsPyromancer(final Rogue player) {
        float add = 1f;
        if (player.getLand() == 'W') {
            player.setOnSpecialField(true);
        }
        if (this.getLand() == 'V') {
            ((Pyromancer) this).setOnSpecialField(true);
            add = WZ.WZCONST;
        }
        Integer r1dmg1 = player.rDmgBackstabAbility(WZ.PYROCONST);
        Integer r1dmg2 = player.rDmgParalysisAbility(WZ.ROG1);
        Integer r1Total = r1dmg1 + r1dmg2;

        Integer pDmgF = ((Pyromancer) this).getDmgFirstAbility(1f - WZ.KNIGHT1);
        Integer pDmgS = ((Pyromancer) this).getDmgSecondAbility(1f - WZ.KNIGHT1);
        Integer pyroTotal = pDmgF + pDmgS;

        player.setHp(player.getHp() - pyroTotal);
        this.setHp(this.getHp() - r1Total);

        player.setHowManyRounds(2);
        player.setExtraDmg(Math.round(((Pyromancer) this).getDmgIgnitePerRound()
                * WZ.BONUSADD * add));
        player.setCanMove(true);

        if (player.getLand() == 'W') {
            this.setHowManyRounds(NUMBER2);
            this.setCanMove(false);
        } else {
            this.setHowManyRounds(NUMBER);
            this.setCanMove(false);
        }
        this.setExtraDmg(r1dmg2);

        if (this.getHp() <= 0 && player.getHp() <= 0) {
            this.isAlive = false;
            ((Player) player).isAlive = false;
        } else if (this.getHp() <= 0) {
            player.win(this);
            this.isAlive = false;
        } else if (player.getHp() <= 0) {
            ((Pyromancer) this).win(player);
            ((Player) player).isAlive = false;
        }

        player.setOnSpecialField(false);
        ((Pyromancer) this).setOnSpecialField(false);

    }

    public final void knightVsKnight(final Knight player) {
        if (player.getLand() == 'L') {
            player.setOnSpecialField(true);
        }
        if (this.getLand() == 'L') {
            ((Knight) this).setOnSpecialField(true);
        }
        Integer hpLimit = Math.round((player.getMaxHP() + (player.getLvl() * player.getAddHpAfterLvl()))
                * ((Knight) this).getHpLimit());
        Integer kDmg1;
        if (player.getHp() < hpLimit) {
            kDmg1 = player.getHp();
        } else {
            kDmg1 = ((Knight) this).getDmgFAbility(1f);
        }
        Integer kDmg2 = ((Knight) this).getDmgSAbility(1f + WZ.KNIGHT1);
        Integer knightDmg = kDmg1 + kDmg2;

        Integer hpLimit2 = Math.round((this.getMaxHP()
                + (this.getLvl() * ((Knight) this).getAddHpAfterLvl()))
                * player.getHpLimit());
        Integer kDmg3;
        if (this.getHp() < hpLimit2) {
            kDmg3 = this.getHp();
        } else {
            kDmg3 = player.getDmgFAbility(1f);
        }
        Integer kDmg4 = player.getDmgSAbility(1f + WZ.KNIGHT1);
        Integer knightDmg2 = kDmg3 + kDmg4;

        player.setHowManyRounds(1);
        player.setExtraDmg(0);
        player.setCanMove(false);
        this.setHowManyRounds(1);
        this.setExtraDmg(0);
        this.setCanMove(false);
        this.setHp(this.getHp() - knightDmg2);
        player.setHp(player.getHp() - knightDmg);

        if (this.getHp() <= 0 && player.getHp() <= 0) {
            this.isAlive = false;
            ((Player) player).isAlive = false;
        } else if (this.getHp() <= 0) {
            player.win(this);
            this.isAlive = false;
        } else if (player.getHp() <= 0) {
            ((Knight) this).win(player);
            ((Player) player).isAlive = false;
        }
        player.setOnSpecialField(false);
        ((Knight) this).setOnSpecialField(false);
    }

    public final void knightVsPyromancer(final Knight player) {
        float add = 1f;
        if (player.getLand() == 'D') {
            player.setOnSpecialField(true);
        }
        if (this.getLand() == 'V') {
            ((Pyromancer) this).setOnSpecialField(true);
            add = WZ.WZCONST;
        }

        Integer pDmgF = ((Pyromancer) this).getDmgFirstAbility(1f + WZ.KNIGHT1);
        Integer pDmgS = ((Pyromancer) this).getDmgSecondAbility(1f + WZ.KNIGHT1);
        Integer pyroTotal = pDmgF + pDmgS;
        Integer hpLimit2 = Math.round((this.getMaxHP()
                + (this.getLvl() * ((Pyromancer) this).getAddHpAfterLvl()))
                * player.getHpLimit());
        Integer kDmg3;
        if (this.getHp() < hpLimit2) {
            kDmg3 = this.getHp();
        } else {
            kDmg3 = player.getDmgFAbility(WZ.PYRO1 + 1f);
        }
        Integer kDmg4 = player.getDmgSAbility(1f - WZ.PYRO1);
        Integer knightDmg2 = kDmg3 + kDmg4;

        this.setHowManyRounds(1);
        this.setExtraDmg(0);
        this.setCanMove(false);
        player.setHowManyRounds(2);
        player.setExtraDmg(Math.round(((Pyromancer) this).getDmgIgnitePerRound()
                * WZ.BONUSCONST * add));
        player.setCanMove(true);

        this.setHp(this.getHp() - knightDmg2);
        player.setHp(player.getHp() - pyroTotal);
        if (this.getHp() <= 0 && player.getHp() <= 0) {
            this.isAlive = false;
            ((Player) player).isAlive = false;
        } else if (this.getHp() <= 0) {
            player.win(this);
            this.isAlive = false;
        } else if (player.getHp() <= 0) {
            ((Pyromancer) this).win(player);
            ((Player) player).isAlive = false;
        }
        player.setOnSpecialField(false);
        ((Pyromancer) this).setOnSpecialField(false);

    }

    public final void pyromancerVsPyromancer(final Pyromancer player) {
        float add = 1f;
        if (player.getLand() == 'V') {
            player.setOnSpecialField(true);
            add = WZ.WZCONST;
        }
        if (this.getLand() == 'V') {
            ((Pyromancer) this).setOnSpecialField(true);
            add = WZ.WZCONST;
        }
        Integer p1DmgF = ((Pyromancer) this).getDmgFirstAbility(1f + WZ.PYRO1);
        Integer p1DmgS = ((Pyromancer) this).getDmgSecondAbility(1f + WZ.PYRO1);
        Integer pyroTotal1 = p1DmgF + p1DmgS;

        Integer p2DmgF = ((Pyromancer) this).getDmgFirstAbility(1f + WZ.PYRO1);
        Integer p2DmgS = ((Pyromancer) this).getDmgSecondAbility(1f + WZ.PYRO1);
        Integer pyroTotal2 = p2DmgF + p2DmgS;

        player.setHp(player.getHp() - pyroTotal1);
        this.setHp(this.getHp() - pyroTotal2);

        player.setHowManyRounds(2);
        player.setExtraDmg(Math.round(((Pyromancer) this).getDmgIgnitePerRound() * WZ.BONUS3
                * add));
        this.setHowManyRounds(2);
        this.setExtraDmg(Math.round(player.getDmgIgnitePerRound() * WZ.BONUS3 * add));

        if (this.getHp() <= 0) {
            player.win(this);
            this.isAlive = false;
        }
        if (player.getHp() <= 0) {
            ((Pyromancer) this).win(player);
            ((Player) player).isAlive = false;
        }
        player.setOnSpecialField(false);
        ((Pyromancer) this).setOnSpecialField(false);
    }

    public final void pyromancerVsWiizard(final Pyromancer player) {
        player.wizzardVsPyromancer((Wizard) this);
    }

    public final void pyromancerVsRogue(final Pyromancer player) {
        player.rogueVsPyromancer((Rogue) this);
    }

    public final void pyromancerVsKnight(final Pyromancer player) {
        player.knightVsPyromancer((Knight) this);
    }

    public final void knightVsRogue(final Knight player) {
        player.rogueVsKnight((Rogue) this);
    }

    public final void knightVsWizzard(final Knight player) {
        player.wizzardVsKnight((Wizard) this);
    }

    public final void rogueVsWizzard(final Rogue player) {
        player.wizzardVsRogue((Wizard) this);
    }

    public final Integer getHowManyRounds() {
        return howManyRounds;
    }

    public final void setHowManyRounds(final Integer howManyRounds) {
        this.howManyRounds = howManyRounds;
    }

    public final Integer getExtraDmg() {
        return extraDmg;
    }

    public final void setExtraDmg(final Integer extraDmg) {
        this.extraDmg = extraDmg;
    }

    public final Character getLand() {
        return land;
    }

    public final void setLand(final Character land) {
        this.land = land;
    }

    public final Integer getLvl() {
        return lvl;
    }

    public final void setLvl(final Integer lvl) {
        this.lvl = lvl;
    }

    public final Integer getHp() {
        return hp;
    }

    public final void setHp(final Integer hp) {
        this.hp = hp;
    }

    public final Integer getXp() {
        return xp;
    }

    public final void setXp(final Integer xp) {
        this.xp = xp;
    }

    public final boolean isFought() {
        return fought;
    }

    public final void setFought(final boolean fought) {
        this.fought = fought;
    }

    public final boolean isAlive() {
        return isAlive;
    }

    public final void setAlive(final boolean alive) {
        isAlive = alive;
    }

    public final boolean getCanMove() {
        return canMove;
    }

    public final void setCanMove(final boolean canMove) {
        this.canMove = canMove;
    }

    public final ArrayList<String> getMoves() {
        return moves;
    }

    public final void setMoves(final String moves) {
        this.moves.add(moves);
    }

    public final Integer getMaxHP() {
        return maxHP;
    }

    public final String getTypeOfCharacter() {
        return typeOfCharacter;
    }

    public final Integer getPositionX() {
        return initialPositionX;
    }

    public final void setPositionX(final Integer initialPositionX) {
        this.initialPositionX = initialPositionX;
    }

    public final Integer getPositionY() {
        return initialPositionY;
    }

    public final void setPositionY(final Integer initialPositionY) {
        this.initialPositionY = initialPositionY;
    }

    public final void setAllMoves(final ArrayList<String> move) {
        this.moves = move;
    }
}
