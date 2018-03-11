/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import java.awt.Font;
import java.util.regex.Pattern;

/**
 *  This is the interface used by the WebPageLoader class it stores the styles 
 *  and signature of the different HTML tags.
 * @author gauravpunjabi
 */
public interface Constants {
    static final Font H1_FONT = new Font("Fira Sans",0,48);
    static final Font H2_FONT = new Font("Fira Sans",0,37);
    static final Font H3_FONT = new Font("Fira Sans",0,30);
    static final Font H4_FONT = new Font("Fira Sans",0,23);
    static final Font H5_FONT = new Font("Fira Sans",0,18);
    static final Font H6_FONT = new Font("Fira Sans",0,12);
    static final Font PARAGRAPH_FONT = new Font("Fira Sans",0,18);
    static final String DIV_TAG = "div";
    static final String PARAGRAPH_TAG = "p";
}
