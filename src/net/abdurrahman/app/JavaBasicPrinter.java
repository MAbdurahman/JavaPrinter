package net.abdurrahman.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
                /*JOptionPane.showMessageDialog(null, "Printing job successfully sent!");*/
                getPrintingDialog();

            } catch (Exception ex) {
                String message = ex.getMessage();
                JOptionPane.showMessageDialog(null, message + "\n" + ex.getStackTrace());
                ex.printStackTrace();
            }
        }
    }//end of main Method

    /**
     * getPrintingDialog Method -
     */
    public static void getPrintingDialog() {
        String spaces_8 = "        ";
        String message = spaces_8 +"Printing will start within 8 seconds. . .";
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        JDialog dialog = optionPane.createDialog("Printing");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addComponentListener(new ComponentAdapter() {
            /**
             * componentShown Method -
             * @param e the event to be processed
             */
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                Timer timer = new Timer(3000, new ActionListener() {
                    /**
                     * actionPerformed Method -
                     * @param ae the event to be processed
                     */
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        dialog.setVisible(false);

                    }//end of actionPerformed Method of Anonymous ActionListener
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        dialog.setVisible(true);
        Object value = optionPane.getValue();
        System.out.println(value);
        dialog.dispose();

    }//end of getPrintingDialog Method

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

