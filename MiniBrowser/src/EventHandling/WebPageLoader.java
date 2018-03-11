/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventHandling;

/**
* *************************************************************************
*                                 IMPORTS
* *************************************************************************
*/
import UserInterface.Constants;
import UserInterface.MainFrame;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;

/**
 * This class is used to provide the BackEnd functionalities of the browser
 * such as Fetching the contents of the URL,parsing the contents,executing the
 * respective operations and displaying the contents if required.
 * @author gauravpunjabi
 */
public class WebPageLoader implements Constants{
    
    /**
     * *************************************************************************
     *                              CONSTRUCTOR
     * *************************************************************************
     */
    /**
     * This is the constructor that stores the reference of the the frame which 
     * displays the web content.
     * It also initializes the contents cursor on coordinate 10,10.
     * @param mainFrame
     */
    public WebPageLoader(final MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.x = this.y = 10;
    }

    /**
     * This method is used to accept the URL that needs to be parsed,understood
     * and displayed on the screen.
     * Initially it verifies if the given path specifies the protocol or not.
     * If not then it adds the default protocol(HTTP:80) to it.
     * It Then uses HttpURLConnection Class to read contents of the web-page .i.e
     * the source code and then passes it to parseCode method which further parses
     * the content of the web-page.
     * @param path URL in the form of string.
     */
    public void displayPage(String path) {
        mainFrame.getDisplayPanel().removeAll();
        this.x = this.y = 10;
        this.width = 1366;
        this.height = 802;
        if("<h1>Hello World</h1>".matches("<([\\w\\d]*?)>([\\w\\d\\s<>//.\'\"]*?)</\\1?>"))
            System.out.println(true);
        if(!path.matches("^www.[\\w*\\d*\\.#/]*") && path.matches("[\\w*\\d*\\.#/]*") && !path.matches("^http://www.[\\w*\\d*\\.#/]*")){
            path = "http://www." + path;
        }
        else if(!path.matches("^http://www.[\\w*\\d*\\.#/]*") && path.matches("^www.[\\w*\\d*\\./#]*")) {
            path = "http://" + path;
        }
        mainFrame.getAddressTextField().setText(path);
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String temp,content = "";
            while((temp = reader.readLine()) != null) 
                content += temp + "\n";
            System.out.println("content = " + content);
            parseCode(content);
        } catch(ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     * This method is used to parse the given lines of code,understands them and
     * the performs the respective task.
     * It starts by declaring a general pattern that is suitable for each and 
     * every tag present in HTML.It then verifies the tag-name to check if it is 
     * a valid tag or not.If it is a valid tag then it reads the data provided
     * within the tag and executes them according to the respective tag.
     * Some of the operations of different HTML tags are specified below.
     *      -1 "div" 
     *              -If this tag is encountered then the parser inputs the data 
     *               then converts it into a token(here string) and resolves this
     *               token separately by passing the token to itself in recursion
     *               which further resolves this token.
     *      -2 heading tags(e.g "h1","h2",etc)
     *              -If this tag is encountered then the parser inputs the data
     *               then creates a label set the text of the label as input data
     *               and sets the style of the label according to the tag it is
     *               present in(For e.g. - for "h1" tag it sets the font with 
     *               the size of 48 pixels).
     *      -3 paragraph tag ("p")
     *              -If this tag is encountered the the parser inputs the data
     *               then displays the whole data as a paragraph.
     * @param code 
     */
    private void parseCode(final String code) {
        System.out.println("Entered ParseCode");
        Matcher generalMatcher = Pattern.compile("<([\\w\\d*]*?)>([\\w\\d\\s<>\\.\"\\:\\=]*?)</\\1?>").matcher(code);
        while(generalMatcher.find()) {
            System.out.println(generalMatcher.group(0));
            System.out.println("Pattern matched " + generalMatcher.group(1));
            if(generalMatcher.group(1).equalsIgnoreCase(DIV_TAG)) {
                System.out.println("Div tag matched ");
                parseCode(generalMatcher.group(2));
            }
            else if(generalMatcher.group(1).equalsIgnoreCase(PARAGRAPH_TAG)) {
                printParagraph(generalMatcher.group(2));
            }
            else if(generalMatcher.group(1).equalsIgnoreCase("h1")) {
                printHeading(H1_FONT,generalMatcher.group(2));
            }
            else if(generalMatcher.group(1).equalsIgnoreCase("h2")) {
                printHeading(H2_FONT,generalMatcher.group(2));
            }
            else if(generalMatcher.group(1).equalsIgnoreCase("h3")) {
                printHeading(H3_FONT,generalMatcher.group(2));
            }
            else if(generalMatcher.group(1).equalsIgnoreCase("h4")) {
                printHeading(H4_FONT,generalMatcher.group(2));
            }
            else if(generalMatcher.group(1).equalsIgnoreCase("h5")) {
                printHeading(H5_FONT,generalMatcher.group(2));
            }
            else if(generalMatcher.group(1).equalsIgnoreCase("h6")) {
                printHeading(H6_FONT,generalMatcher.group(2));
            }
        }
    } 
    
    
    /**
     * *************************************************************************
     *                        METHODS_TO_DISPLAY_CONTENT
     * *************************************************************************
     */
    /**
     * This method is used to display the given content string in the form of 
     * paragraph on the displayPanel on the mainFrame window.
     * @param content 
     */
    private void printParagraph(final String content) {
        for(String line : content.split("\n")) {
            JLabel jLabel = new JLabel(line);
            jLabel.setFont(PARAGRAPH_FONT);
            int width = (PARAGRAPH_FONT.getSize()*content.length()) + 10;
            int height = PARAGRAPH_FONT.getSize();
            jLabel.setBounds(x,y,width,height);
            mainFrame.getDisplayPanel().add(jLabel);
            this.height += height + 5;
            mainFrame.getDisplayPanel().setPreferredSize(new Dimension(this.width,this.height));
            mainFrame.getDisplayPanel().revalidate();
            y += height + 5;
        }
        y+= 5;
    }
    
    /**
     * This method is used to display the Heading on the displayPlanel on the 
     * mainFrame window with the given style of the heading the form of "Font".
     * @param font
     * @param content 
     */
    private void printHeading(final Font font,
                              final String content) {
        JLabel jLabel = new JLabel(content);
        jLabel.setFont(font);
        int width = (font.getSize() * content.length()) + 15;
        int height =font.getSize() + 3;
        jLabel.setBounds(x,y,width,height);
        mainFrame.getDisplayPanel().add(jLabel);
        this.height += height + 10;
        mainFrame.getDisplayPanel().setPreferredSize(new Dimension(this.width,this.height));
        mainFrame.getDisplayPanel().revalidate();
        y += height + 10;
    }
    
    /**
     * *************************************************************************
     *                        VARIABLE_DECLARATION_STARTS
     * *************************************************************************
     */
    private Stack<Integer> tokens;
    private int x,y;
    private int width,height;
    private MainFrame mainFrame;
    /**
     * *************************************************************************
     *                        VARIABLE_DECLARATION_ENDS
     * *************************************************************************
     */
}
