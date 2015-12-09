package UML;


import org.jhotdraw.figures.AbstractLineDecoration;
import org.jhotdraw.util.*;
import java.awt.*;
import java.io.*;


public class AggregationDecoration extends AbstractLineDecoration {


    private int     mySize;

    static final long serialVersionUID = 5949131894547260707L;


    public AggregationDecoration() {
        this(20);
    }


    public AggregationDecoration(int newSize) {
        setSize(newSize);
        setFillColor(Color.white);
        setBorderColor(Color.black);
    }
    

    public Polygon outline(int x1, int y1, int x2, int y2) {
        Polygon shape = new Polygon();

        // calculate direction vector
        double xDir = (double)(x2 - x1);
        double yDir = (double)(y2 - y1);
        
        // calculate direction vector length
        double vLength = Math.sqrt(xDir*xDir + yDir*yDir);
        if (vLength == 0.0) {
            return shape;
        }

        if (xDir == 0.0) {
            xDir = 1.0;
        }

        // normalize direction vector
        xDir = xDir / vLength;
        yDir = yDir / vLength;

        double endX = x1 + xDir * getSize();
        double endY = y1 + yDir * getSize();

        // calculate vector length
        double h = Math.sqrt(getSize());
        
        // calculate orthogonal vector
        double v1y = 1.0;
        double v1x = -(yDir * v1y) / xDir;
        // calculate orthogonal vector length
        double v1Length = Math.sqrt(v1x*v1x + v1y*v1y);
        // normalize orthogonal vector
        v1y = v1y / v1Length;
        v1x = v1x / v1Length;
        double p1y = (endY + y1)/2 + v1y * h;
        double p1x = (endX + x1)/2 + v1x * h;
        double p2y = (endY + y1)/2 - v1y * h;
        double p2x = (endX + x1)/2 - v1x * h;

        shape.addPoint(x1, y1);

        shape.addPoint((int)p1x, (int)p1y);
        shape.addPoint((int)endX, (int)endY);
        shape.addPoint((int)p2x, (int)p2y);
                
        shape.addPoint(x1, y1);
        
        return shape;

    }


    public void write(StorableOutput dw) {
        super.write(dw);
        dw.writeInt(getSize());
    }
    

    public void read(StorableInput dr) throws IOException {
        super.read(dr);
        setSize(dr.readInt());
    }
    

    public void setSize(int newSize) {
        mySize = newSize;
    }
    

    public int getSize() {
        return mySize;
    }
}