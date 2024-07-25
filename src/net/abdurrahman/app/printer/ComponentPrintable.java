package net.abdurrahman.app.printer;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterGraphics;

public class ComponentPrintable implements Printable {
    private Component component;
    public ComponentPrintable(Component component) {
        this.component = component;
    }
    /**
     * Prints the page at the specified index into the specified
     * {@link Graphics} context in the specified
     * format.  A {@code PrinterJob} calls the
     * {@code Printable} interface to request that a page be
     * rendered into the context specified by
     * {@code graphics}.  The format of the page to be drawn is
     * specified by {@code pageFormat}.  The zero based index
     * of the requested page is specified by {@code pageIndex}.
     * If the requested page does not exist then this method returns
     * NO_SUCH_PAGE; otherwise PAGE_EXISTS is returned.
     * The {@code Graphics} class or subclass implements the
     * {@link PrinterGraphics} interface to provide additional
     * information.  If the {@code Printable} object
     * aborts the print job then it throws a {@link PrinterException}.
     *
     * @param graphics   the context into which the page is drawn
     * @param pageFormat the size and orientation of the page being drawn
     * @param pageIndex  the zero based index of the page to be drawn
     * @return PAGE_EXISTS if the page is rendered successfully
     * or NO_SUCH_PAGE if {@code pageIndex} specifies a
     * non-existent page.
     * @throws PrinterException thrown when the print job is terminated.
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        boolean wasBuffered = disableDoubleBuffering(component);
        component.paint(g2d);
        restoreDoubleBuffering(component, wasBuffered);

        return Printable.PAGE_EXISTS;
    }//end of print Method

    private boolean disableDoubleBuffering(Component component) {
        if (component instanceof JComponent == false) {
            return false;
        }
        JComponent jComponent = (JComponent) component;
        boolean wasDoubleBuffering = jComponent.isDoubleBuffered();
        jComponent.setDoubleBuffered(false);
        return wasDoubleBuffering;

    }//end of disableDoubleBuffering Method

    private void restoreDoubleBuffering(Component component, boolean wasBuffered) {
        if (component instanceof JComponent) {
            JComponent jComponent = (JComponent) component;
            jComponent.setDoubleBuffered(wasBuffered);
        }

    }//end of restoreDoubleBuffering Method
}//end of ComponentPrintable Class
