package ler_xml;

import java.awt.Desktop;
import java.io.BufferedReader;    
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;    
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale.Category;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;   
    
public class lerXML {    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new lerXML().lerNota();
    }
    
    private String setLength(String s, int l) {  
    if (s == null)  
        s = new String();  
  
    if (s.length() == l) {  
        return s;  
    } else if (s.length() < l) {  
        char[] esp = new char[l - s.length()];  
        Arrays.fill(esp, ' ');  
        return s.concat(String.valueOf(esp));  
    } else {  
        return s.substring(0, l);  
    }  
}
    
    
    public void lerNota(){
    	double CFOP5401Qtd=0;
    	double CFOP5904Qtd=0;
    	double CFOP5116 = 0;
    	double CFOPOutros = 0;
    	double faturamento = 0;
    	
    	FilenameFilter filtro = new FilenameFilter() {
    	    public boolean accept(File dir, String name) {  
    	        return name.endsWith("25230702930074000152550010000991761547198790.xml");   
    	    }  
    	};  
        String local = "C:\\Users\\Albertiano\\Desktop\\NF-e\\2023-07NFe";
    	File arquivos = new File(local);  
    	String[] nomeArquivos = arquivos.list(filtro);  
    	StringBuilder sb = new StringBuilder();
        try {
            for (String nomeArquivo : nomeArquivos) {	
            String filePath = local + "/" + nomeArquivo;
    
            Reader fileReader = new FileReader(filePath);

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLStreamReader reader = null;
            try {
                reader = inputFactory.createXMLStreamReader(fileReader);
                readDocument(reader);
            } finally {
                if (reader != null) {
                    reader.close();
                }
            } 		
    }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   

    private NotaFiscal readDocument(XMLStreamReader reader) throws XMLStreamException {
        NotaFiscal nota = new NotaFiscal();
        while (reader.hasNext()) {            
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getLocalName();
                    if (elementName.equals("ide"))
                        nota  = readDET(reader, nota);
                    else if (elementName.equals("det")){
                        nota  = readIDE(reader, nota);
                    } else 
                        System.out.println(elementName);
                    break;
                case XMLStreamReader.END_ELEMENT:
                    //System.out.println(nota);
                    return nota;
            }
        }
        throw new XMLStreamException("Premature end of file");
    }
 
    private NotaFiscal readIDE(XMLStreamReader reader, NotaFiscal nota) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getLocalName();
                    String elementValue = readCharacters(reader);
                    if (elementName.equals("nNF")){
                        nota.setNumero(Integer.valueOf(elementValue));
                    } 
                    break;
                case XMLStreamReader.END_ELEMENT:
                    return nota;
            }
        }
        throw new XMLStreamException("Premature end of file");
    }

    private NotaFiscal readDET(XMLStreamReader reader, NotaFiscal nota) throws XMLStreamException {
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getLocalName();
                    String elementValue = readCharacters(reader);
                    System.out.println(elementName);
                    System.out.println(elementValue);

                    break;
                case XMLStreamReader.END_ELEMENT:
                    return nota;
            }
        }
        throw new XMLStreamException("Premature end of file");
    }
     
    private String readCharacters(XMLStreamReader reader) throws XMLStreamException {
        StringBuilder result = new StringBuilder();
        while (reader.hasNext()) {
            int eventType = reader.next();
            switch (eventType) {
                case XMLStreamReader.CHARACTERS:
                case XMLStreamReader.CDATA:
                    result.append(reader.getText());
                    break;
                case XMLStreamReader.END_ELEMENT:
                    return result.toString();
            }
        }
        throw new XMLStreamException("Premature end of file");
    }
} 