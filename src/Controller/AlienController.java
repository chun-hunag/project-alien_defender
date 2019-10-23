/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GameObject.Alien;
import GameObject.Alien1;
import Value.Global;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author billy
 */
public class AlienController {

    private LinkedList<Alien> aliens;
    private DelayCounter moveDelay, genDelay;
    private int count;
    private ScoreController scoreController;
    private LinkedList<Point> route;
    private PlayerController playerController;
    private int stop; // stop generating aliens
    // NOT YET connecting to sceneController to the next scene to zerolize stop;

    public AlienController(LinkedList<Point> route) {
        aliens = new LinkedList<Alien>();
        moveDelay = new DelayCounter(1);
        genDelay = new DelayCounter(5);
        scoreController = new ScoreController();
        this.route = route;
        stop =0;
    }

    private void genAlien() {
        if ((int) (Math.random() * 100) > 10) {
            aliens.add(new Alien1(-25, 50));
            Alien.setRoute(route);
            count++;
        }
    }
    
    public LinkedList<Alien> getAliens(){
        return aliens;
    }

    public void update() {
        //Aliens update
        if (moveDelay.update()) {
            if (count < 50) { // first chapter > 50 aliens
                if (genDelay.update()) {
                    genAlien();
                }
            }else{// stop generating aliens
                stop =1;
            }
            for (int i = 0; i < aliens.size(); i++) {
                Alien a = aliens.get(i);
                a.update();
                if (a.getY() >= 24 * Global.MIN_PICTURE_SIZE) {
                    //player blood declination
                    aliens.remove(i);
                }
                if (a.isDead())// kill counts
                {
                    scoreController.scoreCount(a.getAlienNum());
                    System.out.println(scoreController);
                    aliens.remove(a);
                }
            }
        }
        if(aliens.size()<=0&&stop ==1){
            PlayerController.genInstance();
            playerController.addScore(scoreController.scoreConverter());
        }
    }

    public void paint(Graphics g) {
        //Alines paint
        for (int i = 0; i < aliens.size(); i++) {
            aliens.get(i).paint(g);
        }
    }

}