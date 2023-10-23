package ler_xml;

import java.awt.Desktop;
import java.io.BufferedReader;    
import java.io.File;
import java.io.FileInputStream;    
import java.io.FilenameFilter;
import java.io.IOException;    
import java.io.InputStreamReader;    
import java.io.StringReader; 
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
    	        return name.endsWith("-procNfe.xml");   
    	    }  
    	};  
        String local = "C:\\Users\\Albertiano\\NF-e\\2019-07\\Autorizada";
    	File arquivos = new File(local);  
    	String[] nomeArquivos = arquivos.list(filtro);  
    	StringBuilder sb = new StringBuilder();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            for (String nomeArquivo : nomeArquivos) {	
		    Document doc = dBuilder.parse(nomeArquivo);
		
		System.out.println("Root do elemento: " + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("guest");
		
		System.out.println("----------------------------");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			//System.out.println("\nElemento corrente :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				System.out.println("Id : " + (temp+1));
				System.out.println("Primeiro nome.: " + eElement.getElementsByTagName("fname").item(0).getTextContent());
				System.out.println("Segundo nome..: " + eElement.getElementsByTagName("lname").item(0).getTextContent());
			}
		}
    }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        
/*
            TNfeProc nfe = getTNfeProc(local + "/" + nomeArquivo);
            String CFOP = nfe.getNFe().getInfNFe().getDet().get(0).getProd().getCFOP();
            StringBuilder sbAcao = new StringBuilder();
            sbAcao
                    .append(" | NF-e: ")
                    .append(nfe.getNFe().getInfNFe().getIde().getNNF())
                    .append(" | " + "CFOP: ")
                    .append(setLength(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getCFOP(), 4))
                    .append(" | ")
                    .append(setLength(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getXProd(), 40))
                    .append(" | ")
                    .append(setLength(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getQTrib(), 6))
                    .append(" | Preço: ")
                    .append(setLength(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVUnTrib(), 4))
                    .append(" | Valor: ")
                    .append(setLength(nfe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF(), 8));
            
            //System.out.println(sbAcao);
            sb.append("\r\n");
            sb.append(sbAcao);
            switch (CFOP) {
                case "5401":
                    CFOP5401Qtd += Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getQTrib());
                    faturamento += Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVProd());
                    break;
                case "5904":
                    CFOP5904Qtd += Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getQTrib());
                    faturamento += Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVProd());
                    break;
                case "5116":
                    CFOP5116 +=Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getQTrib());
                    faturamento+=Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVProd());
                    break;
                case "5920":
                    faturamento+=Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVProd());
                    break;
                case "5921":
                    faturamento+=Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVProd());
                    break;
                case "1921":
                    faturamento+=Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVProd());
                    break;
                default:
                	if(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getCProd().equalsIgnoreCase("1")) {
                		CFOPOutros +=Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getQTrib());
                	}
                	System.out.println(sbAcao);
                	
                    faturamento+=Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVProd());
                    break;
            }
        }
              sb.append("\r\n")
                 .append("\r\n")
                .append("CFOP 5401: ")
                .append(CFOP5401Qtd)
                .append(" Aguas")
                .append("\r\n")
                .append("CFOP Outros: ")
                .append(CFOPOutros)
                .append(" Aguas")
                .append("\r\n")
                .append("CFOP 5904: ")
                .append(CFOP5904Qtd)
                .append(" Aguas")
                .append("\r\n")
                .append("CFOP 5116: ")
                .append(CFOP5116)
                .append(" Aguas")
                .append("\r\n")
                .append("\r\n")
                .append("Faturamento: R$ ")
                .append(faturamento);
        try {
            Files.write(Paths.get(local+"/Resultado.txt"), sb.toString().getBytes("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(lerXML.class.getName()).log(Level.SEVERE, null, ex);
        }    	
    	System.out.println();
    	System.out.println("CFOP 5401: "+CFOP5401Qtd+" Aguas");
    	System.out.println("CFOP 5904: "+CFOP5904Qtd+" Aguas");
    	System.out.println("CFOP 5116: "+CFOP5116+" Aguas");
    	System.out.println();
    	System.out.println("Faturamento: R$ "
    	+faturamento);
        Desktop desktop = Desktop.getDesktop();    
        try {
            desktop.open(new File(local+"/Resultado.txt"));
        } catch (IOException ex) {
            Logger.getLogger(lerXML.class.getName()).log(Level.SEVERE, null, ex);
        } */
    }   
      
    private String lerXMLFile(String fileXML) throws IOException {    
        String linha = "";    
        StringBuilder xml = new StringBuilder();    
    
        BufferedReader in = new BufferedReader(new InputStreamReader(    
                new FileInputStream(fileXML)));    
        while ((linha = in.readLine()) != null) {    
            xml.append(linha);    
        }    
        in.close();    
    
        return xml.toString();    
    }          
	
	private static void lerXML() throws Exception{
		File fXmlFile = new File("Agenda.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		System.out.println("Root do elemento: " + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("guest");
		
		System.out.println("----------------------------");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			//System.out.println("\nElemento corrente :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				System.out.println("Id : " + (temp+1));
				System.out.println("Primeiro nome.: " + eElement.getElementsByTagName("fname").item(0).getTextContent());
				System.out.println("Segundo nome..: " + eElement.getElementsByTagName("lname").item(0).getTextContent());
			}
		}
	}
} 