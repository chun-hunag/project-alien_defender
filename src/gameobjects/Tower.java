/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjects;

import controllers.AudioControllerForAudioClip;
import controllers.DelayCounter;
import static values.Global.*;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import javafx.scene.media.AudioClip;
import values.Path;

/**
 *
 * @author user
 */
public abstract class Tower extends ActiveObject {

    private float attack;
    private LinkedList<Point> range;
    private LinkedList<Bullet> bullets;
    private int towerNum;
    private int towerRange;
    private DelayCounter delay;
    private int upgradeStage;
    private int upgradeNow;
    private float cost, upgradeCost;
    private AudioClip audio;
    private AudioControllerForAudioClip audioController;

    public Tower(float x, float y, float width, float height, float attack, float speed) {
        super(x, y, width, height, speed);
        bullets = new LinkedList<Bullet>();
        delay = new DelayCounter(10);
        upgradeStage = 0;
        setAttack(attack);
        audioController = AudioControllerForAudioClip.genInstance();
        audio = audioController.tryGetAudio(Path.Audios.Sounds.Effect.UPGRADE);
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public void setUpgradeCost(float upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public float getUpgradeCost() {
        return upgradeCost;
    }

    public LinkedList getRange() {
        return range;
    }

    public void setRange(LinkedList<Point> range) {
        this.range = range;
    }

    public int getUpgradeStage() {
        return upgradeStage;
    }

    public int getUpgradeNow() {
        return upgradeNow;
    }

    public void setUpgradeNow(int upgradeNow) {
        this.upgradeNow = upgradeNow;
    }

    public void detection(LinkedList<Alien> aliens) {
        for (Point range : range) {
            for (int i = 0; (aliens != null) & i < aliens.size(); i++) {
                Alien alien = aliens.get(i);
                if (alien != null) {
                    if (alien.getX() + SIZE_GRID - DEVIATION >= range.getX()
                            && alien.getX() + DEVIATION <= range.getX() + SIZE_GRID
                            && alien.getY() + SIZE_GRID - DEVIATION >= range.getY()
                            && alien.getY() + DEVIATION <= range.getY() + SIZE_GRID) {
                        changeDirection(alien);
                        attack(alien);
                        return;
                    }
                }
            }
        }
    }

    public float getAttack() {
        return attack;
    }

    public void setAttack(float attack) {
        this.attack = attack;
    }

    public void changeDirection(Alien alien) {
        double h = (alien.getY() - this.getY());
        double w = (alien.getX() - this.getX());
        if (h == 0) {
            if (w > 0) {
                super.setDirection(90);
            } else {
                super.setDirection(270);
            }
        }
        if (w == 0) {
            if (h > 0) {
                super.setDirection(180);
            } else {
                super.setDirection(0);
            }
        }
        if (w != 0 && h != 0) {
            if (w > 0 && h < 0) {
                super.setDirection(90 - (int) (Math.abs(Math.atan((h)
                        / (w)) * 180 / Math.PI)));
            } else if (w > 0 && h > 0) {
                super.setDirection(90 + (int) (Math.abs(Math.atan((h)
                        / (w)) * 180 / Math.PI)));
            } else if (w < 0 && h < 0) {
                super.setDirection(270 + (int) (Math.abs(Math.atan((h)
                        / (w)) * 180 / Math.PI)));
            } else {
                super.setDirection(270 - (int) (Math.abs(Math.atan((h)
                        / (w)) * 180 / Math.PI)));
            }
        }
    }

    public void attack(Alien alien) {
        if (delay.update()) {
            alien.isAttacked(this);
            bullets.add(new Bullet(super.getX(), super.getY(), alien, this, super.getDirection(), super.getSpeed()));
        }
    }

    public void setTowerNum(int towerNum) {
        this.towerNum = towerNum;
    }

    public int getTowerNum() {
        return towerNum;
    }

    public void setTowerRange(int towerRange) {
        this.towerRange = towerRange;
    }

    public int getTowerRange() {
        return towerRange;
    }

    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    // call this function to upgrade the tower
    public boolean upgrade() {
        if (upgradeStage >= 2) {
            return false;
        }
        audio.play();
        upgradeNow++;
        upgradeStage++;
        setAttack(getAttack() * 1.5f);
        setUpgradeCost(getCost() * 0.5f);
        return true;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void paint(Graphics g);
}
