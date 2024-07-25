package net.abdurrahman.app.printer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class JavaSwingPrinter  extends JFrame {
    //Instance variables
    private PageFormat pageFormat;

    /**
     * Default Constructor -
     */
    public JavaSwingPrinter() {
        super("JavaSwingPrinter");
        initComponents();
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        pageFormat = printerJob.defaultPage();
        setVisible(true);
    }//end of JavaSwingPrinter Default Constructor

    /**
     * initComponents Method - contains code to create menu items and menus and
     * attaching JMenuBar to the JFrame
     */
    protected void initComponents() {
        setSize(300, 300);
        centerFrame();

        //Add the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File", true);
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(new FilePrintAction()).setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
        fileMenu.add(new FilePageSetupAction()).setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK | KeyEvent.SHIFT_MASK));
        fileMenu.addSeparator();
        fileMenu.add(new FileQuitAction()).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);



        //Exit the application when window closed
        addWindowListener(new WindowAdapter() {
            /**
             * windowClosing Method -
             * @param we the event to be processed
             */
           public void windowClosing(WindowEvent we) {
               setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

           } //end of windowClosing Method
        });//end of Anonymous WindowAdapter Class

    }//end of initComponents Method

    /**
     * centerFrame Method - centers the frame within the window
     */
    protected void centerFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        setLocation(x, y);

    }//end of centerFrame Method

    /**
     * FilePrintAction Class -
     */
    class FilePrintAction extends AbstractAction {
        public FilePrintAction() {
            super("Print");
        }

        /**
         * actionPerformed Method -
         * @param ae the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            ComponentPrintable componentPrintable = new ComponentPrintable(getContentPane());
            printerJob.setPrintable(componentPrintable, pageFormat);

            if (printerJob.printDialog()) {
                try {
                    printerJob.print();

                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }
            }

        }//end of actionPerformed Method
    }//end of FilePrintAction Class

    /**
     * FilePageSetupAction Class -
     */
    class FilePageSetupAction extends AbstractAction {
        /**
         * Default FilePageSetupAction Constructor -
         */
        public FilePageSetupAction() {
            super("Page Setup...");

        }//end of Default FilePageSetupAction Constructor

        /**
         * actionPerformed Method -
         * @param ae the event to be processed
         */
        public void actionPerformed(ActionEvent ae) {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            pageFormat = printerJob.pageDialog(pageFormat);

        }//end of actionPerformed Method
    }//end of FilePageSetupAction Class

    /**
     * FileQuitAction Class -
     */
    class FileQuitAction extends AbstractAction {
        /**
         * Default FileQuitAction Constructor -
         */
        public FileQuitAction() {
            super("Quit");

        }//end of Default FileQuitAction Constructor

        /**
         * actionPerformed Method -
         * @param ae the event to be processed
         */
        public void actionPerformed(ActionEvent ae) {
            System.exit(0);

        }//end of actionPerformed Method
    }//end of FileQuitAction Class

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
                new JavaSwingPrinter();

            }// end of the run Method for the Anonymous Runnable
        });// end of the Anonymous Runnable
    }//end of main Method
}//end of JavaSwingPrinter Class
