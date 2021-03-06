/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.AudioControllerForAudioClip;
import controllers.CommandSolver;
import controllers.ImageController;
import controllers.PlayerController;
import controllers.TowerController;
import gameobjects.Button;
import gameobjects.Button.ButtonListener;
import gameobjects.Tower;
import values.Global;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javafx.scene.media.AudioClip;
import values.DrawStringPoint;
import static values.Global.*;
import values.Path;

/**
 *
 * @author user
 */
public class TowerInformationWindow extends TowerPopUpWindow {

    private ImageController imageController;
    private BufferedImage image, towerImage;
    private PlayerController playerController;
    private Button upgradeButton;
    private LinkedList<Point> towerRange;
    private Tower tower;
    private int upgradeStage;
    private boolean isEnd;
    private String atkInfo, costInfo;
    private int hoveringTower;
    private DrawStringPoint infoString;
    private AudioClip noMoneyAudio;
    private AudioControllerForAudioClip audioController;
    private int notEnough;
    private BufferedImage grayImg;
    BufferedImage img;

    public TowerInformationWindow(float x, float y, float width, float height, Tower tower, PlayerController playerController) {
        super(6.5f * Global.MIN_PICTURE_SIZE, Global.MIN_PICTURE_SIZE, width, height, null);
        if (y < 200) {
            super.setY(300);
        } else if (y < 400) {
            super.setY(500);
        } else if (y < 600) {
            super.setY(300);
        } else if (y < 800) {
            super.setY(500);
        }
        this.tower = tower;
        hoveringTower = tower.getTowerNum();
        this.upgradeStage = tower.getUpgradeStage();
        imageController = ImageController.genInstance();
        this.playerController = playerController;
        towerRange = new LinkedList();
        getButton(super.getX() - 2 * SIZE_GRID, super.getY());
        isEnd = false;
        audioController = AudioControllerForAudioClip.genInstance();
        noMoneyAudio = audioController.tryGetAudio(Path.Audios.Sounds.Effect.NOMONEY);
        grayImg = imageController.tryGetImage("/Resources/Images/Label/grayUpgrade.png");
        img = imageController.tryGetImage("/Resources/Images/Label/upgrade.png");
        super.mouseCommandListener = new CommandSolver.MouseCommandListener() {
            @Override
            // 關閉視窗
            public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
                if (state == CommandSolver.MouseState.RELEASED || state == CommandSolver.MouseState.CLICKED) {
                    int x = e.getX();
                    int y = e.getY();
                    //check out of window's bound
                    if (x < 6.5f * Global.MIN_PICTURE_SIZE || x > (6.5f * Global.MIN_PICTURE_SIZE + width) || y < Global.MIN_PICTURE_SIZE || y > (Global.MIN_PICTURE_SIZE + height)) {
                        isEnd = true;
                    }

                        if(upgradeButton != null){
                            if (upgradeButton.isRange(x, y)) {
                                upgradeButton.click(x, y);
                            }
                        }
                }
            }
        };
    }

    @Override
    public int getNotEnough() {
        return notEnough;
    }

    @Override
    public boolean isEnd() {
        return isEnd;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void update() {
        if (playerController.isEnough(TowerController.upgradeCostArr[tower.getTowerNum()])  && upgradeStage < 2) {
            if(upgradeButton != null){
               upgradeButton.setImage(img); 
            }
            
        } else {
            if(upgradeButton != null){
               upgradeButton.setImage(grayImg);
            }
        }
        if(upgradeButton != null){
              upgradeButton.update();
    
        }
        if (upgradeStage == 2) {
            switch (hoveringTower) {

                case 0:
                    atkInfo = "Attack: " + (int) (TOWER0_ATK * Math.pow(1.5, upgradeStage));
                    costInfo = "Max Level";
                    return;
                case 1:
                    atkInfo = "Attack: " + (int) (TOWER1_ATK * Math.pow(1.5, upgradeStage));
                    costInfo = "Max Level";
                    return;
                case 2:
                    atkInfo = "Attack: " + (int) (TOWER2_ATK * Math.pow(1.5, upgradeStage));
                    costInfo = "Max Level";
                    return;
                case 3:
                    atkInfo = "Attack: " + (int) (TOWER3_ATK * Math.pow(1.5, upgradeStage));
                    costInfo = "Max Level";
                    return;
                case 4:
                    atkInfo = "Attack: " + (int) (TOWER4_ATK * Math.pow(1.5, upgradeStage));
                    costInfo = "Max Level";
                    return;
            }
        }
        switch (hoveringTower) {

            case 0:
                atkInfo = "Attack: " + (int) (TOWER0_ATK * Math.pow(1.5, upgradeStage)) + "→" + (int) (TOWER0_ATK * Math.pow(1.5, upgradeStage + 1));
                costInfo = "Upgrade $: " + (int) (TOWER0_COST / 2);
                break;
            case 1:
                atkInfo = "Attack: " + (int) (TOWER1_ATK * Math.pow(1.5, upgradeStage)) + "→" + (int) (TOWER1_ATK * Math.pow(1.5, upgradeStage + 1));
                costInfo = "Upgrade $: " + (int) (TOWER1_COST / 2);
                break;
            case 2:
                atkInfo = "Attack: " + (int) (TOWER2_ATK * Math.pow(1.5, upgradeStage)) + "→" + (int) (TOWER2_ATK * Math.pow(1.5, upgradeStage + 1));
                costInfo = "Upgrade $: " + (int) (TOWER2_COST / 2);
                break;
            case 3:
                atkInfo = "Attack: " + (int) (TOWER3_ATK * Math.pow(1.5, upgradeStage)) + "→" + (int) (TOWER3_ATK * Math.pow(1.5, upgradeStage + 1));
                costInfo = "Upgrade $: " + (int) (TOWER3_COST / 2);
                break;
            case 4:
                atkInfo = "Attack: " + (int) (TOWER4_ATK * Math.pow(1.5, upgradeStage)) + "→" + (int) (TOWER4_ATK * Math.pow(1.5, upgradeStage + 1));
                costInfo = "Upgrade $: " + (int) (TOWER4_COST / 2);
                break;
        }
    }

    @Override
    public void paint(Graphics g
    ) {
        //draw Tower Area
        towerRange = tower.getRange();
        Graphics2D k = (Graphics2D) g;
        k.setStroke(new BasicStroke(2f));
        k.setColor(Color.ORANGE);
        for (int i = 0; i < towerRange.size(); i++) {
            Point p = towerRange.get(i);
            k.drawRect((int) p.getX(), (int) p.getY(), (int) Global.MIN_PICTURE_SIZE, (int) Global.MIN_PICTURE_SIZE);
        }
        k.setColor(Color.BLACK);

        //draw PopUpWindow
        image = imageController.tryGetImage("/Resources/Images/Label/Tower_info_Label.png");
        towerImage = imageController.tryGetImage("/Resources/Images/GameObject/Tower2.png");
        int upgrade = upgradeStage + 1;
        if (upgradeStage == 2) {
            upgrade = 2;
        }
        // info window
        g.drawImage(image, (int) super.getX() + 180, (int) super.getY(), (int) (SIZE_GRID * 12), (int) SIZE_GRID * 3, null);
        g.setColor(Color.white);
        g.setFont(FONT_INFOWINDOW);
        // strings
        g.drawString(atkInfo, (int) (super.getX() + SIZE_GRID * 8.8), (int) (super.getY() + SIZE_GRID * 1.2));
        g.drawString(costInfo, (int) (super.getX() + SIZE_GRID * 8.8), (int) (super.getY() + SIZE_GRID * 2.2));
        // tower images
        g.drawImage(towerImage,
                (int) (super.getX() + SIZE_GRID * 6.2), (int) (super.getY() + SIZE_GRID * 0.5),
                (int) (super.getX() + SIZE_GRID * 8.2), (int) (super.getY() + SIZE_GRID * 2.5),
                (int) (hoveringTower * SIZE_OBJECT), (int) SIZE_OBJECT * (upgrade),
                (int) (hoveringTower * SIZE_OBJECT + SIZE_OBJECT), (int) SIZE_OBJECT * (upgrade + 1),
                null);

        if(upgradeButton != null){
            upgradeButton.paint(g);
        }
    }

    private void getButton(float x0, float y0) {

       upgradeButton = new Button(x0 - 2 * MIN_PICTURE_SIZE + 19 * MIN_PICTURE_SIZE, y0 + (int) (0.5 * MIN_PICTURE_SIZE), 2 * MIN_PICTURE_SIZE, 2 * MIN_PICTURE_SIZE, img);

        ButtonListener buttonListener2 = new Button.ButtonListener() {

            //
            @Override
            public void onClick(int x, int y) {
                if (playerController.isEnough(TowerController.upgradeCostArr[tower.getTowerNum()])) {
                    notEnough = 0;
                    if (tower.upgrade()) {
                        playerController.setMoney(playerController.getMoney() - TowerController.upgradeCostArr[tower.getTowerNum()]);
                    }

                } else {
                    noMoneyAudio.play();
                    notEnough = 1;
                }

            }

            @Override
            public void hover(int x, int y) {

            }

        };
        if (upgradeStage != 2) {
            upgradeButton.setButtonListener(buttonListener2);
        }
    }

}
