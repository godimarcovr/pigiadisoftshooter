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
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;

/**
 *
 * @author Marco
 */
public class Entity {

    public boolean debug;
    public PolygonShape pS;
    public Body body;
    public Fixture fix;
    public float dx, dy;
    public float speedMult = 0.04f;
    public float w, h;
    public int c, r;
    public Color col;

    public Entity(float w, float h, float x, float y) {
        col = Color.white;
        this.w = w;
        this.h = h;
        pS = new PolygonShape();
        pS.setAsBox(w, h);

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position = new Vec2(x, y);

        FixtureDef fd = new FixtureDef();
        fd.shape = pS;
        fd.friction = 1;

        body = Window.game2.world.createBody(bd);
        this.fix = body.createFixture(fd);


        EntityCensus.addEntity(this);
    }

    public Entity(Vec2[] vertex, float x, float y) {
        col = Color.white;
        pS = new PolygonShape();
        pS.set(vertex, vertex.length);

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position = new Vec2(x, y);

        FixtureDef fd = new FixtureDef();
        fd.shape = pS;
        fd.friction = 1;

        body = Window.game2.world.createBody(bd);
        body.createFixture(fd);


        EntityCensus.addEntity(this);
    }

    public void setSpeed() {
        this.body.setLinearVelocity(new Vec2(dx * this.speedMult, dy * this.speedMult));
    }

    public void setSpeedMult(float speedMult) {
        this.speedMult = speedMult;
    }

    public void draw() {

        col.bind();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.body.getPosition().x, this.body.getPosition().y, 0);
        GL11.glBegin(GL11.GL_QUADS);
        {
            for (int j = 0; j < pS.getVertexCount(); j++) {
                GL11.glVertex2f(pS.getVertex(j).x, pS.getVertex(j).y);
            }
        }
        GL11.glEnd();
        GL11.glPopMatrix();


    }

    public void setPosition(float x, float y) {
        body.setTransform(new Vec2(x, y), 0);
    }

    public void setPosition(float x, float y, int c, int r) {
        this.c = c;
        this.r = r;
        body.setTransform(new Vec2(x, y), 0);
    }

    public void setMatrixCoordinates(Position p) {
        this.c = (int) p.x;
        this.r = (int) p.y;
    }

    public void setC(int c) {
        if ((Window.game2.map.getType(this.c + c, r)) != 1) {
            this.c += c;
        }
    }

    public void setR(int r) {
        if ((Window.game2.map.getType(c, this.r +r)) != 1) {
            this.r += r;
        }
    }
}
