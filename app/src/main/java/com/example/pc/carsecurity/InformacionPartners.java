package com.example.pc.carsecurity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class InformacionPartners extends AppCompatActivity {
    String xmlfile = "partners.xml"; //nombre del xml

    public void retornarClase(View v){
        finish();
    } //volver al listview anterior

    //al iniciar la clase, se cargan los datos del XML del partner especifico pasado mediante su posicion, y
    //se ponen en los textview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_partners);

        Bundle extras = getIntent().getExtras();
        int posicion = extras.getInt("pos");

        TextView tvnombre = (TextView) findViewById(R.id.txtNombre);
        TextView tvdireccion = (TextView) findViewById(R.id.txtDireccion);
        TextView tvtelefono = (TextView) findViewById(R.id.txtTelefono);
        TextView tvemail = (TextView) findViewById(R.id.txtEmail);

        File file = new File(this.getExternalFilesDir(null), xmlfile); //objeto File con la ruta del xml
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            document = dbf.newDocumentBuilder().parse(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        NodeList empresa = document.getElementsByTagName("Empresa");
        Node mainNode = empresa.item(0);
        Element firstElement = (Element) mainNode;
        NodeList partners = firstElement.getElementsByTagName("Partners");
        Element value = (Element) partners.item(0);
        Node partner_nodo = value.getElementsByTagName("Partner").item(posicion);
        NodeList partner = partner_nodo.getChildNodes();


        for (int k = 0; k < partner.getLength(); ++k) {
            if (partner.item(k).getNodeName().toString().equalsIgnoreCase("Nombre")) {
                tvnombre.setText("NOMBRE: " + partner.item(k).getTextContent());
            } else if (partner.item(k).getNodeName().toString().equalsIgnoreCase("Direccion")) {
                tvdireccion.setText("DIRECCIÓN: " + partner.item(k).getTextContent());
            } else if (partner.item(k).getNodeName().toString().equalsIgnoreCase("Telefono")) {
                tvtelefono.setText("TELÉFONO: " + partner.item(k).getTextContent());
            } else if (partner.item(k).getNodeName().toString().equalsIgnoreCase("Email")) {
                tvemail.setText("EMAIL: " + partner.item(k).getTextContent());
            }
        }
    }
}
