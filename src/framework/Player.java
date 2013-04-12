/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import framework.Window;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;

/**
 *
 * @author Marco
 */
public class Player extends Entity {

    public Player(float w, float h, float x, float y) {
        super(w, h, x, y);
    }

    public Player(Vec2[] vertex, float x, float y) {
        super(vertex, x, y);
    }

    @Override
    public void draw() {
        if (Window.game2.matrixMovement) {
            this.setPosition(Window.game2.map.getMatrixPosition(this).getX(), Window.game2.map.getMatrixPosition(this).getY());
            
        }
        Window.game2.setVisual();
        Color.white.bind();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.body.getPosition().x, this.body.getPosition().y, 0);
        GL11.glRotatef(360 - Ms.getAngle(), 0f, 0f, 1f);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        {
            for (int j = 0; j < pS.getVertexCount(); j++) {
                GL11.glVertex2f(pS.getVertex(j).x, pS.getVertex(j).y);
                //    System.out.println(pS.getVertex(j).x+" // "+ pS.getVertex(j).y);
            }
           
        }
        GL11.glEnd();
        GL11.glPopMatrix();

    }

    public void update() {
        this.body.setTransform(this.body.getPosition(), Ms.getAngle());
    }
    
    
}
