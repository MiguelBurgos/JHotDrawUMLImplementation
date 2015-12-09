package UML;



import org.jhotdraw.framework.*;
import org.jhotdraw.figures.LineConnection;
import org.jhotdraw.figures.ArrowTip;
import java.awt.Color;


public class InheritanceLineConnection extends LineConnection {
    
    
    public InheritanceLineConnection() {
        setStartDecoration(null);
        ArrowTip arrow = new ArrowTip(0.35, 20.0 , 20.0);
        arrow.setFillColor(Color.white);
        arrow.setBorderColor(Color.black);
        setEndDecoration(arrow);
    }

    protected void handleConnect(Figure start, Figure end) {
        super.handleConnect(start, end);

        JModellerClass startClass = ((ClassFigure)start).getModellerClass();
        JModellerClass endClass = ((ClassFigure)end).getModellerClass();

        startClass.addSuperclass(endClass);
    }


    protected void handleDisconnect(Figure start, Figure end) {
        super.handleDisconnect(start, end);
        if ((start != null) && (end!= null)) {
            JModellerClass startClass = ((ClassFigure)start).getModellerClass();
            JModellerClass endClass = ((ClassFigure)end).getModellerClass();
            startClass.removeSuperclass(endClass);
        }
    }


    public boolean canConnect(Figure start, Figure end) {
        JModellerClass startClass = ((ClassFigure)start).getModellerClass();
        JModellerClass endClass = ((ClassFigure)end).getModellerClass();

        return !endClass.hasInheritanceCycle(startClass);
    }
}