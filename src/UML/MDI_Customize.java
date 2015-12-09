package UML;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jhotdraw.contrib.MDI_DrawApplication;
import org.jhotdraw.framework.Drawing;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MiguelAngel
 */
public class MDI_Customize extends MDI_DrawApplication{

    public MDI_Customize(String title) {
        super(title);
    }
    
    public void initializeDiagramFromJava(String directory, Drawing view) throws IOException{
        ArrayList<String> classNames = readDirectory(directory);
        for (String className : classNames) {
            ClassFigure figure = new ClassFigure();
            figure.getClassNameFigure().setText(className);
            view.add(figure);
        }
    }
    
    private ArrayList<String> readDirectory(String directory) throws IOException {
        
        File f = new File(directory);
        ArrayList javaFiles = new ArrayList();
        if(f.exists()){
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                if(files[i].getName().contains(".java")){
                    javaFiles.add(files[i].getName().replace(".java", ""));
                }
            }
        }
        return javaFiles;
    }
    
    
}
