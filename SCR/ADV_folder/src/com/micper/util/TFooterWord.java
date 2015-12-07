package com.micper.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.hwpf.model.TextPiece;
import org.apache.poi.hwpf.usermodel.HeaderStories;
/**
 * An instance of this class can be used to replace 'placeholders' with
 * text within a Word document. Any and all occurrences found within paragraphs
 * of text contained either within the body of the document or the cells of a
 * table will be replaced.
 * 
 * Currently, the replacement takes no regard of any formatting applied to the
 * original text. Instead, it will simply replace the 'placeholder' with text
 * formatted in accordance with the 'Normal' style.
 * 
 * @author AA
 * 
 * @version 1.00 8th December 2008.
 */
public class TFooterWord {
    
    private File inputFile = null;
    private HWPFDocument document = null;
    private HashMap replacementText = null;
    private Set keys = null;
    XWPFHeaderFooterPolicy hfPolicy = null;  
        
    /**
     * Create a new instance of the InsertText class using the following parameters;
     * 
     * @param filename An instance of the String class encapsulating the path
     *        path to and name of the MS Word file that is to be processed. No
     *        checks are made to ensure that the file is accessible or even
     *        of the correct data type.
     * @param replacementText An instance of the HashMap class that contains
     *        one or more key value pairs. Each pair consists of two Strings
     *        the first encapsulates the placeholder and the second the
     *        text that should replace it.
     * 
     * @throws NullPointerException if a null value is passed to either parameter.
     * @throws IllegalArgumentException if the vallue passed to either parameter is empty.
     */
    public TFooterWord(String filename, HashMap replacementText, String filenameTarget) 
      throws NullPointerException, IllegalArgumentException {
        
        // Not strictly necessary as a similar exception will be thrown on
        // instantiation of the File object later. Still like to test the parameters though.
        if(filename == null) {
            throw new NullPointerException("Null value passed to the filename parameter of the InsertText class constructor.");
        }
        if(replacementText == null) {
            throw new NullPointerException("Null value passed to the replacementText parameter of the InsertText class constructor.");
        }
        if(filename=="") {
            throw new IllegalArgumentException("An empty String was passed to the filename parameter of the InsertText class constructor.");
        }
        if(replacementText.isEmpty()) {
            throw new IllegalArgumentException("An empty HashMap was passed to the replacementText parameter of the InsertText class constructor.");
        }
        // Copy parameters to local variables. Get the set of keys backing the HashMap and open the file.
        this.replacementText = replacementText;
        this.keys = replacementText.keySet();
        //System.out.print(">>>>>>>>>>>>>>>>>> replacementText: "+replacementText);
        this.inputFile = new File(filename);
    }
    
    /**
     * Called to replace any named 'placeholders' with their accompanying text.
     */
    public void processFile() {
        Range range = null;
        
        Range rangeFoot = null;
        
        BufferedInputStream buffInputStream = null;
        BufferedOutputStream buffOutputStream = null;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        ParagraphText[] paraText = null;
        
        ParagraphText[] paraTextFoot = null;
        
        
        try {
            // Open the input file.
            fileInputStream = new FileInputStream(this.inputFile);
            buffInputStream = new BufferedInputStream(fileInputStream);
            this.document = new HWPFDocument(new POIFSFileSystem(buffInputStream));
            
            //System.out.print("-------------paraText----");
            // Step through the paragraph text.

            HeaderStories hd = new HeaderStories(this.document);            
            //System.out.print("FirstFooter::" + hd.getOddFooter());
                        
            rangeFoot = hd.getRange();                       
            
            int count=0;
            paraTextFoot = this.loadParagraphs(rangeFoot);
            //System.out.print("+++++++++++++paraTextFoot.length:"+paraTextFoot.length);
            for(int x = 0; x < paraTextFoot.length; x++) {            
            	                       	
            	for (Iterator iterator = this.keys.iterator(); iterator.hasNext();) {
            		String key = (String) iterator.next();
                    if(paraTextFoot[x].getUpdatedText().contains(key)) {
                    	//System.out.print(">>>>>>>>>>>>key:"+key+"--"+"x:"+x+" (String) this.replacementText.get(key):"+(String) this.replacementText.get(key));
                    	paraTextFoot[x].updateText(
                            this.replacePlaceholders(
								key, (String) this.replacementText.get(key),
								paraTextFoot[x].getUpdatedText()));
                    }
				}
            	               
                if(paraTextFoot[x].isUpdated()) {
                	count++;
                	//System.out.print("entro a reemplazar::" + count);
                	rangeFoot.getParagraph(paraTextFoot[x].getParagraphNumber()).replaceText(paraTextFoot[x].getRawText(), paraTextFoot[x].getUpdatedText(), 0);
                	                	
                }   
                
                fileOutputStream = new FileOutputStream(new File("C:\\Temp\\CopyTest.doc"),false);
                buffOutputStream = new BufferedOutputStream(fileOutputStream);
                this.document.write(buffOutputStream);

             }           
        }
        catch(IOException ioEx) {
            //System.out.print("Caught an: " + ioEx.getClass().getName());
            //System.out.print("Message: " + ioEx.getMessage());
            //System.out.print("StackTrace follows:");
            ioEx.printStackTrace(System.out);
        }
        finally {
            if(buffInputStream != null) {
                try {
                    buffInputStream.close();
                }
                catch(IOException ioEx) {
                    // I G N O R E //
                }
            }
            
            if(buffOutputStream != null) {
                try {
                    buffOutputStream.flush();
                    buffOutputStream.close();
                }
                catch(IOException ioEx) {
                    // I G N O R E //
                }
            }
        }
    }
    
    /**
     * Called to replace all occurrences of a placeholder.
     * 
     * @param key An instance of the String class that encapsulates the text of the placeholder.
     * @param value An instance of the String class that encapsulates the
     *        text that will be used to replace the placeholder.
     * @param text An instance of the String class that contains the contents
     *        of a paragraph read from a Word document.
     * 
     * @return An instance of the String class containing an updated version
     *         of the text originally recovered from the Word document; one
     *         where all occurrences of the placeholder have been replaced.
     */
    private String replacePlaceholders(String key, String value, String text) {
        int index = 0;
        // This is far from the most efficient way to parse the String as it
        // always starts from the beginning even after one or more substitutions
        // have been made. For now though it does work but will have obvious
        // limitations if the chunks of text are very large.
        while((index = text.indexOf(key)) >= 0) {
            text = text.substring(0, index) + value + text.substring(index + key.length());
        }
        //System.out.print(">>>>>>>>>>>>>>*text:+"+text+"*<<<<<<<<<<<<<<");
        return(text);
    }
    
    /**
     * Reads the contents of the document as a series of Paragraph objects. The
     * text is extracted from each and encapsulated into a instances of the ParagraphText calss.
     * 
     * It proved to be problematic to call the text() method on an instance
     * of the Paragraph class - sometimes, such a call would fail to return
     * all of the text the actual paragraph contained. So, it was necessary to
     * read all of the text into local variables so that it could be more
     * effectivley and successfully processed and this method was copied -
     * along with it's companion getTextFromPieces() from NickBurch's WordExtractor class.
     * 
     * @param range An instance of the org.apache.poi.hwpf.usermodel.Range class
     *        that encapsulates information about the Word document - it's
     *        sections, paragraphs, tables, pictures, etc.
     * 
     * @return An array of type ParagraphText. Each element will contain an
     *         instance of the ParagraphText class that encasulates information
     *         about a paragraph of text - the text itself, the number of the
     *         paragraph, whether the text has been modified since it was read, etc.
     */
    private ParagraphText[] loadParagraphs(Range range) {
        ParagraphText[] paraText = new ParagraphText[range.numParagraphs()];
        Paragraph paragraph = null;
        String readText = null;
        try{
        
            for(int i = 0; i < range.numParagraphs(); i++) {
            	
                paragraph = range.getParagraph(i);
                readText = paragraph.text();               
	                if(readText.endsWith("\n")) {
	                	//System.out.print(">>>>>>>>>>>>>>>>>>>>>readText:"+readText);
	                    //readText = readText + "\n";
	                }
	                paraText[i] = new ParagraphText(i, readText);
                                
            }
            
        }
        catch(Exception ex) {
            paraText[0] = this.getTextFromPieces();
        }
        return(paraText);
    }

    /**
     * Again, with thanks to Nick Burch, this method will reconstruct the 
     * document's text from a series of TextPieces.
     * 
     * @return An instance of the ParagraphText class that encapsulates all
     *         of the text recovered from the document and treats it as one
     *         very large - potentially - paragraph.
     */
    private ParagraphText getTextFromPieces() {
    	//System.out.print("--------------getTextFromPieces");
        TextPiece piece = null;
        StringBuffer buffer = new StringBuffer();
        String text = null;
        String encoding = "Cp1252";
        
       	Iterator textPieces = this.document.getTextTable().getTextPieces().iterator();
       	while (textPieces.hasNext()) {
            piece = (TextPiece)textPieces.next();
            if (piece.isUnicode()) {
                encoding = "UTF-16LE";
            }
            try {
                text = new String(piece.getRawBytes(), encoding);
       		buffer.append(text);
            } catch(UnsupportedEncodingException e) {
                throw new InternalError("Standard Encoding " + encoding + " not found, JVM broken");
            }
       	}
       	text = buffer.toString();
       	// Fix line endings (Note - won't get all of them
       	text = text.replaceAll("\r\r\r", "\r\n\r\n\r\n");
       	text = text.replaceAll("\r\r", "\r\n\r\n");
       	if(text.endsWith("\r")) {
       		text += "\n";
       	}
       	//System.out.print("+++++++++++++++text:"+text+"+++++++++");
      	return(new ParagraphText(0, text));
      }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            HashMap replacementText = new HashMap();
            replacementText.put("[iConsecReconocim]", "001");
            replacementText.put("[iHolograma]", "5577");           
            replacementText.put("[iNumSolicitud]", "7888");
            
            new TFooterWord("C:\\TEMP\\MYR\\AgenteNavPer_Moral97.doc", replacementText,"").processFile();
        }
        catch(Exception eEx) {
            //System.out.print("Caught an: " + eEx.getClass().getName());
            //System.out.print("Message: " + eEx.getMessage());
            //System.out.print("StackTrace follows:");
            eEx.printStackTrace(System.out);
        }
    }
}
