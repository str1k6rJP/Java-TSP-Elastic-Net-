package com.company;

import javax.swing.*;
import java.awt.*;

public class AbsoluteFinalizer extends JPanel {
    City[] normalizedCities;
    final int BORDER = Drawer.BORDER
            , HALF_OF_CITY_SIZE = Drawer.HALF_OF_CITY_SIZE
            , HALF_OF_NODE_SIZE = Drawer.HALF_OF_NODE_SIZE;

    final int MYWIDTH, MYHEIGHT;

    public AbsoluteFinalizer(City[] cities, int MYWIDTH, int MYHEIGHT) {
        normalizedCities = cities;
        this.MYWIDTH = MYWIDTH;
        this.MYHEIGHT = MYHEIGHT;

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


        g.setColor(Color.MAGENTA);
        for (int i = 1; i < normalizedCities.length; i++) {
            g.drawLine((int)(normalizedCities[i - 1].getX() * MYWIDTH + BORDER / 2)
                    , (int) (normalizedCities[i - 1].getY() * MYHEIGHT + BORDER / 2)
                    , (int) (normalizedCities[i].getX() * MYWIDTH + BORDER / 2)
                    , (int) (normalizedCities[i].getY() * MYHEIGHT + BORDER / 2));
        }
        g.drawLine((int)(normalizedCities[0].getX() * MYWIDTH + BORDER / 2)
                , (int) (normalizedCities[0].getY() * MYHEIGHT + BORDER / 2)
                , (int) (normalizedCities[normalizedCities.length - 1].getX() * MYWIDTH + BORDER / 2)
                , (int) (normalizedCities[normalizedCities.length - 1].getY() * MYHEIGHT + BORDER / 2));

    }
}
