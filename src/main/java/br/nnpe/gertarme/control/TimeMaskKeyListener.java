package br.nnpe.gertarme.control;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;


public class TimeMaskKeyListener extends KeyAdapter {
    private JTextField tf;

    public TimeMaskKeyListener(JTextField tf) {
        this.tf = tf;
        this.tf.addKeyListener(this);
    }

    public void keyPressed(KeyEvent e) {
        //System.out.println("TimeMaskKeyListener");
        if (
            (tf.getText()
                   .length() == 16) &&
                (KeyEvent.VK_BACK_SPACE != e.getKeyCode())
        ) {
            return;
        }

        if (
            (tf.getCaretPosition() == 2) &&
                (KeyEvent.VK_BACK_SPACE != e.getKeyCode())
        ) {
            tf.setText(tf.getText().subSequence(0, 2) + "/");
        }

        if (
            (tf.getCaretPosition() == 5) &&
                (KeyEvent.VK_BACK_SPACE != e.getKeyCode())
        ) {
            tf.setText(tf.getText().subSequence(0, 5) + "/");
        }

        if (
            (tf.getCaretPosition() == 10) &&
                (KeyEvent.VK_BACK_SPACE != e.getKeyCode())
        ) {
            tf.setText(tf.getText().subSequence(0, 10) + " ");
        }

        if (
            (tf.getCaretPosition() == 13) &&
                (KeyEvent.VK_BACK_SPACE != e.getKeyCode())
        ) {
            tf.setText(tf.getText().subSequence(0, 13) + ":");
        }

        if (tf.getCaretPosition() > 15) {
            tf.setText(tf.getText().subSequence(0, 15).toString());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JTextField tf = new JTextField("**/**/**** **:**");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tf.addKeyListener(new TimeMaskKeyListener(tf));
        frame.getContentPane()
             .setLayout(new BorderLayout());
        frame.getContentPane()
             .add(tf);
        frame.setVisible(true);
    }
}
