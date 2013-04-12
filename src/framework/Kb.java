/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import org.lwjgl.input.Keyboard;

/**
 *
 * @author matteo
 */
public class Kb {

    private static String old = "";
    private static String current = "";
    private static int kOld = 0;
    private static int kCurrent = 0;

    public static String getChars() {
        if (Keyboard.getNumKeyboardEvents() > 0) {
            old = "";
        }
        Keyboard.next();
        if (Keyboard.getEventKeyState()) {
            current = Keyboard.getKeyName(Keyboard.getEventKey());
            if (!(old.equals(current)) && current != null) {

                old = current;

                return (current);
            }
        }
        return "";
    }

    public static int getKeys() {
        if (Keyboard.getNumKeyboardEvents() > 0) {
            kOld = 0;
        }
        Keyboard.next();
        if (Keyboard.getEventKeyState()) {
            kCurrent = (Keyboard.getEventKey());
            if (kOld != kCurrent) {

                kOld = kCurrent;

                return (kCurrent);
            } else if (Keyboard.isRepeatEvent()) {

                return (kCurrent);
            }
        }
        return 0;
    }

    public static boolean isPressed(String key) {
        if (Keyboard.isKeyDown(Keyboard.getKeyIndex(key))) {
            return true;
        }
        return false;
    }
}
