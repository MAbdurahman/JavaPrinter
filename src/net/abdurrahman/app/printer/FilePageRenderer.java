package net.abdurrahman.app.printer;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class FilePageRenderer extends JComponent implements Printable {
    //Instance variables
    private int currentPage;
    // lines contains all the lines of the lines
    private Vector lines;
    // pages is a Vector of Vectors.  Each of its elements
    // represents a single page.  Each of its elements is
    // a Vector containing Strings that are the lines for
    // a particular page.
    private Vector pages;
    private Font font;
    private int fontSize;
    private Dimension preferredSize;

    public FilePageRenderer(File file, PageFormat pageFormat) throws IOException {
        fontSize = 12;
        font = new Font("Serif", Font.PLAIN, fontSize);

        //Open the file
        BufferedReader reader = new BufferedReader(new FileReader(file));

        //Read all the lines
        String line;
        lines = new Vector();
        while ((line = reader.readLine()) != null) {
            lines.addElement(line);

            //Clean up
            reader.close();

            //Now paginate, based on the PageFormat
            paginate(pageFormat);
        }



    }//end of FilePageRenderer Constructor

    public void paginate(PageFormat pageFormat) {
        currentPage = 0;
        pages = new Vector();
        float y = fontSize;
        Vector page = new Vector();
        for (int i = 0; i < lines.size(); i++) {
            String line = (String) lines.elementAt(i);
            page.addElement(line);
            y += fontSize;
            if (y + fontSize * 2 > pageFormat.getImageableHeight()) {
                y = 0;
                pages.addElement(page);
                page = new Vector();
            }
        }
        //Add the last page
        if (pages.size() > 0) {
            pages.addElement(page);
        }
        //Set our preferred size based on the PageFormat
        preferredSize = new Dimension((int)(pageFormat.getImageableWidth()),
                                (int)pageFormat.getImageableHeight());
        repaint();

    }//end of paginate Method
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //Make the background white
        java.awt.geom.Rectangle2D rectangle2D =
                new java.awt.geom.Rectangle2D.Float(0, 0, preferredSize.width, preferredSize.height);
        g2d.setPaint(Color.white);
        g2d.fill(rectangle2D);

        //Get the current page
        Vector page = (Vector) pages.elementAt(currentPage);

        //Draw all the lines for this page
        g2d.setFont(font);
        g2d.setColor(Color.black);
        float x = 0;
        float y = fontSize;
        for (int i = 0; i < page.size(); i++) {
            String line = (String) page.elementAt(i);
            if (line.length() > 0) {
                g2d.drawString(line, (int)x , (int)y);
            }
            y += fontSize;
        }
    }//end of paintComponent Method

    /**
     * @param graphics   the context into which the page is drawn
     * @param pageFormat the size and orientation of the page being drawn
     * @param pageIndex  the zero based index of the page to be drawn
     * @return int - returns an int representing
     * @throws PrinterException - throws a PrinterException exception
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex >= pages.size()) {
            return Printable.NO_SUCH_PAGE;
        }
        int savedPage = currentPage;
        currentPage = pageIndex;

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        paint(g2d);

        currentPage = savedPage;
        return Printable.PAGE_EXISTS;

    }//end of print Method

    public Dimension getPreferredSize() {
        return preferredSize;

    }//end of getPreferredSize Method

    public int getCurrentPage() {
        return currentPage;

    }//end of getCurrentPage Method

    public int getNumberOfPages() {
        return pages.size();

    }//end of getNumberOfPage Method

    public void nextPage() {
        if (currentPage < pages.size() - 1) {
            currentPage++;
        }
        repaint();

    }//end of nextPage Method
    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
        }
        repaint();
    }
}//end of FilePageRenderer Class
