package com.soronthar.rpg.runner.manager.screens;


import org.soronthar.util.StringUtils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DialogManager {
    private boolean finished = true;

    private int currentLine = 0;
    private int linesToShow = 0;

    private final int dialogX;
    private final int dialogY;
    private final int width;
    private final int height;

    private int fontWidth;
    private int fontHeight;
    private final ArrayList<String> linesToDraw;

    public DialogManager(String textFile, Rectangle viewPortBounds, FontMetrics fontMetrics) {
        int visibleWidth = viewPortBounds.width;
        int visibleHeight = viewPortBounds.height;

        height = visibleHeight / 4;
        dialogX = 0;
        dialogY = visibleHeight - height;
        width = visibleWidth;
        fontWidth = fontMetrics.getMaxAdvance();
        fontHeight = fontMetrics.getHeight();

        linesToDraw = new ArrayList<String>();

        File text = new File(textFile + ".txt");
        String[] split = textFile.split("\r?\n");

        List list= Arrays.asList(split);
//        try {
//            list = FileUtils.readLines(text, "UTF-8");
//        } catch (IOException e) {
//            throw new TechnicalException(e);
//        }

        int lineWidth = width - (2 * fontWidth);
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
            linesToDraw.add(head);

            if (tail.endsWith(".") || tail.endsWith(":")) {
                linesToDraw.add(tail);
                tail = "";
            }
        }

        if (!StringUtils.isEmptyOrNullValue(tail)) {
            linesToDraw.add(tail);
        }

        linesToShow = height / fontHeight;
    }

    public void paint(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(dialogX, dialogY, width, height);
        g.setColor(Color.white);

        int x = dialogX + fontWidth;
        int y = dialogY + fontHeight;

        int start = currentLine;
        int end = Math.min(currentLine + linesToShow, linesToDraw.size());
        for (int i = start; i < end; i++) {
            String next = linesToDraw.get(i);
            g.drawString(next, x, y);
            y += fontHeight;
        }
    }

    public boolean isFinished() {
        return currentLine >= linesToDraw.size();
    }

    public void advance() {
        currentLine = currentLine + linesToShow;
    }
}
