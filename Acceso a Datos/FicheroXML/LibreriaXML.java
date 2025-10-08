package ada;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class LibreriaXML {
    public static void main(String[] args) {
        try {
            // Ejercicio 1
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element libreria = doc.createElement("libreria");
            doc.appendChild(libreria);

            Element nombre = doc.createElement("nombre");
            nombre.appendChild(doc.createTextNode("Librería Pepe"));
            libreria.appendChild(nombre);

            Element libros = doc.createElement("libros");
            libreria.appendChild(libros);

            agregarLibro(doc, libros, "1234567890", "Don Quijote de la Mancha", "Miguel de Cervantes");
            agregarLibro(doc, libros, "2345678901", "Lazarillo de Tormes", "Anónimo");
            agregarLibro(doc, libros, "45678910123", "La vida es sueño", "Pedro Calderón de la Barca");

            // Ejercicio 2
            System.out.println("Contenido inicial ");
            mostrarContenido(doc.getDocumentElement(), "");

            // Ejercicio 3
            guardarDocumento(doc, "libreria.xml");

            // Ejercicio 4
            agregarLibro(doc, libros, "8901234567", "100 años de soledad", "Gabriel García Márquez");

            // Ejercicio 5
            Element direccion = doc.createElement("direccion");
            direccion.appendChild(doc.createTextNode("C/ Amiel 12"));
            libreria.insertBefore(direccion, libros);

            // Ejercicio 6
            NodeList listaLibros = doc.getElementsByTagName("libro");
            if (listaLibros.getLength() > 1) {
                Node segundoLibro = listaLibros.item(1);
                segundoLibro.getParentNode().removeChild(segundoLibro);
            }

            // Ejercicio 7
            direccion.setTextContent("C/ Amiel 26");

            // Ejercicio 8
            listaLibros = doc.getElementsByTagName("libro");
            for (int i = 0; i < listaLibros.getLength(); i++) {
                Element libro = (Element) listaLibros.item(i);
                libro.setAttribute("estado", "nuevo");
            }

            // Ejercicio 9
            if (listaLibros.getLength() >= 1)
                ((Element) listaLibros.item(0)).removeAttribute("estado");
            if (listaLibros.getLength() >= 3)
                ((Element) listaLibros.item(2)).removeAttribute("estado");

            System.out.println("\n Contenido final ");
            mostrarContenido(doc.getDocumentElement(), "");

            guardarDocumento(doc, "libreria_modificada.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void agregarLibro(Document doc, Element libros, String isbn, String titulo, String autor) {
        Element libro = doc.createElement("libro");
        libro.setAttribute("isbn", isbn);

        Element etTitulo = doc.createElement("titulo");
        etTitulo.appendChild(doc.createTextNode(titulo));
        libro.appendChild(etTitulo);

        Element etAutor = doc.createElement("autor");
        etAutor.appendChild(doc.createTextNode(autor));
        libro.appendChild(etAutor);

        libros.appendChild(libro);
    }

    private static void mostrarContenido(Node nodo, String tab) {
        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
            System.out.print(tab + "<" + nodo.getNodeName());
            NamedNodeMap attrs = nodo.getAttributes();
            for (int i = 0; i < attrs.getLength(); i++) {
                Node a = attrs.item(i);
                System.out.print(" " + a.getNodeName() + "=\"" + a.getNodeValue() + "\"");
            }
            System.out.println(">");
        } else if (nodo.getNodeType() == Node.TEXT_NODE) {
            String texto = nodo.getNodeValue().trim();
            if (!texto.isEmpty())
                System.out.println(tab + texto);
        }

        NodeList hijos = nodo.getChildNodes();
        for (int i = 0; i < hijos.getLength(); i++) {
            mostrarContenido(hijos.item(i), tab + "  ");
        }

        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(tab + "</" + nodo.getNodeName() + ">");
        }
    }

    private static void guardarDocumento(Document doc, String nombreArchivo)
            throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(nombreArchivo));
        transformer.transform(source, result);
    }
}