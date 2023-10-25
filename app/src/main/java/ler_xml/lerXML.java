package ler_xml;

import java.awt.Desktop;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class lerXML {

    public static void main(String[] args) {
        new lerXML().lerNota();
    }

    public void lerNota() {
        FilenameFilter filtro = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        };
        String local = "C:\\Users\\Albertiano\\Desktop\\NF-e\\2023-09NFe";
        File arquivos = new File(local);
        String[] nomeArquivos = arquivos.list(filtro);
        List<String> filesName = Arrays.asList(nomeArquivos);
        List<NotaFiscal> notas = new ArrayList<NotaFiscal>();
        filesName.stream().forEach(xml -> addFileToList(local.concat("/").concat(xml), notas));
        StringBuilder sb = new StringBuilder();
        List<Item> items = new ArrayList<Item>();
        notas.stream().forEach(n -> {
            items.addAll(n.getItems());
            sb.append(n);
            sb.append("\r\n");
        });

        Collection<List<Item>> results = items.stream()
                .collect(Collectors.groupingBy(i -> i.cfop))
                .values();

        results.forEach(c -> {
            BigDecimal sum = c.stream()
                    .map(x -> x.getQuantidade())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            sb.append("\r\n");
            sb.append("CFOP: ");
            sb.append(c.get(0).getCfop());
            sb.append(" = ");
            sb.append(sum);
            sb.append("\r\n");
            System.out.println(sb);
        });

        try {
            Files.write(Paths.get(local + "/Resultado.txt"), sb.toString().getBytes("UTF-8"));
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(local + "/Resultado.txt"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println(filesName.size());
        System.out.println(notas.size());
        System.out.println(filesName.size() == notas.size());
    }

    private void addFileToList(String filePath, List<NotaFiscal> notas) {
        NotaFiscal nota = new NotaFiscal();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            var nodeIDE = document.getDocumentElement().getElementsByTagName("ide").item(0);
            var nodeDET = document.getDocumentElement().getElementsByTagName("det");
            nota = readIDE(nodeIDE, nota);
            for (int i = 0; i < nodeDET.getLength(); i++) {
                NodeList nodes = nodeDET.item(i).getChildNodes();
                for (int iDET = 0; iDET < nodes.getLength(); iDET++) {
                    Node node = nodes.item(iDET);
                    if (node.getNodeName() == "prod")
                        nota = readDET(node, nota);
                }
            }
            notas.add(nota);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private NotaFiscal readIDE(Node nodeIDE, NotaFiscal nota) {
        NodeList nodeList = nodeIDE.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            switch (node.getNodeType()) {
                case Node.ELEMENT_NODE:
                    Element elem = (Element) node;
                    if (elem.getTagName() == "nNF")
                        nota.setNumero(Integer.valueOf(elem.getTextContent()));
                    if (elem.getTagName() == "dhEmi")
                        nota.setDate(elem.getTextContent());
                    break;
                default:
                    System.out.println("readIDE" + node);
            }
        }
        return nota;
    }

    private NotaFiscal readDET(Node nodeDET, NotaFiscal nota) {
        NodeList nodeList = nodeDET.getChildNodes();
        Item item = new Item();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            switch (node.getNodeType()) {
                case Node.ELEMENT_NODE:
                    Element elem = (Element) node;
                    if (elem.getTagName() == "CFOP")
                        item.setCfop(elem.getTextContent());
                    if (elem.getTagName() == "xProd")
                        item.setDescProduto(elem.getTextContent());
                    if (elem.getTagName() == "qCom")
                        item.setQuantidade(new BigDecimal(elem.getTextContent()));
                    if (elem.getTagName() == "vUnCom")
                        item.setPreco(new BigDecimal(elem.getTextContent()));
                    if (elem.getTagName() == "vProd")
                        item.setSubtotal(new BigDecimal(elem.getTextContent()));
                    break;
                default:
                    System.out.println("readDET" + node);
            }
        }
        nota.getItems().add(item);
        return nota;
    }
}