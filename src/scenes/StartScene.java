/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;


import controllers.AudioController;
import controllers.BackgroundController;
import controllers.ImageController;
import controllers.SceneController;
import gameobjects.Button;
import gameobjects.Button.ButtonListener;
import values.Path;
import controllers.CommandSolver.MouseCommandListener;
import controllers.CommandSolver.MouseState;
import values.Global;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.sound.sampled.Clip;

    

/**
 *
 * @author user
 */
public class StartScene extends Scene{

    private MouseCommandListener mouseCommandListener;
    private BackgroundController backgroundController;
    private ImageController imageController;
    private AudioController audioController;
    private Clip audio;
    private Button buttonStart;
    
    
    public StartScene(SceneController sceneController){
        super(sceneController);
        backgroundController = new BackgroundController(-1);
        imageController = ImageController.genInstance();
        audioController = AudioController.genInstance();
        audio = audioController.tryGetAudio(Path.Audios.Musics.TEST);
        audio.loop(Clip.LOOP_CONTINUOUSLY);
        
        mouseCommandListener = new MouseCommandListener(){
            @Override
            public void mouseTrig(MouseEvent e, MouseState state, long trigTime) {
                
                if(state == MouseState.RELEASED || state == MouseState.CLICKED){
                    if(buttonStart.isRange(e.getX(), e.getY())){
                        buttonStart.click(e.getX(), e.getY());
                    }
                }else if(state == MouseState.MOVED){
                    if(buttonStart.isRange(e.getX(), e.getY())){
                        buttonStart.hover(e.getX(), e.getY());
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
        backgroundController.update();
        if(buttonStart.getWidth() !=  7 * Global.MIN_PICTURE_SIZE){
            genButton();
        }
        buttonStart.update();
    }

    @Override
    public void sceneEnd() {
        buttonStart = null;
        imageController.clearImage();
//        audio.close();
        audioController.clearAudio();

    }

    @Override
    public void paint(Graphics g) {
        backgroundController.paint(g);
        buttonStart.paint(g);
    }   

    public MouseCommandListener getMouseCommandListener(){
        return mouseCommandListener;
    }
    
    public void genButton(){
        
        buttonStart = new Button(3, 40, (int)(Global.FRAME_WIDTH), (int)(Global.FRAME_HEIGHT + 16 * Global.MIN_PICTURE_SIZE),
        "Click to Start");
        
        buttonStart.setButtonListener(new ButtonListener(){
            @Override
            public void onClick(int x, int y) {
                 sceneController.changeScene(new MenuScene(sceneController, audio));
            }

            @Override
            public void hover(int x, int y) {
            }
        }
        );
    }
}
