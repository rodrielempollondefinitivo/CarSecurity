package com.example.pc.carsecurity;


import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;
import android.content.Intent;

import android.widget.CalendarView;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import android.widget.ListView;


public class Partners extends AppCompatActivity {

    private Document document;
    EditText nombre;
    EditText direccion;
    EditText telefono;
    EditText email;
    String ultimos_datos[] = new String[4];
    boolean bPrimeraVez = true;


    public void salir(View v){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    String xmlPath = "partners.xml"; //ruta del archivo XML

    // con este metodo se añade un nodo (se modifica el XML) con informacion del partner (es decir, un <Partner>)
    public void añadirNodo() throws Exception, FileNotFoundException, IOException {
        File xmlFile = new File(this.getExternalFilesDir(null), xmlPath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            Node n = doc.getElementsByTagName("Partners").item(0);

            Element nuevo_partner = doc.createElement("Partner");
            n.insertBefore(nuevo_partner, n.getFirstChild());

            Element nuevo_nombre = doc.createElement("Nombre");
            nuevo_nombre.appendChild(doc.createTextNode(nombre.getText().toString()));
            nuevo_partner.appendChild(nuevo_nombre);

            Element nueva_direccion = doc.createElement("Direccion");
            nueva_direccion.appendChild(doc.createTextNode(direccion.getText().toString()));
            nuevo_partner.appendChild(nueva_direccion);

            Element nuevo_telefono = doc.createElement("Telefono");
            nuevo_telefono.appendChild(doc.createTextNode(telefono.getText().toString()));
            nuevo_partner.appendChild(nuevo_telefono);

            Element nuevo_email = doc.createElement("Email");
            nuevo_email.appendChild(doc.createTextNode(email.getText().toString()));
            nuevo_partner.appendChild(nuevo_email);

            doc.getDocumentElement().normalize();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(this.getExternalFilesDir(null), xmlPath));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); //sirve para intender al codigo XML

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException pce) {
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException tfe) {
            return;
        }

    }

    //se crea el xml que se rellena con la primera informacion dada
    public void generarDocument() {

        Element empresa = document.createElement("Empresa");
        document.appendChild(empresa);

        Element partners = document.createElement("Partners");
        empresa.appendChild(partners);

        Element partner = document.createElement("Partner");
        partners.appendChild(partner);

        Element nombreXML = document.createElement("Nombre");
        partner.appendChild(nombreXML);

        nombreXML.appendChild(document.createTextNode(nombre.getText().toString()));

        Element direccionXML = document.createElement("Direccion");
        partner.appendChild(direccionXML);

        direccionXML.appendChild(document.createTextNode(direccion.getText().toString()));

        Element telefonoXML = document.createElement("Telefono");
        partner.appendChild(telefonoXML);

        telefonoXML.appendChild(document.createTextNode(telefono.getText().toString()));

        Element emailXML = document.createElement("Email");
        partner.appendChild(emailXML);

        emailXML.appendChild(document.createTextNode(email.getText().toString()));
    }

    public void generarXML(String path) { //clase que genera el xml mediante codigo
        try {
            TransformerFactory generador_xml = TransformerFactory.newInstance();
            Transformer conversor = generador_xml.newTransformer();
            conversor.setOutputProperty(OutputKeys.INDENT, "yes");
            conversor.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            Source origen_documento = new DOMSource(document);
            File fichero_xml = new File(path);

            FileWriter fw = new FileWriter(fichero_xml);
            PrintWriter pw = new PrintWriter(fw);

            StreamResult resultado = new StreamResult(pw);

            conversor.transform(origen_documento, resultado);
        } catch (FileNotFoundException e) {
            System.out.println(e.getClass().getName());
        } catch (IOException e) {
            System.out.println(e.getClass().getName());
        } catch (TransformerException e) {
            System.out.println(e.getClass().getName());
        }
    }

    //aqui se llama para crear el XML en caso de que no exista
    public void crearXML(String path) throws IOException, TransformerException, ParserConfigurationException {
        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factoria.newDocumentBuilder();
        document = builder.newDocument();

        generarDocument();
        generarXML(path);
    }

    public void abrirArchivo(View v) throws Exception {
    /*
    Aquí selecciona los datos de los EDT para rellenar o modificar (Añadir nodos) el xml
     */
        boolean bContinuar = true;
        nombre = (EditText) findViewById(R.id.edtNombre);
        direccion = (EditText) findViewById(R.id.edtDireccion);
        telefono = (EditText) findViewById(R.id.etdTelefono);
        email = (EditText) findViewById(R.id.edtEmail);

        String nombre_introducido = nombre.getText().toString();
        String direccion_introducida = direccion.getText().toString();
        String telefono_introducido = telefono.getText().toString();
        String email_introducido = email.getText().toString();

        String path;

        if (nombre_introducido.equalsIgnoreCase("") || direccion_introducida.equalsIgnoreCase("") || telefono_introducido.equalsIgnoreCase("") || email_introducido.equalsIgnoreCase("")) {
            Toast.makeText(this, "Rellene todos los campos, por favor.", Toast.LENGTH_SHORT).show();
        } else {
            if (telefono.getText().toString().length() == 9) {
                if (bPrimeraVez == true) { //con esto compruebo que no se meten los mismos datos dos veces seguidas
                    ultimos_datos[0] = nombre_introducido;
                    ultimos_datos[1] = direccion_introducida;
                    ultimos_datos[2] = telefono_introducido;
                    ultimos_datos[3] = email_introducido;
                    bPrimeraVez = false;
                } else {

                    if (ultimos_datos[0].equalsIgnoreCase(nombre_introducido) && ultimos_datos[1].equalsIgnoreCase(direccion_introducida) && ultimos_datos[2].equalsIgnoreCase(telefono_introducido) && ultimos_datos[3].equalsIgnoreCase(email_introducido)) {
                        bContinuar = false;
                    } else {
                        ultimos_datos[0] = nombre_introducido;
                        ultimos_datos[1] = direccion_introducida;
                        ultimos_datos[2] = telefono_introducido;
                        ultimos_datos[3] = email_introducido;
                        bContinuar = true;
                    }
                }

                if (bContinuar == true) {
                    File f = new File(this.getExternalFilesDir(null), "partners.xml");
                    if (f.exists() == true) {
                        añadirNodo();
                        //aqui resetear textos una vez añadido el partner
                        nombre.getText().clear();
                        direccion.getText().clear();
                        telefono.getText().clear();
                        email.getText().clear();
                    } else {
                        f.createNewFile();
                        path = f.getPath();
                        crearXML(path);
                    }
                    Toast.makeText(this, "Partner dado de alta.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Ha introducido los mismos datos.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Número de teléfono erróneo.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void abrirConsulta(View v) {
        Intent i = new Intent(this, ConsultaPartners.class);
        startActivity(i);
    }
}

