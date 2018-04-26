import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MouseDrawCircle extends JPanel implements MouseListener, MouseMotionListener {
    public ArrayList<MyCircle> circles = new ArrayList<MyCircle>();
    public Color drawingColor = Color.black;
    public HashMap<String, String> map;


    public MouseDrawCircle() {
        setBackground(Color.BLACK);
        addMouseListener(this);
        addMouseMotionListener(this);
        map = new HashMap<>();
    }

    public void mousePressed(MouseEvent evt) {


        if ( evt.isShiftDown() ) {
            circles.clear();
            repaint();
            return;
        }

        if ( evt.isMetaDown() ) {

        }else if (evt.isControlDown()) {

        }else  if (evt.isAltDown()){

        } else {
            MyCircle circle;
            if (FinalProject.randGen) {
                circle = new MyCircle(rand(0, this.getWidth()), rand(0, this.getHeight()), FinalProject.fillCir);
            }
            // no buttons are pressed
            else circle = new MyCircle(evt.getX(), evt.getY(), FinalProject.fillCir);

            if (FinalProject.randColor) {
                Color randColor = genRandColor();
                FinalProject.colorIndi.setBackground(randColor);
                FinalProject.curColor = randColor;
                circle.color = randColor;
            }
            else circle.color = FinalProject.curColor;

            circles.add(circle);
        }

    } // end mousePressed();

    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
    public void mouseClicked(MouseEvent evt) { }
    public void mouseReleased(MouseEvent evt) {
        int x2 = evt.getX();  // x-coordinate where user clicked.
        int y2= evt.getY();  // y-coordinate where user clicked.
        double x = circles.get(circles.size() - 1).x;
        double y = circles.get(circles.size() - 1).y;
        double radius;
        if (FinalProject.randGen) {
            radius = rand(5,70);
//            radius = rand(,20);
        }
        else radius =  Math.sqrt(Math.pow(y2 - y, 2) + Math.pow(x2 - x, 2));
        circles.get(circles.size() - 1).radius = radius;
        repaint();
    }

    // these are the MouseMotionEvent methods
    public void mouseMoved(MouseEvent evt){ }

    public void mouseDragged(MouseEvent evt){
//        int x = evt.getX();  // x-coordinate where user clicked.
//        int y = evt.getY();  // y-coordinate where user clicked.

//        if (points.size()>= 200)
//            points.remove(0);
//        repaint();
    }
    public void paintComponent(Graphics g){

        g.setColor(Color.white);
        g.fillRect(0,0,getWidth(),getHeight());

        for (MyCircle circle : circles) {
            double x = circle.x, y = circle.y;
            double radius = circle.radius;
            int u = (int)(x-radius);
            int v = (int)(y-radius);
            int w = (int)(2*radius);
            int h = w;
            if (circle.filled) {
                g.setColor( circle.color );
                g.fillOval( u,v,w,h );
            }

//            System.out.println("g = [" + circle + "]");
            g.setColor( circle.color );
            g.drawOval(u,v,w,h );
//            System.out.println("g = [" + circle + "]");
        }



    }

    static class MyCircle{
        public double radius;     // The radius of the circle.
        public double x,y;       // The location of the center of the circle.
        public boolean filled;
        public Color color;   // The color of the circle.
        public MyCircle(double x, double y, boolean fill) {
            this.x = x;
            this.y = y;
            this.filled = fill;
        }
        public String toString() {
//            return "Circle x = " + this.x + ", y = " + this.y + ", color is " + color.toString();
            int fill = filled ? 1 : 0;
            return this.x + "_" + this.y + "_" + this.radius + "_" + fill + "_"  + color.toString();
        }
    }
    public static Color genRandColor() {
        Random randomGenerator = new Random();

        int r = randomGenerator.nextInt(256);
        int g = randomGenerator.nextInt(256);
        int b = randomGenerator.nextInt(256);
        return new Color(r, g, b);
    }

    public void withdraw() {
        if (circles.size() >= 1)
            circles.remove(circles.size() -1);
        repaint();
    }

    private static double rand(double rangeMin, double rangeMax) {
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        return randomValue;
    }

}
