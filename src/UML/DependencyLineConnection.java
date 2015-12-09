package UML;


import org.jhotdraw.figures.LineConnection;
import org.jhotdraw.figures.ArrowTip;
import org.jhotdraw.framework.Figure;
import java.awt.Graphics;


public class DependencyLineConnection extends LineConnection {

    static final long serialVersionUID = -2964321053621789632L;

    public DependencyLineConnection() {
        super();

        setStartDecoration(null);
        ArrowTip arrow = new ArrowTip(0.4, 12.0, 0.0);
        arrow.setBorderColor(java.awt.Color.black);
        setEndDecoration(arrow);
        setEndDecoration(arrow);
    }


    protected void handleConnect(Figure start, Figure end) {
        super.handleConnect(start, end);

        JModellerClass startClass = ((ClassFigure)start).getModellerClass();
        JModellerClass endClass = ((ClassFigure)end).getModellerClass();

        startClass.addDependency(endClass);
    }


    protected void handleDisconnect(Figure start, Figure end) {
        super.handleDisconnect(start, end);
        if ((start != null) && (end!= null)) {
            JModellerClass startClass = ((ClassFigure)start).getModellerClass();
            JModellerClass endClass = ((ClassFigure)end).getModellerClass();
            startClass.removeDependency(endClass);
        }
    }


    protected void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        int xDistance = x2 - x1;
        int yDistance = y2 - y1;
        double direction = Math.PI/2 - Math.atan2(xDistance, yDistance);
        
        double xAngle = Math.cos(direction);
        double yAngle = Math.sin(direction);
        int lineLength = (int)Math.sqrt(xDistance*xDistance + yDistance*yDistance);

        for (int i = 0; i + 5 < lineLength; i = i + 10) {
            int p1x = x1 + (int)(i * xAngle);
            int p1y = y1 + (int)(i * yAngle);
            int p2x = x1 + (int)((i + 5) * xAngle);
            int p2y = y1 + (int)((i + 5) * yAngle);
            g.drawLine(p1x, p1y, p2x, p2y);
        }
    }
}