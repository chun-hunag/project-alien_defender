/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;

/**
 *
 * @author billy
 */
public class ScoreController {

    private ArrayList<Integer> kills;
    public static ScoreController scoreController;
    private int totalScore;

    public ScoreController() {
        kills = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            kills.add(0);
        }
    }

    public void scoreCount(int alienNum) {
        switch (alienNum) {
            case 1:
                kills.set(0, kills.get(0) + 1);
                break;
            case 2:
                kills.set(1, kills.get(1) + 1);
                break;
            case 3:
                kills.set(2, kills.get(2) + 1);
                break;
            case 4:
                kills.set(3, kills.get(3) + 1);
                break;
            case 5:
                kills.set(4, kills.get(4) + 1);
                break;
        }
    }

    public int scoreConverter() {
        totalScore += kills.get(0) * 1
                + kills.get(1) * 2
                + kills.get(2) * 3
                + kills.get(3) * 4
                + kills.get(4) * 5;
        return totalScore;
    }

    public int countPerKill(int alienNum) {
        switch (alienNum) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
        }
        return -1;
    }

    public ArrayList<Integer> getKills() {
        return kills;
    }

    public String toString() {
        String str = "";
        int count = 1;
        for (int kill : kills) {
            str += "Alien *" + kill + "\n";
            count++;
        }
        return str;
    }
}
