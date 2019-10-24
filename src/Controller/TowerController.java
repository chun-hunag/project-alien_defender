/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GameObject.Alien;
import GameObject.Tower;
import static Value.Global.*;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author billy
 */
public class TowerController {

    private LinkedList<Tower> towers;
    private LinkedList<Alien> aliens;
    private LinkedList<Point> range;

    public TowerController(AlienController alienController) {
        towers = new LinkedList<Tower>();
        this.aliens = alienController.getAliens();
        
    }

    public LinkedList<Tower> getTowers(){
        return towers;
    }
    
        public void genRange(Point point, int towerNum) {
        range = new LinkedList<Point>();
        int towerRange = towerNum+1;
        for (int i = -towerRange; i < towerRange; i++) {
            for (int j = -towerRange; j < towerRange; j++) {
                if (Math.abs(i) + Math.abs(j) <= towerRange) {
                    range.add(new Point((int)(point.getX() + i), (int)(point.getY() + j)));
                }
                j += (SIZE_GRID - 1);
            }
            i += (SIZE_GRID - 1);
        }
        for (int i = towerRange; i >= 0; i--) {
            for (int j = towerRange; j >= 0; j--) {
                if (Math.abs(i + j) <= towerRange) {
                    range.add(new Point((int)(point.getX() + i), (int)(point.getY() + j)));
                }
                j -= (SIZE_GRID - 1);
            }
            i -= (SIZE_GRID - 1);
        }
    }
    
    public void update() {
        //Tower update
        if (aliens.size() != 0) {
            for (int i = 0; i < towers.size(); i++) {
                    towers.get(i).detection(aliens);
                    towers.get(i).update();
            }
        } 
    }

    public void paint(Graphics g) {
        //Tower paint
        for (int i = 0; i < towers.size(); i++) {
            towers.get(i).paint(g);
        }
    }
}
