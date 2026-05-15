import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML RSS (version 2.0) feed from a given URL into the
 * corresponding HTML output file.
 *
 * @author Zhuangzhuang He
 *
 */
public final class RSSReader {

    private RSSReader() {
    }

    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: xml is a tag";

        int index = -1;
        for (int i = 0; i < xml.numberOfChildren() && index == -1; i++) {
            if (xml.child(i).isTag() && xml.child(i).label().equals(tag)) {
                index = i;
            }
        }
        return index;
    }

    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals("channel");

        // Extract Title
        String title = "Empty Title";
        int titleIdx = getChildElement(channel, "title");
        if (titleIdx != -1 && channel.child(titleIdx).numberOfChildren() > 0) {
            title = channel.child(titleIdx).child(0).label();
        }

        // Extract Link
        int linkIdx = getChildElement(channel, "link");
        String link = channel.child(linkIdx).child(0).label();

        // Extract Description
        String description = "No description";
        int descIdx = getChildElement(channel, "description");
        if (descIdx != -1 && channel.child(descIdx).numberOfChildren() > 0) {
            description = channel.child(descIdx).child(0).label();
        }

        out.println("<html><head><title>" + title + "</title></head><body>");
        out.println("<h1><a href=\"" + link + "\">" + title + "</a></h1>");
        out.println("<p>" + description + "</p>");
        out.println("<table border=\"1\">");
        out.println("<tr><th>Date</th><th>Source</th><th>News</th></tr>");
    }

    private static void outputFooter(SimpleWriter out) {
        out.println("</table></body></html>");
    }

    private static void processItem(XMLTree item, SimpleWriter out) {
        // 1. Handle Publication Date
        String date = "No date available";
        int dateIdx = getChildElement(item, "pubDate");
        if (dateIdx != -1) {
            date = item.child(dateIdx).child(0).label();
        }

        // 2. Handle Source
        String source = "No source available";
        int sourceIdx = getChildElement(item, "source");
        if (sourceIdx != -1) {
            String sourceUrl = item.child(sourceIdx).attributeValue("url");
            String sourceName = item.child(sourceIdx).child(0).label();
            source = "<a href=\"" + sourceUrl + "\">" + sourceName + "</a>";
        }

        // 3. Handle Title/Description (The core requirement)
        String displayTitle = "No title available";
        int tIdx = getChildElement(item, "title");
        int dIdx = getChildElement(item, "description");

        if (tIdx != -1 && item.child(tIdx).numberOfChildren() > 0) {
            displayTitle = item.child(tIdx).child(0).label();
        } else if (dIdx != -1 && item.child(dIdx).numberOfChildren() > 0) {
            displayTitle = item.child(dIdx).child(0).label();
        }

        // 4. Handle Link
        int linkIdx = getChildElement(item, "link");
        out.println("<tr><td>" + date + "</td><td>" + source + "</td>");
        if (linkIdx != -1) {
            String url = item.child(linkIdx).child(0).label();
            out.println("<td><a href=\"" + url + "\">" + displayTitle + "</a></td>");
        } else {
            out.println("<td>" + displayTitle + "</td>");
        }
        out.println("</tr>");
    }

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter RSS 2.0 feed URL: ");
        String url = in.nextLine();
        out.print("Enter output HTML file name: ");
        String fileName = in.nextLine();

        // Create XMLTree first to ensure the URL is valid
        XMLTree rss = new XMLTree1(url);

        if (rss.label().equals("rss") && rss.hasAttribute("version")
                && rss.attributeValue("version").equals("2.0")) {

            XMLTree channel = rss.child(getChildElement(rss, "channel"));
            SimpleWriter fileOut = new SimpleWriter1L(fileName);

            outputHeader(channel, fileOut);

            for (int i = 0; i < channel.numberOfChildren(); i++) {
                if (channel.child(i).isTag() && channel.child(i).label().equals("item")) {
                    processItem(channel.child(i), fileOut);
                }
            }

            outputFooter(fileOut);
            fileOut.close();
            java.io.File file = new java.io.File(fileName);
            out.println("文件绝对路径是: " + file.getAbsolutePath());
        } else {
            out.println("Error: Not a valid RSS 2.0 feed.");
        }

        in.close();
        out.close();
    }
}