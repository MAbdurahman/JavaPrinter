package net.abdurrahman.app.printer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Objects;
import java.util.Random;

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
        setSize(500, 300);

        centerFrame();

        /** ImageIcons for JMenu and JMenuItems */
        ImageIcon printIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/print.png")));
        ImageIcon pageSetupIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/pageSetup.png")));
        ImageIcon quitIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/quit.png")));


        //Add the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File", true);
        fileMenu.add(new FilePrintAction(printIcon)).setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(new FilePageSetupAction(pageSetupIcon)).setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        fileMenu.addSeparator();
        fileMenu.add(new FileQuitAction(quitIcon)).setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
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
        public FilePrintAction(ImageIcon icon) {
            super("Print", icon);
        }

        /**
         * actionPerformed Method -
         * @param ae the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            /*ComponentPrintable componentPrintable = new ComponentPrintable(getContentPane());*/
            PatchWorkComponent workComponent = new PatchWorkComponent("Captivated");
            /*printerJob.setPrintable(componentPrintable, pageFormat);*/
            printerJob.setPrintable(workComponent);

            if (printerJob.printDialog()) {
                try {
                    printerJob.print();
                    getPrintingDialog();

                } catch (PrinterException ex) {
                    String message = ex.getMessage() + "\n" + ex.getStackTrace();
                    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
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
        public FilePageSetupAction(ImageIcon icon) {
            super("Page Setup ...", icon);

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
        public FileQuitAction(ImageIcon icon) {
            super("Exit", icon);

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
     * getPrintingDialog Method -
     */
    public static void getPrintingDialog() {
        String spaces_8 = "        ";
        String message = spaces_8 + getPrintString();
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
     * getPrintString Method - selects a random string from printStrings
     * @return String - returns a random print string message
     */
    public static String getPrintString() {
        final String [] printStrings = {
                "Sending to printer. . .",
                "Printing will start within 8 seconds. . .",
                "Preparing to print. . ."
        };
        Random random = new Random ();
        int index = random.nextInt (printStrings.length);

        return printStrings[index];

    }//end of getPrintString Method

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
