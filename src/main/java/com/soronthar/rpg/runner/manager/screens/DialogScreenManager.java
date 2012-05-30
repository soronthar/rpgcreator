package com.soronthar.rpg.runner.manager.screens;

import java.awt.*;

public class DialogScreenManager {
    private DialogManager dialog;

    private void showTextDialog(Graphics g) {
        this.dialog.paint(g);
    }

    public void paint(Graphics g) {
        if (isShowingDialog()) {
            showTextDialog(g);
        }
    }

    public void showDialog(String text, Rectangle viewPort, FontMetrics fontMetrics) {
        this.dialog = new DialogManager(text,viewPort,fontMetrics);
    }

    public boolean isShowingDialog() {
        return this.dialog != null && !this.dialog.isFinished();
    }

    public void advanceDialog() {
        if (dialog.isFinished()) {
            this.dialog = null;
        } else {
            dialog.advance();
        }
    }
}
