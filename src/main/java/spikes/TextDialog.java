package spikes;

import org.apache.commons.io.FileUtils;
import org.soronthar.error.TechnicalException;
import org.soronthar.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class TextDialog extends Canvas {
    public TextDialog() {
        //Ignore repaints and focus so we are in complete control over the rendering
//        this.setIgnoreRepaint(true);
        this.setFocusable(false);
        this.setPreferredSize(new Dimension(640, 480));
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0, 0, getWidth(), getHeight());

        int height = this.getHeight() / 4;
        int width = this.getWidth();
        int dialogX = 0;
        int dialogY = this.getHeight() - height;
        g.setColor(Color.blue);
        g.fillRect(dialogX, dialogY, width, height);
        g.setColor(Color.white);

        FontMetrics fontMetrics = g.getFontMetrics();
        int fontWidth = fontMetrics.getMaxAdvance();
        int fontHeight = fontMetrics.getHeight();
        File text = new File("resources/sign.txt");
        java.util.List list;
        try {
            list = FileUtils.readLines(text, "UTF-8");
        } catch (IOException e) {
            throw new TechnicalException(e);
        }

        int lineWidth = width - (2 * fontWidth);
        int x = dialogX + fontWidth;
        int y = dialogY + fontHeight;
        String head = "", tail = "";
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            String line = tail + " " + iterator.next();

            char[] chars = line.toCharArray();

            int i = chars.length;
            while (i > 0 && fontMetrics.charsWidth(chars, 0, i) > lineWidth) {
                i--;
            }
            head = line.substring(0, i);
            tail = line.substring(i);
            g.drawString(head, x, y);
            y += fontHeight;
        }

        if (!StringUtils.isEmptyOrNullValue(tail)) {
            g.drawString(tail, x, y);
        }


    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TextDialog());
        frame.pack();
        frame.setVisible(true);
    }
}
