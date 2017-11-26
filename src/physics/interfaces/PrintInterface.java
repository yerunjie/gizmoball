package physics.interfaces;

import java.awt.*;

public interface PrintInterface {
    void print(Color color, Graphics g);

    default void drawing(Color color, Graphics g){
        print(color, g);
    }
}
