package net.abdurrahman.app.printer;

import java.awt.*;
import java.awt.print.*;
import java.io.*;
import java.util.Vector;

import javax.swing.*;

public class FilePageRenderer
        extends JComponent
        implements Printable {
    private int currentPage;
    // mLines contains all the lines of the file.
    private Vector mLines;
    // mPages is a Vector of Vectors. Each of its elements
    //   represents a single page. Each of its elements is
    //   a Vector containing Strings that are the lines for
    //   a particular page.
    private Vector mPages;
    private Font pageFont;
    private int fontSize;
    private Dimension preferredSize;

    /**
     * FilePageRenderer Constructor -
     * @param file
     * @param pageFormat
     * @throws IOException
     */
    public FilePageRenderer(File file, PageFormat pageFormat)
            throws IOException {
        fontSize = 14;
        pageFont = new Font("Serif", Font.PLAIN, fontSize);
        // Open the file.
        BufferedReader reader = new BufferedReader(
                new FileReader(file));
        // Read all the lines.
        String line;
        mLines = new Vector();
        while ((line = reader.readLine()) != null)
            mLines.addElement(line);
        // Clean up.
        reader.close();
        // Now paginate, based on the PageFormat.
        paginate(pageFormat);

    }//end of FilePageRenderer Constructor

    /**
     * paginate Method -
     * @param pageFormat
     */
    public void paginate(PageFormat pageFormat) {
        currentPage = 0;
        mPages = new Vector();
        float y = fontSize;
        Vector page = new Vector();
        for (int i = 0; i < mLines.size(); i++) {
            String line = (String)mLines.elementAt(i);
            page.addElement(line);
            y += fontSize;
            if (y + fontSize * 2 > pageFormat.getImageableHeight()) {
                y = 0;
                mPages.addElement(page);
                page = new Vector();
            }
        }
        // Add the last page.
        if (page.size() > 0) mPages.addElement(page);
        // Set our preferred size based on the PageFormat.
        preferredSize = new Dimension((int)pageFormat.getImageableWidth(),
                (int)pageFormat.getImageableHeight());

        repaint();

    }//end of paginate Method

    /**
     * paintComponent -
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        // Make the background white.
        java.awt.geom.Rectangle2D rectangle2D = new java.awt.geom.Rectangle2D.Float(0, 0,
                preferredSize.width, preferredSize.height);
        g2.setPaint(Color.white);
        g2.fill(rectangle2D);
        // Get the current page.
        Vector page = (Vector)mPages.elementAt(currentPage);
        // Draw all the lines for this page.
        g2.setFont(pageFont);
        g2.setPaint(Color.black);
        float x = 0;
        float y = fontSize;
        for (int i = 0; i < page.size(); i++) {
            String line = (String)page.elementAt(i);
            if (line.length() > 0) g2.drawString(line, (int)x, (int)y);
            y += fontSize;
        }
    }//end of paintComponent Method

    /**
     * print Method -
     * @param g the context into which the page is drawn
     * @param pageFormat the size and orientation of the page being drawn
     * @param pageIndex the zero based index of the page to be drawn
     * @return int representing Printable.NO_SUCH_PAGE and Printable.PAGE_EXISTS
     */
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex >= mPages.size()){
            return NO_SUCH_PAGE;
        }
        int savedPage = currentPage;
        currentPage = pageIndex;

        Graphics2D g2 = (Graphics2D)g;
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        paint(g2);
        currentPage = savedPage;

        return PAGE_EXISTS;

    }//end of print Method

    /**
     * getPreferredSize Method
     * @return Dimension for preferredSize
     */
    public Dimension getPreferredSize() { return preferredSize; }

    /**
     * getCurrentPage Method -
     * @return int representing the current page
     */
    public int getCurrentPage() { return currentPage; }

    /**
     * getNumberOfPages Method -
     * @return int representing the number of pages
     */
    public int getNumberOfPages() { return mPages.size(); }

    /**
     * nextPage Method - advances current page to the next page
     */
    public void nextPage() {
        if (currentPage < mPages.size() - 1) {
            currentPage++;
        }

        repaint();
    }//end of nextPage Method

    /**
     * previousPage - returns current page to the previous page
     */
    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
        }

        repaint();

    }//end of previousPage Method
}//end of FilePageRenderer Class
