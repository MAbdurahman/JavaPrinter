package net.abdurrahman.app.printer;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;

public class JavaFilePrinter extends Frame {
    //Instance variables
    private PageFormat pageFormat;
    private FilePageRenderer filePageRenderer;




    /**
     * main Method - contains the command line arguments
     * @param args - String[] representing the command line arguments
     */
    public static void main(String[] args) {
        /** Get the Look and Feel of the system */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException |
                 InstantiationException |
                 IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            String message = ex.getMessage();
            JOptionPane.showMessageDialog(null, message);
        }
        SwingUtilities.invokeLater(new Runnable() {
            /**
             * run Method - implements the one method, run of Interface Runnable
             */
            @Override
            public void run() {
                new JavaFilePrinter();

            }// end of the run Method for the Anonymous Runnable
        });// end of the Anonymous Runnable
    }//end of main Method
}//end of JavaFilePrinter Class
