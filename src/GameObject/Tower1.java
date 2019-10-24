/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObject;

import Controller.DelayCounter;
import Value.Global;
import static Value.Global.*;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author billy
 */
public class Tower1 extends Tower {

    private TowerHelper tHelper;
    private LinkedList<Bullet> bullets;
    private DelayCounter delay;

    public Tower1(float x, float y) {
        super(x, y, SIZE_GRID, SIZE_GRID, 10, 2 * Global.SPEED); // x, y, width, height, attack, speed
        super.setTowerNum(0);
        tHelper = new TowerHelper(super.getTowerNum());
        super.setTowerRange((int) checkTowerNum(super.getTowerNum()));
        genRange();
        bullets = super.getBullets();
        delay = new DelayCounter(1);
    }

    public float checkTowerNum(int towerNum) {
        switch (towerNum) {
            case 0:
                return TOWER0_ATKRANGE;
            case 1:
                return TOWER1_ATKRANGE;
            case 2:
                return TOWER2_ATKRANGE;
            case 3:
                return TOWER3_ATKRANGE;
            case 4:
                return TOWER4_ATKRANGE;
        }
        return -1;
    }

    public void genRange() {
        super.setRange(new LinkedList<Point>());
        int towerRange = super.getTowerRange();
        for (int i = -towerRange; i < towerRange; i++) {
            for (int j = -towerRange; j < towerRange; j++) {
                if (Math.abs(i) + Math.abs(j) <= towerRange) {
                    super.getRange().add(new Point((int) (super.getX() + i), (int) (super.getY() + j)));
                }
                j += (SIZE_GRID - 1);
            }
            i += (SIZE_GRID - 1);
        }
        for (int i = towerRange; i >= 0; i--) {
            for (int j = towerRange; j >= 0; j--) {
                if (Math.abs(i + j) <= towerRange) {
                    super.getRange().add(new Point((int) (super.getX() + i), (int) (super.getY() + j)));
                }
                j -= (SIZE_GRID - 1);
            }
            i -= (SIZE_GRID - 1);
        }
    }

    @Override
    public void upgrade() {
        switch (super.getUpgradeStage()) {
            case 0:
                break;
            case 1:
                super.setAttack(super.getAttack() * 1.3f);
                super.setUpgrade(0);
                break;
        }
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < super.getBullets().size(); i++) {
            bullets.get(i).paint(g);
            if (bullets.get(i).isReached()) {
                if (delay.update()) {
                    bullets.remove(i);
                }
            }
        }
        tHelper.paint(g, super.getX(), getY(), SIZE_GRID, SIZE_GRID, super.getDirection(), super.getUpgradeStage());
    }
}
