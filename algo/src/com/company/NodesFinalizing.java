package com.company;

        import javax.swing.*;
        import java.awt.*;

public class NodesFinalizing extends JPanel {
    final int BORDER = Drawer.BORDER, HALF_OF_CITY_SIZE = Drawer.HALF_OF_CITY_SIZE, HALF_OF_NODE_SIZE = Drawer.HALF_OF_NODE_SIZE;
    final int MYWIDTH, MYHEIGHT;
    City[] normalizedCities;
    double[] nodeXes, nodeYs;

    public NodesFinalizing(City[] cities, int MYWIDTH, int MYHEIGHT, double[] nodeXes, double[] nodeYs) {
        normalizedCities = cities;
        this.MYWIDTH = MYWIDTH;
        this.MYHEIGHT = MYHEIGHT;
        this.nodeXes = nodeXes;
        this.nodeYs = nodeYs;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


        g.setColor(Color.DARK_GRAY);
        g.drawLine(BORDER / 2 - 3, BORDER / 2 - 3, MYWIDTH + BORDER / 2 + 3, BORDER / 2 - 3);
        g.drawLine(BORDER / 2 - 3, BORDER / 2 - 3, BORDER / 2 - 3, MYHEIGHT + BORDER / 2 + 3);
        g.drawLine(BORDER / 2 - 3, MYHEIGHT + BORDER / 2 + 3, MYWIDTH + BORDER / 2 + 3, MYHEIGHT + BORDER / 2 + 3);
        g.drawLine(MYWIDTH + BORDER / 2 + 3, BORDER / 2 - 3, MYWIDTH + BORDER / 2 + 3, MYHEIGHT + BORDER / 2 + 3);

        g.drawLine(BORDER / 2, BORDER / 2 + MYHEIGHT / 2, MYWIDTH + BORDER / 2, BORDER / 2 + MYHEIGHT / 2);
        g.drawLine(BORDER / 2 + MYWIDTH / 2, BORDER / 2, BORDER / 2 + MYWIDTH / 2, BORDER / 2 + MYHEIGHT);

        g.setColor(Color.RED);
        for (City c : normalizedCities) {
            g.fillOval((int) (c.getX() * MYWIDTH + BORDER / 2 - HALF_OF_CITY_SIZE), (int) (c.getY() * MYHEIGHT + BORDER / 2 - HALF_OF_CITY_SIZE)
                    , 2 * HALF_OF_CITY_SIZE, 2 * HALF_OF_CITY_SIZE);
        }

        g.setColor(Color.BLUE);
        for (int i = 1; i < nodeXes.length; i++) {
            g.drawLine((int) nodeXes[i - 1], (int) nodeYs[i - 1], (int) nodeXes[i], (int) nodeYs[i]);
        }
        g.drawLine((int) nodeXes[nodeXes.length - 1], (int) nodeYs[nodeYs.length - 1], (int) nodeXes[0], (int) nodeYs[0]);

        g.setColor(Color.BLACK);
        for (int i = 0; i < nodeXes.length; i++) {
            g.fillOval((int) nodeXes[i] - HALF_OF_NODE_SIZE, (int) nodeYs[i] - HALF_OF_NODE_SIZE
                    , 2 * HALF_OF_NODE_SIZE, 2 * HALF_OF_NODE_SIZE);
        }
    }


}