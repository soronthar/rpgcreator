package spikes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

/**
 * Created by IntelliJ IDEA.
 * User: Administrador
 * Date: 16/04/12
 * Time: 07:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class Draw extends JFrame {
    class P extends JPanel {
        P() {
            this.setPreferredSize(new Dimension(64, 64));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Color color = g.getColor();
            int padding = 10;
            int tileSize = 64;
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, tileSize, tileSize);
            g.setColor(Color.blue);
            g.drawRect(0, 0, tileSize, tileSize);

            g.drawLine(tileSize / 4, 0, tileSize / 4, tileSize);
            g.drawLine(tileSize / 2, 0, tileSize / 2, tileSize);
            g.drawLine(3 * tileSize / 4, 0, 3 * tileSize / 4, tileSize);

            g.drawLine(0, tileSize / 4, tileSize, tileSize / 4);
            g.drawLine(0, tileSize / 2, tileSize, tileSize / 2);
            g.drawLine(0, 3 * tileSize / 4, tileSize, 3 * tileSize / 4);

            drawIcon(g, padding, tileSize);
            g.setColor(color);
        }

        private void drawIcon(Graphics g, int padding, int tileSize) {

            g.setColor(Color.GREEN);
            int sideSize = tileSize - (padding * 2) - 1;
            g.drawRect(padding, padding, sideSize, sideSize);


            AttributedString s = new AttributedString("S");
            int fontSize = tileSize / 2;
            s.addAttribute(TextAttribute.SIZE, fontSize);
            g.drawString(s.getIterator(), (tileSize - fontSize) / 2, tileSize - ((tileSize - fontSize) / 2));


//            g.setColor(Color.green);

//            for (int x = padding+1; x < tileSize-padding-1; x++) {
//                for (int y = padding+2; y <tileSize-padding; y += 2) {
//                    g.drawLine(x, y, x, y);
//                }
//            }
        }

    }

    public Draw() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new FlowLayout());
        this.add(new P());
        JButton comp = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Draw.this.repaint();
            }
        });
        comp.setBackground(Color.black);
        this.add(comp);
        this.setBackground(Color.BLACK);
        this.setSize(new Dimension(200, 200));

//        pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Draw();
    }
}
