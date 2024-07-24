package net.abdurrahman.app;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;


public class JavaImageArea {
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
        printerJob.setPrintable(new OutlinePrintable());
        if (printerJob.printDialog()) {
            try {
                printerJob.print();
                String message = "Printing job successfully sent, and starting to print.";
                JOptionPane.showMessageDialog(null, message);

            } catch (Exception ex) {
                String message = ex.getMessage();
                JOptionPane.showMessageDialog(null, message + "\n" + ex.getStackTrace());
                ex.printStackTrace();
            }
        }
    }//end of main Method

    static class OutlinePrintable implements Printable {

        /**
         * print Method -
         * @param graphics   the context into which the page is drawn
         * @param pageFormat the size and orientation of the page being drawn
         * @param pageIndex  the zero based index of the page to be drawn
         * @return
         * @throws PrinterException
         */
        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

            if (pageIndex != 0) {
                return Printable.NO_SUCH_PAGE;
            }
            Graphics2D g2d = (Graphics2D) graphics;
            Rectangle2D outline = new Rectangle2D.Double(pageFormat.getImageableX(), pageFormat.getImageableY(),
                    pageFormat.getImageableWidth(), pageFormat.getImageableHeight());
            g2d.setPaint(Color.black);
            g2d.draw(outline);

            return Printable.PAGE_EXISTS;

        }//end of print Method

    }//end of OutlinePrintable Class
}//end of JavaImageArea Class
