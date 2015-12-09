package UML;




import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import org.jhotdraw.standard.*;
import org.jhotdraw.figures.*;
import org.jhotdraw.framework.*;
import org.jhotdraw.contrib.*;
import org.jhotdraw.util.*;
import java.awt.Color;


public class JModellerApplication extends MDI_DrawApplication {


    public static final String DIAGRAM_IMAGES = "/images/";
    

    public JModellerApplication() {
        super("Keith Schlittkus, Felix Schwarz & Frank Imler - Petri-NetSim");
    }


    protected void createTools(JToolBar palette) {
        super.createTools(palette);

        Tool tool = new ConnectedTextTool(this, new TextFigure());
        palette.add(createToolButton(IMAGES+"ATEXT", "Label", tool));
        
        
        ClassFigure t2 = new ClassFigure();
        
        tool = new CreationTool(this, t2){
            public void mouseDragged(MouseEvent e, int x, int y) {
                
            }
            public void mousePressed(MouseEvent e, int x, int y){
                  System.out.println("hola");
            	 System.out.println("Maus x: " + x);
            	 t2.createPopupMenu();
            	 super.mouseDown(e, x, y);
             }
        };
        
        palette.add(createToolButton(IMAGES+"DIAMOND", "CLASS", tool));
                
        
        
        tool = new ConnectionTool(this, new AssociationLineConnection());
        palette.add(createToolButton(IMAGES+"LINE", "Association Tool", tool));
        
        tool = new ConnectionTool(this, new InheritanceLineConnection());
        palette.add(createToolButton(DIAGRAM_IMAGES+"INHERITANCE", "Inheritance Tool", tool));
        
      
        
    }


    protected Tool createSelectionTool() {
       return new DelegationSelectionTool(this);
    }


    protected void createMenus(JMenuBar mb) {
        mb.add(createFileMenu());
        mb.add(createEditMenu());
        mb.add(createAlignmentMenu());
        mb.add(createAttributesMenu());
        mb.add(createLookAndFeelMenu());
    }


//    protected JMenu createAttributesMenu() {
//        JMenu menu = new JMenu("Attributes");
//        menu.add(createColorMenu("Fill Color", BLACK));
//        menu.add(createColorMenu("Pen Color", "FrameColor"));
//        return menu;
//    }


    protected JMenu createAlignmentMenu() {
        CommandMenu menu = new CommandMenu("Align");
        menu.add(new ToggleGridCommand("Toggle Snap to Grid", this, new Point(4,4)));
        return menu;
    }


    protected JMenu createFileMenu() {
        JMenu menu = super.createFileMenu();
        menu.insert(
            new AbstractAction("New Window") {
                public void actionPerformed(ActionEvent event) {
                    newWindow(createDrawing());
                }
            }, 5);
        menu.insertSeparator(6);
        return menu;
    }


    protected DrawingView createDrawingView() {
        DrawingView newView = super.createDrawingView();
        newView.setBackground(Color.white);
        return newView;
    }
    

    public static void main(String[] args) {
        JModellerApplication window = new JModellerApplication();
        window.open();
    }
}