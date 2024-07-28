package net.abdurrahman.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
                /*String message = "Printing job successfully sent. . .";
                JOptionPane.showMessageDialog(null, message);*/
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
