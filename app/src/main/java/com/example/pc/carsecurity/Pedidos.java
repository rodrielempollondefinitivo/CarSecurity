package com.example.pc.carsecurity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Pedidos extends AppCompatActivity {
private FloatingActionButton bsiguiente;
private FloatingActionButton bvolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bvolver = (FloatingActionButton) findViewById(R.id.btnvolver);
        bvolver.setImageResource(R.drawable.icono_atras);
        bsiguiente=(FloatingActionButton) findViewById(R.id.btnsiguiente);
        bsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento=new Intent(Pedidos.this, PedidosCatalogo.class);
                startActivity(intento);
            }
        });

        try {
            rellenarSpinnerXML();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void volver(View v){
        finish();
    }

    public void rellenarSpinnerXML() throws ParserConfigurationException, IOException, SAXException {
        ArrayAdapter<String> adapter;
        List<String> lista_partners = new ArrayList<String>();
        String xmlfile = "partners.xml";

        File file = new File(this.getExternalFilesDir(null), xmlfile); //objeto File con la ruta del xml
        if(file.exists()) {
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

        Spinner spn = (Spinner) findViewById(R.id.spPartners);
        String[] myArray = new String[lista_partners.size()];  //se crea el array para rellenar el listview con el nombre del os partner
        lista_partners.toArray(myArray);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArray);
        spn.setAdapter(adapter);

        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
                */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
    }


}
