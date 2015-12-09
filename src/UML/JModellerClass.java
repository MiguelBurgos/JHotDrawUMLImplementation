package UML;



import java.util.*;
import java.io.Serializable;

/**
 *
 * @author MiguelAngel
 */
public class JModellerClass implements Serializable {

    private String myName;   
    private Vector myAttributes;  
    private Vector myMethods;
    private Vector myAssociatedClasses;
    private Vector mySuperclasses;
    private Vector myDependClasses;

    static final long serialVersionUID = -3748332488864682801L;

    public JModellerClass() {
        this("Class");
    }
 
    public JModellerClass(String newClassName) {
        setName(newClassName);
        myAttributes = new Vector();
        myMethods = new Vector();
        myAssociatedClasses = new Vector();
        mySuperclasses = new Vector();
        myDependClasses = new Vector();
    }

    public void setName(String newName) {
        myName = newName;
    }
    
    public String getName() {
        return myName;
    }
    
    public void addAttribute(String newAttribute) {
        myAttributes.addElement(newAttribute);
    }
    
    public void removeAttribute(String oldAttribute) {
        myAttributes.removeElement(oldAttribute);
    }

    public void renameAttribute(String oldAttribute, String newAttribute) {
        int attributeIndex = myAttributes.indexOf(oldAttribute);
        if (attributeIndex >= 0) {
            myAttributes.removeElementAt(attributeIndex);
            myAttributes.insertElementAt(newAttribute, attributeIndex);
        }
    }

    public Iterator getAttributes() {
        return myAttributes.iterator();
    }

    public int getNumberOfAttributes() {
        return myAttributes.size();
    }

    public boolean hasAttribute(String checkAttributeName) {
        return myAttributes.contains(checkAttributeName);
    }
      
    public void addMethod(String newMethod) {
        myMethods.addElement(newMethod);
    }
  
    public void removeMethod(String oldMethod) {
        myMethods.removeElement(oldMethod);
    }
    
    public void renameMethod(String oldMethod, String newMethod) {
        int methodIndex = myMethods.indexOf(oldMethod);
        if (methodIndex >= 0) {
            myMethods.removeElementAt(methodIndex);
            myMethods.insertElementAt(newMethod, methodIndex);
        }
    }

    public Iterator getMethods() {
        return myMethods.iterator();
    }

    public int getNumberOfMethods() {
        return myMethods.size();
    }

    public boolean hasMethod(String checkMethodName) {
        return myMethods.contains(checkMethodName);
    }

    public void addAssociation(JModellerClass newAssociatedClass) {
        myAssociatedClasses.add(newAssociatedClass);
    }
   
    public void removeAssociation(JModellerClass oldAssociatedClass) {
        myAssociatedClasses.remove(oldAssociatedClass);
    }

    public boolean hasAssociation(JModellerClass checkAssociatedClass) {
        return myAssociatedClasses.contains(checkAssociatedClass);
    }
    
    public Iterator getAssociations() {
        return myAssociatedClasses.iterator();
    }
    
    public void addSuperclass(JModellerClass newSuperclass) {
        mySuperclasses.add(newSuperclass);
    }
    
    public void removeSuperclass(JModellerClass oldSuperclass) {
        mySuperclasses.remove(oldSuperclass);
    }

    public Iterator getSuperclasses() {
        return mySuperclasses.iterator();
    }   

    public boolean hasInheritanceCycle(JModellerClass possibleSubclass) {
        if (possibleSubclass == this) {
            return true;
        }

        return possibleSubclass.isSuperclass(this);

    }

    public boolean isSuperclass(JModellerClass possibleSubclass) {
        if (possibleSubclass.mySuperclasses.contains(this)) {
            return true;
        }
        
        Iterator i = possibleSubclass.getSuperclasses();
        while (i.hasNext()) {
            Object currentObject = i.next();
            if (isSuperclass((JModellerClass) currentObject)) {
                return true;
            }
        }

        return false;
    }
  
    public void addDependency(JModellerClass newDependency) {
        myDependClasses.add(newDependency);
    }
    
    public void removeDependency(JModellerClass oldDependency) {
        myDependClasses.remove(oldDependency);
    }
    
    public boolean hasDependency(JModellerClass checkDependency) {
        return myDependClasses.contains(checkDependency);
    }
    
    public Iterator getDependencies() {
        return myDependClasses.iterator();
    }
}