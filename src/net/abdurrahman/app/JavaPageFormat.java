package net.abdurrahman.app;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.print.*;

public class JavaPageFormat {
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
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pf = printJob.pageDialog(printJob.defaultPage());
        Paper paper = new Paper();
        double margin = 36; //half-inch
        paper.setImageableArea(margin, margin,
                paper.getWidth() - (margin * 2), paper.getHeight() - (margin * 2));

        pf.setPaper(paper);
        printJob.setPrintable(new PageFormatPrintable(), pf);

        if (printJob.printDialog()) {
            try {
                printJob.print();
                String message = "Printing job successfully sent, and starting to print.";
                JOptionPane.showMessageDialog(null, message, "Printing", JOptionPane.PLAIN_MESSAGE);

            } catch (Exception ex) {
                String message = ex.getMessage() + "\n" + ex.getStackTrace();
                JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }//end of main Method

    static class PageFormatPrintable implements Printable {

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
            String pangram = "The quick brown fox jumps over the lazy dog";
            g2d.setFont(new Font("Serif", Font.PLAIN, 20));
            g2d.setPaint(Color.black);
            g2d.drawString(pangram, 50, 100);
            Rectangle2D outline = new Rectangle2D.Double(pageFormat.getImageableX(), pageFormat.getImageableY(),
                    pageFormat.getImageableWidth(), pageFormat.getImageableHeight());
            g2d.draw(outline);

            return Printable.PAGE_EXISTS;

        }//end of print Method
    }//end of PageFormatPrintable Class
}//end of JavaPageFormat Class
