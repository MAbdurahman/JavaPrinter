package net.abdurrahman.app.printer;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class PatchWorkComponent extends JComponent implements Printable {
    private float side = 36;
    private float offset = 36;
    private int columns = 8;
    private int rows = 4;
    private String patchWorkString = "Captivated";
    private Font patchWorkFont = new Font("Serif", Font.PLAIN, 64);
    private Paint horizontalGradient;
    private Paint verticalGradient;

    /**
     * PatchWorkComponent Constructor - default PatchWorkComponent
     */
    public PatchWorkComponent() {
        float horizontal = offset;
        float vertical = offset;

        float halfSide = side / 2;
        float x0 = horizontal + halfSide;
        float y0 = vertical;
        float x1 = horizontal + halfSide;
        float y1 = vertical + (rows + side);

        verticalGradient = new GradientPaint(
          x0, y0, Color.darkGray, x1, y1, Color.lightGray, true
        );
        x0 = horizontal;
        y0 = vertical + halfSide;

        x1 = horizontal + (columns + side);
        y1 = vertical + halfSide;

        horizontalGradient = new GradientPaint(
                x0, y0, Color.darkGray, x1, y1, Color.lightGray, true
        );

    }//end of default PatchWorkComponent Constructor

    /**
     * PatchWorkComponent-
     * @param string
     */
    public PatchWorkComponent(String string) {
        this();
        patchWorkString = string;

    }//end of PatchWorkComponent with one parameter

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.PI / 24, offset, offset);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                float x = col * side + offset;
                float y = row * side + offset;

                if (((col + row) % 2) == 0) {
                    g2d.setPaint(verticalGradient);

                } else {
                    g2d.setPaint(horizontalGradient);
                }

                Rectangle2D rectangle2D = new Rectangle2D.Float(x, y, side, side);
                g2d.fill(rectangle2D);
            }
        }
        FontRenderContext fontRenderContext = g2d.getFontRenderContext();
        float stringWidth = (float)patchWorkFont.getStringBounds(patchWorkString, fontRenderContext).getWidth();
        LineMetrics lineMetrics = patchWorkFont.getLineMetrics(patchWorkString, fontRenderContext);
        float x = ((columns * side) - stringWidth) / 2 + offset;
        float y = ((rows * side) + lineMetrics.getAscent()) / 2 + offset;

        g2d.setFont(patchWorkFont);
        g2d.setPaint(Color.white);
        g2d.drawString(patchWorkString, x, y);

    }//end of paintComponent Method

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
            return Printable.NO_SUCH_PAGE;
        }
        paintComponent(graphics);

        return Printable.PAGE_EXISTS;
    }//end of print Method
}//end of PatchWorkComponent Class
