package net.abdurrahman.app.printer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class DisappearingMessage implements ActionListener {
    private final int ONE_SECOND = 1000;
    private Timer timer;
    private JFrame frame;
    private JLabel msgLabel;

    /**
     * DisappearingMessage Constructor -
     * @param str
     * @param seconds
     */
    public DisappearingMessage (String str, int seconds) {
        frame = new JFrame ("Test Message");
        msgLabel = new JLabel (str, SwingConstants.CENTER);
        msgLabel.setPreferredSize(new Dimension(600, 400));

        timer = new Timer (this.ONE_SECOND * seconds, this);
        // only need to fire up once to make the message box disappear
        timer.setRepeats(false);

    }//end of DisappearingMessage Constructor

    /**
     * Start the timer
     */
    public void start () {
        // make the message box appear and start the timer
        frame.getContentPane().add(msgLabel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        timer.start();
    }

    /**
     * Handling the event fired by the timer
     */
    public void actionPerformed (ActionEvent ae) {
        // stop the timer and kill the message box
        timer.stop();
        frame.dispose();

    }//end of actionPerformed Method

    // =====================
// yes = 0, no = 1, cancel = 2
// timer = uninitializedValue, [x] = null

    public static void getPrintingDialog() {
        String message = "Printing will start within 10 seconds...";
        /*JOptionPane.showMessageDialog(null, message, "Printing", JOptionPane.PLAIN_MESSAGE);*/
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
        JDialog dialog = optionPane.createDialog("Printing");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                Timer timer1 = new Timer(7000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.setVisible(false);

                    }//end of actionPerformed Method for Anonymous ActionListener for Timer
                });
                timer1.setRepeats(false);
                timer1.start();

            }//end of componentShown Method for Anonymous ComponentAdapter
        });
        dialog.setVisible(true);
        Object optionPaneValue = optionPane.getValue();
        System.out.println(optionPaneValue);
        System.out.println("Finished");
        dialog.dispose();

    }//end of getPrintingDialog Method

public static void main (String[] args) {
/*        DisappearingMessage dm = new DisappearingMessage("Test", 3);
        dm.start();*/
             getPrintingDialog();

    }//end of main Method
}//end of DisappearingMessage Class
