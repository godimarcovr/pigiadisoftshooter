/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco;

import framework.Controls;
import framework.Entity;
import framework.EntityCensus;
import framework.Kb;
import framework.Map;
import framework.Ms;
import framework.Player;
import framework.Position;
import framework.TimerHandler;
import framework.Window;
import java.util.LinkedList;
import java.util.Timer;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;

/**
 *
 * @author Marco
 */
public class Game {

    public Map map;
    public long lastFrame;
    public int fps;
    public long lastFPS;
    public World world;
    public int velIt = 6, posIt = 2;
    public Entity e2;
    public Player pl;
    public boolean matrixMovement = true;

    public Game() {
    }

    public void start() {
       

        // init OpenGL here
        boolean success = Window.initialise(800, 600);
        Window.setMeterSpace(4 * 40, 3 * 40);
        Window.game2 = this;

        try {
            Display.create();
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }
        //menuInitialize();//Menu initialize
        initGL(); // init OpenGL
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        /**
         * ********************************************************************************************************
         */
        this.world =new World(new Vec2(0, 0));

        this.pl = new Player(5f, 5f, 0, 0);
        pl.debug = true;
        map = new Map();

        Controls.setKeys(new String[]{"W", "S", "A", "D"});

        /**
         * ********************************************************************************************************
         */
        pl.setMatrixCoordinates(map.getEntityCoordinates(pl));
        pl.setPosition(map.getMatrixPosition(pl).getX(), map.getMatrixPosition(pl).getY());

        while (!Display.isCloseRequested()) {
            int delta = getDelta();
            Ms.update(delta);
            TimerHandler.update(delta);
            updateFPS();
            update(delta);
            long startTime = System.currentTimeMillis();
            renderGL();
            System.out.print( System.currentTimeMillis() - startTime+"\n");
            Display.update();
            Display.sync(60); // cap fps to 60fps
        }



        Display.destroy();
    }

    private void initGL() {

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        Float[] bounds = Window.getBoundaries();
        GLU.gluOrtho2D(bounds[0], bounds[1], bounds[2], bounds[3]);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        //GL11.glEnable(GL11.GL_BLEND);
        //GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    private int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public void renderGL() {
       
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        map.draw();
        for (Entity entity : EntityCensus.ents) {
            entity.draw();
        }
        pl.draw();



    }

    public void setVisual() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluOrtho2D(pl.body.getPosition().x + Window.bounds[0], pl.body.getPosition().x + Window.bounds[1], pl.body.getPosition().y + Window.bounds[2], pl.body.getPosition().y + Window.bounds[3]);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
    int delay = 0;

    public void update(int delta) {
        String read = Kb.getChars();


        Vec2 mov = Controls.getPlayerMovement();

        if (!matrixMovement) {
            pl.dx = mov.x;
            pl.dy = mov.y;
            pl.update();

            pl.setSpeed();
            pl.setMatrixCoordinates(map.getEntityCoordinates(pl));

        } else if (delay > 2) {
            delay = 0;
            
            if (mov.x == 1) {
                pl.setC(1);
            } else if (mov.x == -1) {
                pl.setC(-1);
            }
            if (mov.y == 1) {
                pl.setR(1);
            } else if (mov.y == -1) {
                pl.setR(-1);
            }

        }
        delay += 1;

        world.step(delta, velIt, posIt);
        System.out.print(pl.c +" " + pl.r+"\n");
        


    }
}
