package com.dhbw.studienarbeit.skiapp;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class VorhersageWetterFragment extends Fragment {

    public static String[] wIcons = new String[10];
    public static String[] temp=new String[10];
    public static String[] wR =new String[10];
    public static String[] wS =new String[10];
    public static String zeitSonnenaufgang, zeitSonnenuntergang, Stadtname, substr;

    public static SeekBar sbTage;
    public static TextView tvTage;

    public static double LATITUDE, LONGITUDE;
    public static double LAST_KNOWN_LNG, LAST_KNOWN_LAT;

    public AktuelleLocation aktLoc;
    public Intent intent;
    public LocationManager locMan;

    ArrayAdapter<String> mVorhersageAdapter;
    Integer anzTage = 7;

    Button bAktualisieren;
    ImageButton ibHoleStandortWetter;
    EditText etSucheStadt;
    ListView vorhersageListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Menue bekannt geben, dadurch kann unser Fragment Menue-Events verarbeiten
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] vorhersageArray = {
                "Hier",
                "steht",
                "hoffentlich",
                "gleich",
                "dein sonniger",
                "Wetterbericht",
                "für einen",
                "super genialen",
                "Skiausflug"
        };

        List<String> wochenVorhersage = new ArrayList<String>(Arrays.asList(vorhersageArray));

        locMan = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        aktLoc = new AktuelleLocation();
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, aktLoc);

        mVorhersageAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // Die aktuelle Umgebung (diese Activity)
                        R.layout.item_wettervorhersage, // ID der XML-Layout Datei
                        R.id.label, // ID des TextViews
                        wochenVorhersage); // Beispieldaten in einer ArrayList

        View rootView = inflater.inflate(R.layout.fragment_wetter, container, false);

        etSucheStadt = (EditText) rootView.findViewById(R.id.etWetterStadt);
        bAktualisieren = (Button) rootView.findViewById(R.id.bWetterAktualisieren);
        bAktualisieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Erzeugen einer Instanz von HoleWetterdatenTask und starten des asynchronen Tasks
                HoleWetterdatenTask holeDatenTask = new HoleWetterdatenTask();
                if (etSucheStadt.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Bitte Standort eintragen und aktualsieren", Toast.LENGTH_LONG).show();
                } else {
                    holeDatenTask.execute(etSucheStadt.getText().toString(), String.valueOf(anzTage));
                    // Den Benutzer informieren, dass neue Wetterdaten im Hintergrund abgefragt werden
                    Toast.makeText(getActivity(), "Wetterdaten für " + etSucheStadt.getText().toString() + " werden abgefragt!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ibHoleStandortWetter = (ImageButton) rootView.findViewById(R.id.ibHoleStandort);
        ibHoleStandortWetter.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                getGPS();
                if (LATITUDE != 0.0 && LONGITUDE != 0.0) {
                    LAST_KNOWN_LAT = LATITUDE;
                    LAST_KNOWN_LNG = LONGITUDE;
                }
                Geocoder geocoder = new Geocoder(getActivity(), Locale.GERMAN);
                try {
                    List<Address> addresses = geocoder.getFromLocation(LAST_KNOWN_LAT, LAST_KNOWN_LNG, 1);

                    if (addresses != null) {
                        Address returnedAddress = addresses.get(0);
                        Stadtname = returnedAddress.getAddressLine(1);
                        substr = Stadtname.substring(6, Stadtname.length());
                        StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                        for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                            strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                        }
                        etSucheStadt.setText(substr);
                        HoleWetterdatenTask holeDatenTask = new HoleWetterdatenTask();
                        holeDatenTask.execute(substr, String.valueOf(anzTage));
                    } else {
                        Toast.makeText(getActivity(), "Keine Addresse verfügbar", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Keine Addresse verfügbar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        vorhersageListView = (ListView) rootView.findViewById(R.id.listView_vorhersage);
        vorhersageListView.setAdapter(new WeatherDetailAdapter(
                getActivity(), vorhersageArray, vorhersageArray));
        vorhersageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {



                    String wetterstaerke = (String) wS[position];
                    String wetterrichtung = (String) wR[position];
                    String anzeigeicon = (String) wIcons[position];
                    String anzeigetemp = (String) temp[position];
                if(anzeigetemp != null) {
                    // Intent erzeugen und Starten der WetterdetailActivity mit Intent
                    Intent wetterDetailIntent = new Intent(getActivity(), WetterDetailActivity.class);
                    Bundle uebergabe = new Bundle();
                    uebergabe.putString("wStaerke", wetterstaerke);
                    uebergabe.putString("wRichtung", wetterrichtung);
                    uebergabe.putString("sAufgang", zeitSonnenaufgang);
                    uebergabe.putString("sUntergang", zeitSonnenuntergang);
                    uebergabe.putString("aIcon", anzeigeicon);
                    uebergabe.putString("aTemp", anzeigetemp);

                    wetterDetailIntent.putExtras(uebergabe);
                    startActivity(wetterDetailIntent);
                }else{
                    Toast.makeText(getActivity(),
                            "Es wurden noch eine Wetterdaten abgerufen",Toast.LENGTH_SHORT).show();
                }
            }
        });

        sbTage = (SeekBar) rootView.findViewById(R.id.seekBar);
        tvTage = (TextView) rootView.findViewById(R.id.tvAktTage);

        sbTage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                progress = progress + 4;
                tvTage.setText(String.valueOf(progress));
                anzTage = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //tvTage.setText(String.valueOf(seekBar.getMax()+4));
            }
        });
        return rootView;
    }


    public class AktuelleLocation implements LocationListener {
        private LocationManager locationManager;
        private String provider;


        @Override
        public void onLocationChanged(Location location) {
            LATITUDE = location.getLatitude();
            LONGITUDE = location.getLongitude();


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }


    }


    public void getGPS() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);


        Location l = null;

        for (int i = providers.size() - 1; i >= 0; i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null) break;
        }

        double[] gps = new double[2];
        if (l != null) {
            gps[0] = l.getLatitude();
            gps[1] = l.getLongitude();
        }

        LAST_KNOWN_LAT = gps[0];
        LAST_KNOWN_LNG = gps[1];

    }


    // Innere Klasse HoleWetterdatenTask führt den asynchronen Task auf eigenem Arbeitsthread aus
    public class HoleWetterdatenTask extends AsyncTask<String, Integer, String[]> {

        private final String LOG_TAG = HoleWetterdatenTask.class.getSimpleName();

        private String[] leseXmlWetterdatenAus(String xmlString) {

            Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xmlString));
                doc = db.parse(is);
            } catch (ParserConfigurationException e) {
                Log.e(LOG_TAG, "Error: " + e.getMessage());
                return null;
            } catch (SAXException e) {
                Log.e(LOG_TAG, "Error: " + e.getMessage());
                return null;
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error: " + e.getMessage());
                return null;
            }

            Element xmlWetterdaten = doc.getDocumentElement();

            // Datum für jeden Tag auslesen
            NodeList elemente = xmlWetterdaten.getElementsByTagName("time");
            String[] wetterdatenArray = new String[elemente.getLength()];
            Node element;
            NamedNodeMap attribute;

            for (int i = 0; i < elemente.getLength(); i++) {
                element = elemente.item(i);
                attribute = element.getAttributes();
                String datum = attribute.getNamedItem("day").getNodeValue();
                wetterdatenArray[i] = datum;
            }

            // Temperaturwerte für jeden Tag auslesen
            elemente = xmlWetterdaten.getElementsByTagName("temperature");
            for (int i = 0; i < elemente.getLength(); i++) {
                element = elemente.item(i);
                attribute = element.getAttributes();
                String temperaturTag = attribute.getNamedItem("day").getNodeValue();
                wetterdatenArray[i] = wetterdatenArray[i] + "   \nTag: " + temperaturTag + "°C";
                String temperaturNacht = attribute.getNamedItem("night").getNodeValue();
                wetterdatenArray[i] = wetterdatenArray[i] + "  \nNacht: " + temperaturNacht + "°C";
                Log.v(LOG_TAG, "XML Output:" + wetterdatenArray[i]);
            }

            //Windstaerke
            elemente = xmlWetterdaten.getElementsByTagName("windDirection");
            for (int i = 0; i < elemente.getLength(); i++) {
                element = elemente.item(i);
                attribute = element.getAttributes();
                wR[i]=attribute.getNamedItem("name").getNodeValue();
                Log.v(LOG_TAG, "XML Output:" + wR[i]);
            }
            //Windstaerke
            elemente = xmlWetterdaten.getElementsByTagName("windSpeed");
            for (int i = 0; i < elemente.getLength(); i++) {
                element = elemente.item(i);
                attribute = element.getAttributes();
                wS[i]=attribute.getNamedItem("mps").getNodeValue();
                Log.v(LOG_TAG, "XML Output:" + wS[i]);
            }

            //Symbol auslesen
            elemente = xmlWetterdaten.getElementsByTagName("symbol");
            for (int i = 0; i < elemente.getLength(); i++) {

                attribute = elemente.item(i).getAttributes();
                String Symbolinchen = attribute.getNamedItem("var").getNodeValue();
                wIcons[i] = Symbolinchen;
            }

            Log.v(LOG_TAG,"Dohanno sollte da newSchtring standa" +xmlString);

            // Sonnenauf- und Sonnenuntergang auslesen
            elemente = xmlWetterdaten.getElementsByTagName("sun");
            attribute = elemente.item(0).getAttributes();
            zeitSonnenaufgang = attribute.getNamedItem("rise").getNodeValue();
            zeitSonnenuntergang = attribute.getNamedItem("set").getNodeValue();
            Log.v(LOG_TAG,"XML Output:" + zeitSonnenaufgang + ", " + zeitSonnenuntergang);




            return wetterdatenArray;
        }

        @Override
        protected String[] doInBackground(String... strings) {

            if (strings.length == 0) { // Keine Eingangsparameter erhalten, Abbruch
                return null;
            }

            // Wir konstruieren die Anfrage-URL für openweathermap.org
            // Parameter sind auf openweathermap.org/api beschrieben

            final String URL_PARAMETER = "http://api.openweathermap.org/data/2.5/forecast/daily?";
            //final String URL_PARAMETER = "http://api.openweathermap.org/data/2.5/forecast/city?id=524901&APPID=8260a420267231f30b21b51d80b36674";
            final String ORT_PARAMETER = "q";
            final String DATENFORMAT_PARAMETER = "mode";
            final String EINHEIT_PARAMETER = "units";
            final String ANZAHL_TAGE_PARAMETER = "cnt";
            final String SPRACHE_PARAMETER = "lang";
            final String APIKEY_PRARAMETER = "APPID";

            String ort = strings[0];
            String datenformat = "xml";
            String einheit = "metric";
            int anzahlTage = Integer.parseInt(strings[1]);
            String sprache = "de";
            String apikey = "8260a420267231f30b21b51d80b36674";

            String anfrageString = URL_PARAMETER + ORT_PARAMETER + "=" + ort;
            anfrageString += "&" + DATENFORMAT_PARAMETER + "=" + datenformat;
            anfrageString += "&" + EINHEIT_PARAMETER + "=" + einheit;
            anfrageString += "&" + ANZAHL_TAGE_PARAMETER + "=" + anzahlTage;
            anfrageString += "&" + SPRACHE_PARAMETER + "=" + sprache;
            anfrageString += "&" + APIKEY_PRARAMETER + "=" + apikey;

            Log.v(LOG_TAG, "Zusammengesetzter Anfrage-String: " + anfrageString);

            // Die URL-Verbindung und der BufferedReader, werden im finally-Block geschlossen
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            // In diesen String speichern wir die Wetterdaten im XML-Format
            String wetterdatenXmlString = "";

            try {
                URL url = new URL(anfrageString);

                // Aufbau der Verbindung zu openweathermap.org
                httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                if (inputStream == null) { // Keine Wetterdaten-Stream erhalten, Abbruch
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    wetterdatenXmlString += line + "\n";
                }
                if (wetterdatenXmlString.length() == 0) { // Keinen Wetterdaten ausgelesen, Abbruch
                    return null;
                }
                //Log.v(LOG_TAG, "Wetterdaten XML-String: " + wetterdatenXmlString);
                publishProgress(1, 1);

            } catch (IOException e) { // Beim Holen der Daten trat ein Fehler auf, Abbruch
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }


            return leseXmlWetterdatenAus(wetterdatenXmlString);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            // Auf dem Bildschirm geben wir eine Statusmeldung aus, immer wenn
            // publishProgress(int...) in doInBackground(String...) aufgerufen wird
            Toast.makeText(getActivity(), values[0] + " von " + values[1] + " geladen", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(String[] strings) {
            for (int x=0; x<strings.length;x++){
                temp[x]=strings[x];
            }

            vorhersageListView.setAdapter(new WeatherDetailAdapter(getActivity(), strings, wIcons));
            // Hintergrundberechnungen sind jetzt beendet, darüber informieren wir den Benutzer
            Toast.makeText(getActivity(), "Wetterdaten vollständig geladen!", Toast.LENGTH_SHORT).show();
        }


    }
}
