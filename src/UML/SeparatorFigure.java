package UML;


import java.awt.*;
import org.jhotdraw.figures.*;


public class SeparatorFigure extends LineFigure {

    
    public SeparatorFigure() {
        super();
    }

    public void draw(Graphics g) {
        g.setColor(getFrameColor());
        g.drawLine(startPoint().x, startPoint().y, endPoint().x - 1, startPoint().y);
    }
}