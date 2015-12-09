package UML;


import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import org.jhotdraw.framework.*;
import org.jhotdraw.standard.*;
import org.jhotdraw.figures.*;
import static org.jhotdraw.application.DrawApplication.IMAGES;
import org.jhotdraw.util.CommandMenu;

public class NothingApp extends MDI_Customize {

    private final String CLASS_IMAGES_ = "/images/";
    private final String JAVA_DIRECTORY_ = System.getProperty("user.dir") + "\\JavaClasses";
    //private final String JAVA_DIRECTORY_ = "src\\UML";
    
    public NothingApp() {
        super("Nothing");
    }

    protected void createTools(JToolBar palette) {
        super.createTools(palette);

        Tool tool = new TextTool(this, new TextFigure());
        palette.add(createToolButton(IMAGES + "TEXT", "Text Tool", tool));

        tool = new CreationTool(this, new ClassFigure());
        palette.add(createToolButton(CLASS_IMAGES_ + "CLASS", "Class Diagram", tool));

        tool = new ConnectionTool(this, new AssociationLineConnection());
        palette.add(createToolButton(IMAGES + "LINE", "Composite Tool", tool));

        tool = new ConnectionTool(this, new DependencyLineConnection());
        palette.add(createToolButton(CLASS_IMAGES_ + "DEPENDENCY", "Dependency Line Tool", tool));

        tool = new ConnectionTool(this, new InheritanceLineConnection());
        palette.add(createToolButton(CLASS_IMAGES_ + "INHERITANCE", "Inheritance connection tool", tool));

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

    protected JMenu createAlignmentMenu() {
        CommandMenu menu = new CommandMenu("Align");
        menu.add(new ToggleGridCommand("Toggle Snap to Grid", this, new Point(4, 4)));
        return menu;
    }

    protected DrawingView createDrawingView() {
        DrawingView newView = super.createDrawingView();
        newView.setBackground(Color.white);
        return newView;
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
        menu.insert(
                new AbstractAction("Initialize Diagram from Java..") {
                    public void actionPerformed(ActionEvent event) {
                        try {
                            Drawing view = createDrawing();
                            newWindow(view);
                            initializeDiagramFromJava(JAVA_DIRECTORY_, view);
                        } catch (IOException ex) {
                            Logger.getLogger(NothingApp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }, 7);
        return menu;
    }

	//-- main -----------------------------------------------------------
    public static void main(String[] args) {
        NothingApp window = new NothingApp();
        window.open();
    }
}
