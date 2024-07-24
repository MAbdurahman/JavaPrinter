package net.abdurrahman.app;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * JavaBasicPrinter - Basic PrinterJob class that controls the printing process.  It can start
 * the printing or cancel the printing.  Also contains methods for displaying a print dialog
 * and page setup dialog.
 *
 * @author MAbdurrahman
 * @date 23 July 2024
 * @version 1.0.0
 */
public class JavaBasicPrinter {
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

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(new BasicPrintable());

        if (printerJob.printDialog()) {
            try {
                printerJob.print();
                JOptionPane.showMessageDialog(null, "Printing job successfully sent!");

            } catch (Exception ex) {
                String message = ex.getMessage();
                JOptionPane.showMessageDialog(null, message + "\n" + ex.getStackTrace());
                ex.printStackTrace();
            }
        }
    }//end of main Method

    /**
     * BasicPrintable Class - Basic Printable Interface, which represents what will be printed.  It
     * contains a single method, print()
     */
    static class BasicPrintable implements Printable {

        /**
         * @param graphics   the context into which the page is drawn
         * @param pageFormat the size and orientation of the page being drawn
         * @param pageIndex  the zero based index of the page to be drawn
         * @return
         * @throws PrinterException
         */
        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex != 0) {
                return NO_SUCH_PAGE;
            }
            Graphics2D g2d = (Graphics2D) graphics;
            String pangram = "Sixty zippers were quickly picked from the woven jute bag.";
            g2d.setColor(Color.black);
            g2d.setFont(new Font("Arial", Font.PLAIN, 16));
            g2d.drawString(pangram, 50, 144);
            return PAGE_EXISTS;

        }//end of print Method
    }//end of BasicPrintable Class
}//end of BasicPrinter Class

