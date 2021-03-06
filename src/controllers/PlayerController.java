/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import values.DrawStringPoint;
import values.Global;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.scene.media.AudioClip;
import static values.Global.MIN_PICTURE_SIZE;
import values.Path;

/**
 *
 * @author user
 */
public class PlayerController {

    public static PlayerController playerController;
    private String name;
    private int hp;
    private int stage;
    private long score;
    private long money;
    private DrawStringPoint namePoint, killPoint, hpPoint, moneyPoint;
    private ImageController imageController;
    private BufferedImage hpImage[], hurtMask;
    private int moneyChange, hpChange, notEnough; // change color when value changes
    private DelayCounter delay, hurtDelay;
    private float ratio;
    private AudioClip audio;
    private AudioControllerForAudioClip audioController;
    private int kill;

    private PlayerController(int money, int hp) {
        this.name = "Player";
        this.score = 0;
        this.stage = 1;
        this.money = money;
        this.hp = hp;
        this.ratio = 1f;
        imageController = ImageController.genInstance();
        hpImage = new BufferedImage[2];
        hpImage[0] = imageController.tryGetImage("/Resources/Images/GameObject/BloodLineInner.png");
        hpImage[1] = imageController.tryGetImage("/Resources/Images/GameObject/BloodLineOutter.png");
        hurtMask = imageController.tryGetImage(Path.Image.Scene.HURT_MASK);
        delay = new DelayCounter(5);
        hurtDelay = new DelayCounter(2);
        audioController = AudioControllerForAudioClip.genInstance();
        audio = audioController.tryGetAudio(Path.Audios.Sounds.Effect.HURT);
        kill =0;
        
    }

    private PlayerController(String name, long score, int stage, long money) {
        this.name = name;
        imageController = ImageController.genInstance();
        hpImage = new BufferedImage[2];
        hpImage[0] = imageController.tryGetImage("/Resources/Images/GameObject/BloodLineInner.png");
        hpImage[1] = imageController.tryGetImage("/Resources/Images/GameObject/BloodLineOutter.png");
    }

    public static PlayerController genInstance() {
        if (playerController == null) {
            playerController = new PlayerController(300, 100);
        }
        return playerController;
    }

    public void initialize() {
        this.score = 0;
        this.stage = 1;
        this.money = 300; // test
        this.hp = 100;
        this.ratio = 1f;
    }

    public void reset() {
        this.money = 300; // test
        this.hp = 100;
        this.ratio = 1f;
    }

    public boolean saveData(short number) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream("savedata" + Short.toString(number) + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new PlayerController(name, score, stage, money));
            fos.close();
            oos.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public static boolean loadData(short number) {
        FileInputStream fis;
        try {
            fis = new FileInputStream("savedata" + Short.toString(number) + ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            playerController = (PlayerController) ois.readObject();
            fis.close();
            ois.close();
        } catch (FileNotFoundException ex) {
            return false;
        } catch (ClassNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    //setter

    public void setKill(int kill){
        this.kill = kill;
    }
    
    public void setMoney(long money) {
        this.money = money;
        moneyChange = 1;
    }
    
    public int getKill(){
        return kill;
    }

    public long getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public long getScore() {
        return score;
    }
    
    public void setNotEnough(int notEnough){
        this.notEnough = notEnough;
    }

    public boolean isEnough(long money) {
        if (this.money < money) {
            return false;
        }
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public void addScore(long score) {
        this.score += score;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void addStage(int stage) {
        this.stage += stage;
    }

    public void setHP(int hp) {
        if (this.hp <= 0) {
            this.hp = 0;
            return;
        }
        this.hp = hp;
        audio.play();
        hpChange = 1;
    }

    public int getHP() {
        return hp;
    }

    public void update() {
        if (namePoint != null) {
            if (!namePoint.getText().equals(name)) {
                namePoint.setText(name);
            }
        }
        if (killPoint != null) {
            killPoint.setText(Long.toString(kill));
            if (killPoint.getHeight() != Global.FRAME_HEIGHT) {
                killPoint.update(8 * Global.MIN_PICTURE_SIZE, 4 * Global.MIN_PICTURE_SIZE);
            }
        }
        if (hpPoint != null) {
            hpPoint.setText(Long.toString(hp));
            if (hpPoint.getHeight() != Global.FRAME_HEIGHT) {
                hpPoint.update(4 * Global.MIN_PICTURE_SIZE, 4 * Global.MIN_PICTURE_SIZE);
            }
        }
        if (moneyPoint != null) {
            moneyPoint.setText(Long.toString(money));
            if (moneyPoint.getHeight() != Global.FRAME_HEIGHT) {
                moneyPoint.update(2 * Global.MIN_PICTURE_SIZE, 2 * Global.MIN_PICTURE_SIZE);
            }
        }

        if (ratio >= 0) {
            ratio = ((float) hp / 100f);
        } else {
            ratio = 0;
        }

    }

    public void paint(Graphics g) {
        if (namePoint == null) {
            namePoint = new DrawStringPoint(24f * Global.MIN_PICTURE_SIZE, 0, g, Global.FONT_NAME, name, 2f * Global.MIN_PICTURE_SIZE, 2f * Global.MIN_PICTURE_SIZE);
        }
        if (killPoint == null) {
            killPoint = new DrawStringPoint(21f * Global.MIN_PICTURE_SIZE, 2f * Global.MIN_PICTURE_SIZE, g, Global.FONT_SCORE, Long.toString(score), 4f * Global.MIN_PICTURE_SIZE, 4f * Global.MIN_PICTURE_SIZE);
        }
        if (hpPoint == null) {
            hpPoint = new DrawStringPoint(28f * Global.MIN_PICTURE_SIZE, 0.5f * Global.MIN_PICTURE_SIZE, g, Global.FONT_HP, Long.toString(hp), 4f * Global.MIN_PICTURE_SIZE, 4f * Global.MIN_PICTURE_SIZE);
        }
        if (moneyPoint == null) {
            moneyPoint = new DrawStringPoint(24f * Global.MIN_PICTURE_SIZE, 4.5f * Global.MIN_PICTURE_SIZE, g, Global.FONT_MONEY, Long.toString(money), 4f * Global.MIN_PICTURE_SIZE, 2f * Global.MIN_PICTURE_SIZE);
        }
        //drawHp
        g.drawImage(hpImage[0], (int) (19f * Global.MIN_PICTURE_SIZE) + (int) ((1 - ratio) * (12f * Global.MIN_PICTURE_SIZE)), (int) (1.42 * Global.MIN_PICTURE_SIZE),
                (int) (ratio * (12f * Global.MIN_PICTURE_SIZE)), (int) (2f * Global.MIN_PICTURE_SIZE), null);

        //drawHP
        g.setColor(Color.white);
        if (hpChange == 1) {
            g.drawImage(hurtMask, 0, 0, null);
            if (hurtDelay.update()) {
                hpChange = 0;
            }
        }
        g.setFont(hpPoint.getFont());
        g.drawString(hpPoint.getText() + "%", (int) (hpPoint.getX() - MIN_PICTURE_SIZE * 0.7f), (int) (hpPoint.getY()));
        g.setColor(Color.white);
        if (stage != 1) {
            g.setColor(Color.black);
        }
        //drawName
        g.setFont(namePoint.getFont());
        g.drawString(namePoint.getText(), (int) (namePoint.getX() + MIN_PICTURE_SIZE * 0.5f), (int) (namePoint.getY()));

        //drawMoney
        if (moneyChange == 1) {
            g.setColor(Color.orange);
            if (delay.update()) {
                moneyChange = 0;
            }
        }
        if (notEnough == 1) {
            g.setColor(Color.red);
            if (delay.update()) {
                notEnough = 0;
            }
        }
        g.setFont(moneyPoint.getFont());
        g.drawString(moneyPoint.getText(), (int) (moneyPoint.getX() + 0.5 * MIN_PICTURE_SIZE), (int) (moneyPoint.getY()));
        g.drawString(" Coin", (int) (28.5f * MIN_PICTURE_SIZE), (int) (5.841f * MIN_PICTURE_SIZE));
        g.setColor(Color.white);
        if (stage != 1) {
            g.setColor(Color.black);
        }
        
        //drawScore
        g.setFont(killPoint.getFont());
        g.drawString(killPoint.getText(), (int) (killPoint.getX() + 0.5 * MIN_PICTURE_SIZE), (int) (killPoint.getY()));
        g.drawString(" Kill", (int) (29f * MIN_PICTURE_SIZE), (int) (4.341f * MIN_PICTURE_SIZE));
        //reset
        g.setColor(Color.black);
    }

}
