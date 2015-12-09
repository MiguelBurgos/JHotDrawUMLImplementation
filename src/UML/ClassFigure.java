package UML;



import javax.swing.*;
import java.awt.*;
import static java.awt.SystemColor.menu;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.*;
import org.jhotdraw.figures.*;
import org.jhotdraw.standard.*;
import org.jhotdraw.framework.Figure;
import org.jhotdraw.framework.FigureChangeEvent;
import org.jhotdraw.contrib.*;
import org.jhotdraw.framework.DrawingEditor;
import org.jhotdraw.framework.FigureAttributeConstant;
import org.jhotdraw.framework.Handle;
import org.jhotdraw.framework.HandleEnumeration;
import org.jhotdraw.util.*;


public class ClassFigure extends GraphicalCompositeFigure {

   
    private JModellerClass  myClass;

    private Font            attributeFont;

    private Font            methodFont;

    private GraphicalCompositeFigure    myAttributesFigure;

    private GraphicalCompositeFigure    myMethodsFigure;

    private TextFigure myClassNameFigure;

    static final long serialVersionUID = 6098176631854387694L;

 
    public ClassFigure() {
        this(new RectangleFigure());
    }

   
    public ClassFigure(Figure newPresentationFigure) {
        super(newPresentationFigure);
    }


    protected void initialize() {
       
        removeAll();

       
        attributeFont = new Font("Helvetica", Font.PLAIN, 12);
        methodFont = new Font("Helvetica", Font.PLAIN, 12);

        
        setModellerClass(new JModellerClass());

        
        setClassNameFigure(new TextFigure() {
            public void setText(String newText) {
                super.setText(newText);
                getModellerClass().setName(newText);
                update();
            }
        });
        getClassNameFigure().setFont(new Font("Helvetica", Font.BOLD, 12));
        getClassNameFigure().setText(getModellerClass().getName());
        
        
        GraphicalCompositeFigure nameFigure = new GraphicalCompositeFigure(new SeparatorFigure());
        nameFigure.add(getClassNameFigure());
        nameFigure.getLayouter().setInsets(new Insets(0, 4, 0, 0));
        add(nameFigure);

        
        setAttributesFigure(new GraphicalCompositeFigure(new SeparatorFigure()));
        getAttributesFigure().getLayouter().setInsets(new Insets(4, 4, 4, 0));
        // add the figure to the Composite
        add(getAttributesFigure());

        // create a figure responsible for maintaining methods
        setMethodsFigure(new GraphicalCompositeFigure(new SeparatorFigure()));
        getMethodsFigure().getLayouter().setInsets(new Insets(4, 4, 4, 0));
        // add the figure to the Composite
        
        add(getMethodsFigure());

        setAttribute(FigureAttributeConstant.POPUP_MENU, createPopupMenu());
        
        super.initialize();
    }


    protected JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new AbstractAction("Add Public Attribute") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    addPublicAttribute("attribute");
                }
            });
        popupMenu.add(new AbstractAction("Add Private Attribute") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    addPrivateAttribute("attribute");
                }
            });
        popupMenu.add(new AbstractAction("Add Protected Attribute") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    addProtectedAttribute("attribute");
                }
            });
        popupMenu.addSeparator();
        popupMenu.add(new AbstractAction("Add Public Method") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    addPublicMethod("method()");
                }
            });
        popupMenu.add(new AbstractAction("Add Private Method") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    addPrivateMethod("method()");
                }
            });
        popupMenu.add(new AbstractAction("Add Protected Method") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    addProtectedMethod("method()");
                }
            });
        popupMenu.setLightWeightPopupEnabled(true);
        return popupMenu;
    }


    protected void setAttributesFigure(GraphicalCompositeFigure newAttributesFigure) {
        myAttributesFigure = newAttributesFigure;
    }


    public GraphicalCompositeFigure getAttributesFigure() {
        return myAttributesFigure;
    }


    protected void setMethodsFigure(GraphicalCompositeFigure newMethodsFigure) {
        myMethodsFigure = newMethodsFigure;
    }


    public GraphicalCompositeFigure getMethodsFigure() {
        return myMethodsFigure;
    }


    protected void setClassNameFigure(TextFigure newClassNameFigure) {
        myClassNameFigure = newClassNameFigure;
    }
    

    public TextFigure getClassNameFigure() {
        return myClassNameFigure;
    }

    protected void addPublicAttribute(String newAttribute) {
        getModellerClass().addAttribute(newAttribute);
        TextFigure classFigureAttribute = new TextFigure() {
            public void setText(String newString) {
                if (!getText().equals(newString)) {
                    getModellerClass().renameAttribute(getText(), newString);
                }
                if (!newString.contains("+") && !newString.contains("+ ")) super.setText("+ " + newString);
                else if (newString.contains("+") && !newString.contains("+ ")) super.setText(newString.replace("+", "+ "));
                else super.setText(newString);
                updateAttributeFigure();
            }
        };
        classFigureAttribute.setText(newAttribute);
        classFigureAttribute.setFont(attributeFont);
        getAttributesFigure().add(classFigureAttribute);
        updateAttributeFigure();
    }
    
    protected void addPrivateAttribute(String newAttribute) {
        getModellerClass().addAttribute(newAttribute);
        TextFigure classFigureAttribute = new TextFigure() {
            public void setText(String newString) {
                if (!getText().equals(newString)) {
                    getModellerClass().renameAttribute(getText(), newString);
                }
                if (!newString.contains("-") && !newString.contains("- ")) super.setText("- " + newString);
                else if (newString.contains("-") && !newString.contains("- ")) super.setText(newString.replace("-", "- "));
                else super.setText(newString);
                updateAttributeFigure();
            }
        };
        classFigureAttribute.setText(newAttribute);
        classFigureAttribute.setFont(attributeFont);
        getAttributesFigure().add(classFigureAttribute);
        updateAttributeFigure();
    }
    
    protected void addProtectedAttribute(String newAttribute) {
        getModellerClass().addAttribute(newAttribute);
        TextFigure classFigureAttribute = new TextFigure() {
            public void setText(String newString) {
                if (!getText().equals(newString)) {
                    getModellerClass().renameAttribute(getText(), newString);
                }
                if (!newString.contains("#") && !newString.contains("# ")) super.setText("# " + newString);
                else if (newString.contains("#") && !newString.contains("# ")) super.setText(newString.replace("#", "# "));
                else super.setText(newString);
                updateAttributeFigure();
            }
        };
        classFigureAttribute.setText(newAttribute);
        classFigureAttribute.setFont(attributeFont);
        getAttributesFigure().add(classFigureAttribute);
        updateAttributeFigure();
    }
    
    protected void addAttribute(String newAttribute) {
        getModellerClass().addAttribute(newAttribute);
        TextFigure classFigureAttribute = new TextFigure() {
            public void setText(String newString) {
                if (!getText().equals(newString)) {
                    getModellerClass().renameAttribute(getText(), newString);
                }
                super.setText(newString);
                updateAttributeFigure();
            }
        };
        classFigureAttribute.setText(newAttribute);
        classFigureAttribute.setFont(attributeFont);
        getAttributesFigure().add(classFigureAttribute);
        updateAttributeFigure();
    }

    protected void removeAttribute(Figure oldAttribute) {
        getModellerClass().removeAttribute(((TextFigure)oldAttribute).getText());
        getAttributesFigure().remove(oldAttribute);
        updateAttributeFigure();
    }


    protected void updateAttributeFigure() {
        getAttributesFigure().update();
        update();
    }

    protected void addPublicMethod(String newMethod) {
        getModellerClass().addMethod(newMethod);
        TextFigure classFigureMethod = new TextFigure() {
            public void setText(String newString) {
                if (!getText().equals(newString)) {
                    getModellerClass().renameMethod(getText(), newString);
                }
                if (!newString.contains("+") && !newString.contains("+ ")) super.setText("+ " + newString);
                else if (newString.contains("+") && !newString.contains("+ ")) super.setText(newString.replace("+", "+ "));
                else super.setText(newString);
                updateMethodFigure();
            }
        };
        classFigureMethod.setText(newMethod);
        classFigureMethod.setFont(methodFont);
        getMethodsFigure().add(classFigureMethod);
        updateMethodFigure();
    }
    
    protected void addPrivateMethod(String newMethod) {
        getModellerClass().addMethod(newMethod);
        TextFigure classFigureMethod = new TextFigure() {
            public void setText(String newString) {
                if (!getText().equals(newString)) {
                    getModellerClass().renameMethod(getText(), newString);
                }
                if (!newString.contains("-") && !newString.contains("- ")) super.setText("- " + newString);
                else if (newString.contains("-") && !newString.contains("- ")) super.setText(newString.replace("-", "- "));
                else super.setText(newString);
                updateMethodFigure();
            }
        };
        classFigureMethod.setText(newMethod);
        classFigureMethod.setFont(methodFont);
        getMethodsFigure().add(classFigureMethod);
        updateMethodFigure();
    }
    
    protected void addProtectedMethod(String newMethod) {
        getModellerClass().addMethod(newMethod);
        TextFigure classFigureMethod = new TextFigure() {
            public void setText(String newString) {
                if (!getText().equals(newString)) {
                    getModellerClass().renameMethod(getText(), newString);
                }
                if (!newString.contains("#") && !newString.contains("# ")) super.setText("# " + newString);
                else if (newString.contains("#") && !newString.contains("# ")) super.setText(newString.replace("#", "# "));
                else super.setText(newString);
                updateMethodFigure();
            }
        };
        classFigureMethod.setText(newMethod);
        classFigureMethod.setFont(methodFont);
        getMethodsFigure().add(classFigureMethod);
        updateMethodFigure();
    }
    
    protected void addMethod(String newMethod) {
        getModellerClass().addMethod(newMethod);
        TextFigure classFigureMethod = new TextFigure() {
            public void setText(String newString) {
                if (!getText().equals(newString)) {
                    getModellerClass().renameMethod(getText(), newString);
                }
                super.setText(newString);
                updateMethodFigure();
            }
        };
        classFigureMethod.setText(newMethod);
        classFigureMethod.setFont(methodFont);
        getMethodsFigure().add(classFigureMethod);
        updateMethodFigure();
    }
   
    protected void removeMethod(Figure oldMethod) {
        getModellerClass().removeMethod(((TextFigure)oldMethod).getText());
        getMethodsFigure().remove(oldMethod);
        updateMethodFigure();
    }


    protected void updateMethodFigure() {
        getMethodsFigure().update();
        update();
    }


    protected void setModellerClass(JModellerClass newClass) {
        myClass = newClass;
    }


    public JModellerClass getModellerClass() {
        return myClass;
    }
    

    public void figureRequestRemove(FigureChangeEvent e) {
        Figure removeFigure = e.getFigure();
        if (getAttributesFigure().includes(removeFigure)) {
            removeAttribute(removeFigure);
        }
        else if (getMethodsFigure().includes(removeFigure)) {
            removeMethod(removeFigure);
        }
        else {
            // remove itself
            listener().figureRequestRemove(new FigureChangeEvent(this, displayBox()));
        }
    }
    
    @Override
   public HandleEnumeration handles() {
	List handles = CollectionsFactory.current().createList();
	BoxHandleKit.addHandles(this, handles);
	return new HandleEnumerator(handles);
	}
 
    public Vector handle() {
        Vector handles = new Vector();
        handles.add(new HandleTracker((DrawingEditor)getPresentationFigure(), (Handle) RelativeLocator.northWest()));
        handles.add(new HandleTracker((DrawingEditor)getPresentationFigure(), (Handle)RelativeLocator.northEast()));
        handles.add(new HandleTracker((DrawingEditor)getPresentationFigure(), (Handle)RelativeLocator.southWest()));
        handles.add(new HandleTracker((DrawingEditor)getPresentationFigure(), (Handle)RelativeLocator.southEast()));

        return handles;
    }
 

    public boolean isEmpty() {
        return figureCount() == 0;
    }


    public void read(StorableInput dr) throws IOException {
        getClassNameFigure().setText(dr.readString());

        int attributesCount = dr.readInt();
        for (int attributeIndex = 0; attributeIndex < attributesCount; attributeIndex++) {
            addAttribute(dr.readString());
        }

        int methodsCount = dr.readInt();
        for (int methodIndex = 0; methodIndex < methodsCount; methodIndex++) {
            addMethod(dr.readString());
        }
        setPresentationFigure((Figure)dr.readStorable());
        setAttribute(FigureAttributeConstant.POPUP_MENU, createPopupMenu());
        update();
    }
    

    public void write(StorableOutput dw) {
        dw.writeString(getModellerClass().getName());
        dw.writeInt(getModellerClass().getNumberOfAttributes());

        Iterator attributeIterator = getModellerClass().getAttributes();
        while (attributeIterator.hasNext()) {
            dw.writeString((String)attributeIterator.next());
        }
        dw.writeInt(getModellerClass().getNumberOfMethods());

        Iterator methodIterator = getModellerClass().getMethods();
        while (methodIterator.hasNext()) {
            dw.writeString((String)methodIterator.next());
        }
        dw.writeStorable(getPresentationFigure());
    }


    private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
        // call superclass' private readObject() indirectly
        s.defaultReadObject();
        
        setAttribute(FigureAttributeConstant.POPUP_MENU, createPopupMenu());
    }
}