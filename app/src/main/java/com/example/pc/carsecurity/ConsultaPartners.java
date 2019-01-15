package com.example.pc.carsecurity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.util.*;

public class ConsultaPartners extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    String xmlfile = "partners.xml"; //nombre del xml
    private ListView listav;
    ArrayAdapter<String> adapter;
    List<String> lista_partners = new ArrayList<String>();

    public void refrescarPagina() {
        listav = (ListView) findViewById(R.id.lv_partners);

        try {
            cargarXML(); //llamando a este metodo, cuando se carga la clase se cargan los datos del XML
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        String[] myArray = new String[lista_partners.size()];  //se crea el array para rellenar el listview con el nombre del os partner
        lista_partners.toArray(myArray);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArray);
        listav.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_partners);

        refrescarPagina();
        listav.setOnItemLongClickListener(this);
    }

    public void cerrarClase(View v) {
        finish();
    } //se cierra la clase con el botón de volver

    public void cargarXML() throws ParserConfigurationException, IOException, SAXException { //se carg el XML y su información para rellenar el listview
        File file = new File(this.getExternalFilesDir(null), xmlfile); //objeto File con la ruta del xml
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(file);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("Partner");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                lista_partners.add(eElement.getElementsByTagName("Nombre")  //aquí es donde se rellena el array en base a los nombres de los partners
                        .item(0).getTextContent());
            }
        }

    }

    //elimina el partner seleccionado en base a la posicion que se pasa desde el listview (que es la misma del xml)
    private void eliminar(int posicion) {
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
        value.removeChild(partner_nodo);

        document.normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
    }

    /*
    En esta parte del código se utiliza un click listener que cuando mantienes pulsado en una de las opciones de las
    listview te hace aparecer opciones
     */
    @Override
    public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long id) {
        final int posicion = i;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] items = new CharSequence[3];
        items[0] = "Información";
        items[1] = "Eliminar";
        items[2] = "Cancelar";
        builder.setTitle("Seleccione una opción")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            //ver actividad eventos
                            Intent intent = new Intent(getApplication(), InformacionPartners.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("pos", posicion);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        } else if (i == 1) {
                            eliminar(posicion);
                            finish();
                        }

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }
}