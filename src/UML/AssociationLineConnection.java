package UML;



import org.jhotdraw.framework.*;
import org.jhotdraw.figures.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.io.*;

public class AssociationLineConnection extends LineConnection {

    private transient JPopupMenu myPopupMenu;

    static final long serialVersionUID = 6492295462615980490L;
    
    public AssociationLineConnection() {
        super();

        setStartDecoration(null);
        setEndDecoration(null);
    
        setAttribute(Figure.POPUP_MENU, createPopupMenu());
    }
    
    protected void handleConnect(Figure start, Figure end) {
        super.handleConnect(start, end);
        JModellerClass startClass = ((ClassFigure)start).getModellerClass();
        JModellerClass endClass = ((ClassFigure)end).getModellerClass();
        startClass.addAssociation(endClass);
        endClass.addAssociation(startClass);
    }

    protected void handleDisconnect(Figure start, Figure end) {
        super.handleDisconnect(start, end);
        if ((start != null) && (end!= null)) {
            JModellerClass startClass = ((ClassFigure)start).getModellerClass();
            JModellerClass endClass = ((ClassFigure)end).getModellerClass();
            startClass.removeAssociation(endClass);
            endClass.removeAssociation(startClass);
        }
    }

    public void setAttribute(String name, Object value) {
        if (name.equals(Figure.POPUP_MENU)) {
            myPopupMenu = (JPopupMenu)value;
        }
        else {
            super.setAttribute(name, value);
        }
    }

    public Object getAttribute(String name) {
        if (name.equals(Figure.POPUP_MENU)) {
            return myPopupMenu;
        }
        else {
            return super.getAttribute(name);
        }
    }

    protected JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new AbstractAction("aggregation") {
                public void actionPerformed(ActionEvent event) {
                    setAggregation(!isAggregation());
                    if (isAggregation()) {
                        ((JMenuItem)event.getSource()).setText("no aggregation");
                    }
                    else {
                        ((JMenuItem)event.getSource()).setText("aggregation");
                    }
                }
            });
        popupMenu.add(new AbstractAction("uni-directional") {
                public void actionPerformed(ActionEvent event) {
                    setUniDirectional(!isUniDirectional());
                    if (isUniDirectional()) {
                        ((JMenuItem)event.getSource()).setText("bi-directional");
                        JModellerClass startClass = ((ClassFigure)startFigure()).getModellerClass();
                        JModellerClass endClass = ((ClassFigure)endFigure()).getModellerClass();
                        endClass.addAssociation(startClass);
                    }
                    else {
                        ((JMenuItem)event.getSource()).setText("uni-directional");
                        JModellerClass startClass = ((ClassFigure)startFigure()).getModellerClass();
                        JModellerClass endClass = ((ClassFigure)endFigure()).getModellerClass();
                        endClass.removeAssociation(startClass);
                    }
                }
            });
            
        popupMenu.setLightWeightPopupEnabled(true);
        return popupMenu;
    }

    protected void setAggregation(boolean isAggregation) {
        willChange();
        if (isAggregation) {
            setStartDecoration(new AggregationDecoration());
        }
        else {
            setStartDecoration(null);
        }
        change();
        changed();
    }

    protected boolean isAggregation() {
        return getStartDecoration() != null;
    }

    protected void setUniDirectional(boolean isDirected) {
        willChange();
        if (isDirected) {
            ArrowTip arrow = new ArrowTip(0.4, 12.0, 0.0);
            arrow.setBorderColor(java.awt.Color.black);
            setEndDecoration(arrow);
        }
        else {
            setEndDecoration(null);
        }
        change();
        changed();
    }
  
    protected boolean isUniDirectional() {
        return getEndDecoration() != null;
    }

    protected void change() {
        if (listener() != null) {
            listener().figureRequestUpdate(new FigureChangeEvent(this));
        }
    }   

    private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
        // call superclass' private readObject() indirectly
        s.defaultReadObject();
        
        setAttribute(Figure.POPUP_MENU, createPopupMenu());
    }
}