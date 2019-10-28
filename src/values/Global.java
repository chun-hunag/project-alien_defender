/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author user
 */
public class Global {

    //Update 

    public static final int UPDATE_PER_SEC = 60;
    public static final int MILLISEC_PER_UPDATE = 1000 / UPDATE_PER_SEC;

    //Frame Limit
    public static final int FRAME_LIMIT = 120;
    public static final int LIMIT_DELAY_TIME = 1000 / FRAME_LIMIT;

    //SPEED
    public static final int SPEED = 120 / UPDATE_PER_SEC;
    public static final int ANIMATE_DELAY_PER_SEC = UPDATE_PER_SEC / 15;

    //Delay
    public static final int BUTTON_UPDATE_DELAY = 3;


    //Grid
    public static final float SIZE_GRID = 32f;
    
    ///Objects
    public static final float SIZE_OBJECT = 65f;
    public static final float DEVIATION = SIZE_GRID/11f; // deviation for grid judgement
    public static final float SIZE_BLOODLINE = SIZE_OBJECT/15f;
    
    //Tower
    public static final float TOWER0_ATKRANGE = 1 * SIZE_GRID;
    public static final float TOWER1_ATKRANGE = 2 * SIZE_GRID;
    public static final float TOWER2_ATKRANGE = 3 * SIZE_GRID;
    public static final float TOWER3_ATKRANGE = 4 * SIZE_GRID;
    public static final float TOWER4_ATKRANGE = 5 * SIZE_GRID;
    public static final int TOWER0_COST = 20;
    public static final int TOWER1_COST = 30;
    public static final int TOWER2_COST = 40;
    public static final int TOWER3_COST = 50;
    public static final int TOWER4_COST = 60;

    //Cailbaration
    public static float MIN_PICTURE_SIZE = 32f;
    public static final float STANDAR_MIN_SIZE = 32f;
    public static float FRAME_WIDTH = 1024f;
    public static float FRAME_HEIGHT = 768f;

    //direction
    public static final int UP = 0;
    public static final int DOWN = 180;
    public static final int LEFT = 270;
    public static final int RIGHT = 90;
    
    //Font
    public static final Font FONT_00 = new Font(Font.DIALOG, Font.PLAIN, 100);
    public static final Font FONT_01 = new Font(Font.DIALOG, Font.PLAIN, 120);
    public static final Font FONT_BUTTON = new Font(Font.DIALOG, Font.PLAIN, 40);
    public static final Font FONT_INPUT = new Font(Font.DIALOG, Font.PLAIN, 30);
    public static final Font FONT_NAME = new Font(Font.DIALOG, Font.PLAIN, 30);
    public static final Font FONT_HP = new Font(Font.DIALOG, Font.PLAIN, 30);
    public static final Font FONT_SCORE = new Font(Font.DIALOG, Font.PLAIN, 30);
    public static final Font FONT_MONEY = new Font(Font.DIALOG, Font.PLAIN, 30);
    public static final Font FONT_TEXT = new Font(Font.DIALOG, Font.PLAIN, 30);
   
    public static final Color DEFAULT_FONT_COLOR = Color.BLACK;
    
    //Key
    public static final int KEY_ENTER = 6666;
    public static final int KEY_BACK_SPACE = 6667;

}
