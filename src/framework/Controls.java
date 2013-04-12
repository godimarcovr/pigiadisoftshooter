/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import org.jbox2d.common.Vec2;

/**
 *
 * @author Marco
 */
public class Controls {

    public static String[] keys;
    public static int pl_forward = 0, pl_back = 1, pl_left = 2, pl_right = 3;

    public static Vec2 getPlayerMovement() {
        Vec2 ret = new Vec2(0, 0);

        if (Kb.isPressed(Controls.keys[pl_forward])) {
            ret.y += 1;
        }

        if (Kb.isPressed(Controls.keys[pl_back])) {
            ret.y -= 1;
        }

        if (Kb.isPressed(Controls.keys[pl_left])) {
            ret.x -= 1;
        }

        if (Kb.isPressed(Controls.keys[pl_right])) {
            ret.x += 1;
        }

        return ret;
    }

    public static void setKeys(String[] k){
        keys=k;
    }
}
