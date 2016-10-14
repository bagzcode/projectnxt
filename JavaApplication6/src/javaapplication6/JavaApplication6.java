/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.math.*;
import java.util.Random;


public class JavaApplication6
{
    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new PointPanel());
        f.setSize(800, 600);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}

class DrawLine implements Runnable{
    double x,y;
    int value;
    
    PointPanel pointPanel;
    Random random = new Random();
    
    public DrawLine(PointPanel pointPanel){
        this.pointPanel = pointPanel;
    }
    
    
    @Override
    public void run() {
        try{
            
            for(int j = 0; j <= 360; j++)
            {
                
                if ((j >= 40) && (j<=360))
                {
                    value = random.nextInt(156-145+1)+150;
                    x = Math.sin(Math.toRadians(j))*value;
                    y = Math.cos(Math.toRadians(j))*value;
                    System.out.println(x);
                    this.pointPanel.addCenterPoint(400+x, 300+y);
                }  
               
                Thread.sleep(50);
                
            }
            
            
        }
        catch(Exception e){}
    }
    
}

class PointPanel extends JPanel
{
    List pointList;
    Color selectedColor;
    Ellipse2D selectedPoint;
    
    public PointPanel()
    {
        pointList = new ArrayList();
        selectedColor = Color.red;
        addMouseListener(new PointLocater(this));
        setBackground(Color.white);
        addCenterPoint(400,300);
        /**/
        Thread t = new Thread(new DrawLine(this));
        t.start();
    }
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D e;
        Color color;
        for(int j = 0; j < pointList.size(); j++)
        {
            e = (Ellipse2D)pointList.get(j);
            if(e == selectedPoint)
                color = selectedColor;
            else
                color = Color.blue;
            g2.setPaint(color);
            g2.fill(e);
        }
    }
    
    public List getPointList()
    {
        return pointList;
    }
    
    public void setSelectedPoint(Ellipse2D e)
    {
        selectedPoint = e;
        repaint();
    }
    
    public void addPoint(Point p)
    {
        Ellipse2D e = new Ellipse2D.Double(p.x-3, p.y-3, 6, 6);
        pointList.add(e);
        selectedPoint = null;
        repaint();
    }
    
    public void addCenterPoint(double a, double b)
    {
        Ellipse2D center = new Ellipse2D.Double(a,b, 6, 6);
        pointList.add(center);
        selectedPoint = null;
        repaint();
    }
    
}

/*
class PointLocater extends MouseAdapter
{
    PointPanel pointPanel;
    
    public PointLocater(PointPanel pp)
    {
        pointPanel = pp;
    }
    
    public void mousePressed(MouseEvent e)
    {
        Point p = e.getPoint();
        boolean haveSelection = false;
        List list = pointPanel.getPointList();
        Ellipse2D ellipse;
        for(int j = 0; j < list.size(); j++)
        {
            ellipse = (Ellipse2D)list.get(j);
            if(ellipse.contains(p))
            {
                pointPanel.setSelectedPoint(ellipse);
                haveSelection = true;
                break;
            }
        }
        if(!haveSelection)
            pointPanel.addPoint(p);
    }
}
*/