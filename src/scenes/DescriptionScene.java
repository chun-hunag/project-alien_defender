/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import controllers.CommandSolver;
import controllers.CommandSolver.MouseState;
import controllers.ImageController;
import controllers.SceneController;
import gameobjects.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;
import values.DrawStringPoint;
import values.Global;
import static values.Global.*;
import values.Path;

/**
 *
 * @author user
 */
public class DescriptionScene extends Scene {

    private CommandSolver.MouseCommandListener mouseCommandListener;
    private ImageController imageController;
    private BufferedImage image, image2, description1, description2, description3, description4;
    private Button backButton, continueButton;
    private Clip audio;
    private int sX;
    private int page;
    private String pageString;
    private DrawStringPoint pagePoint;

    public DescriptionScene(SceneController sceneController, Clip audio) {
        super(sceneController);
        imageController = ImageController.genInstance();
        image = imageController.tryGetImage(Path.Image.Scene.SPACE1);
        image2 = imageController.tryGetImage(Path.Image.Scene.SPACE2);
        description1 = imageController.tryGetImage(Path.Image.Description.DESCRIPTION1);
        description2 = imageController.tryGetImage(Path.Image.Description.DESCRIPTION2);
        description3 = imageController.tryGetImage(Path.Image.Description.DESCRIPTION3);
        description4 = imageController.tryGetImage(Path.Image.Description.DESCRIPTION4);
        page = 1;
        pageString = Integer.toString(page) + " / " + "4";
        this.audio = audio;
        mouseCommandListener = new CommandSolver.MouseCommandListener() {
            @Override
            public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
                if (state == MouseState.RELEASED || state == MouseState.CLICKED) {
                    int x = e.getX();
                    int y = e.getY();
                    if (backButton != null) {
                        if (backButton.isRange(x, y)) {
                            backButton.click(x, y);
                        }
                    }
                    if (continueButton != null) {
                        if (continueButton.isRange(x, y)) {
                            continueButton.click(x, y);
                        }
                    }
                }
            }

        };
    }

    @Override
    public void sceneBegin() {
        genButton();
    }

    @Override
    public void sceneUpdate() {
        if(pagePoint != null){
            pageString = Integer.toString(page) + " / " + "4";
            pagePoint.setText(pageString);
            pagePoint.update(Global.FRAME_WIDTH, Global.FRAME_WIDTH);
        }
        backButton.update();
        continueButton.update();
        if (sX == -(int) (2 * 32 * MIN_PICTURE_SIZE)) {
            sX = 0;
        }
        sX -= 1;
    }

    @Override
    public void sceneEnd() {
        backButton = null;
        continueButton = null;
        imageController.clearImage();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image,
                sX, 0,
                (int) (32 * MIN_PICTURE_SIZE), (int) (24 * MIN_PICTURE_SIZE), null);
        g.drawImage(image2,
                sX + (int) (32 * MIN_PICTURE_SIZE), 0,
                (int) (32 * MIN_PICTURE_SIZE), (int) (24 * MIN_PICTURE_SIZE), null);
        g.drawImage(image,
                sX + (int) (2 * 32 * MIN_PICTURE_SIZE), 0,
                (int) (32 * MIN_PICTURE_SIZE), (int) (24 * MIN_PICTURE_SIZE), null);
        backButton.paint(g);
        g.setColor(Color.orange);

        switch (page) {
            case 1:
                g.drawImage(description1, 120, 80, 895, 650, 0, 0, 895, 674, null);
                break;
            case 2:
                g.drawImage(description2, 120, 80, 895, 650, 0, 0, 895, 674, null);
                break;
            case 3:
                g.drawImage(description3, 120, 80, 895, 650, 0, 0, 895, 674, null);
                break;
            case 4:
                g.drawImage(description4, 120, 80, 895, 650, 0, 0, 895, 674, null);
                break;
                
        }
        g.setFont(FONT_TEXT);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Click to Continue", 400, 710);
        
        if(pagePoint == null){
            pagePoint = new DrawStringPoint(0, 0, g, Global.FONT_SCORE, pageString, Global.FRAME_WIDTH, Global.FRAME_HEIGHT);
        }
        g.drawString(pagePoint.getText(), (int)(pagePoint.getX()- 11 * Global.MIN_PICTURE_SIZE), (int)(pagePoint.getY() - 14 * Global.MIN_PICTURE_SIZE));
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseCommandListener() {
        return mouseCommandListener;
    }

    private void genButton() {
        backButton = new Button(27 * MIN_PICTURE_SIZE, 21f * MIN_PICTURE_SIZE, 4f * MIN_PICTURE_SIZE, 2f * MIN_PICTURE_SIZE,
                imageController.tryGetImage(Path.Image.Button.BACK_BUTTON));
        backButton.setText("Back");
        backButton.setButtonListener(new Button.ButtonListener() {
            @Override
            public void onClick(int x, int y) {
                sceneController.changeScene(new MenuScene(sceneController, audio));

            }

            @Override
            public void hover(int x, int y) {
            }

        });

        continueButton = new Button(0, 0, (int) (Global.FRAME_WIDTH), (int) (Global.FRAME_HEIGHT + 14 * Global.MIN_PICTURE_SIZE), "Click to Retry");

        continueButton.setButtonListener(new Button.ButtonListener() {
            @Override
            public void onClick(int x, int y) {
                page++;
                if(page > 4){
                    page = 1;
                }
            }

            @Override
            public void hover(int x, int y) {
            }
        }
        );
    }
}
