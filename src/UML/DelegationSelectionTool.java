package UML;



import org.jhotdraw.framework.*;
import org.jhotdraw.contrib.*;
import org.jhotdraw.figures.*;
import java.awt.event.*;



public class DelegationSelectionTool extends CustomSelectionTool {

   
    private TextTool myTextTool;
    
    public DelegationSelectionTool(DrawingEditor newEditor) {
        super(newEditor);
        setTextTool(new TextTool(newEditor, new TextFigure()));
    }
    
 
    protected void handleMouseDoubleClick(MouseEvent e, int x, int y) {
        Figure figure = drawing().findFigureInside(e.getX(), e.getY());
        if ((figure != null) && (figure instanceof TextFigure)) {
            getTextTool().activate();
            getTextTool().mouseDown(e, x, y);
        }
    }


    protected void handleMouseClick(MouseEvent e, int x, int y) {
        deactivate();
    }


    public void deactivate() {
        super.deactivate();
        if (getTextTool().isActive()) {
    	    getTextTool().deactivate();
        }
    }


    protected void setTextTool(TextTool newTextTool) {
        myTextTool = newTextTool;
    }

   
    protected TextTool getTextTool() {
       return myTextTool;
    }
}