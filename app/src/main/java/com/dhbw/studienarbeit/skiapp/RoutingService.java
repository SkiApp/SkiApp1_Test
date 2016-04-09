package com.dhbw.studienarbeit.skiapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;


public class RoutingService extends Fragment {
    public ArrayAdapter<String> RoutingAdapter;
    static String[] kdk = new String[30];
    static String[] Bilder = new String[30];
    static String[] Distanz = new String[30];
    static String[] PisLift = new String[30];
    static Integer Zahl;
    static Integer k;
    static String START;
    static String END;

    ListView RouteListView;
    public static String [] Lift={"DSB Stafelalpe Sekt. 1", "DSB Stafelalpe Sekt. 2", "Glatthorn", "Guggernülli", "Übungslift Faschina", "Skikindergarten", "Uga", "Hohes Licht", "Sunnegg", "Hasenbühel", "Oberdamüls", "Walisgaden", "Furka", "Elsenkopf", "Hohe Wacht", "Gratlift Sunnegg", "Ragaz", "Rossstelle", "Gipfelbahn", "Wildgunten", "Suttis", "Mellaubahn"};
    public static String[][] Karte = {
            {"skiroute",    "blau", "blau", "blau", "blau", "skiroute", "skiroute", "rot",  "rot",  "rot",  "skiroute", "skiroute", "blau", "blau", "skiroute", "blau", "rot",  "skiroute", "skiroute", "blau", "skiroute", "blau", "blau", "rot",  "rot",  "skiroute", "skiroute", "blau", "blau", "rot",  "blau", "blau", "blau", "rot",  "rot",  "rot",  "blau", "blau", "blau", "rot",  "rot",  "blau", "rot",  "rot",  "rot",  "blau", "blau", "rot",  "rot",  "rot",  "blau", "schwarz",  "schwarz",  "schwarz",  "rot",  "blau", "skiroute", "rot",  "rot",  "rot",  "blau", "blau", "blau", "blau", "rot",  "DSB Stafelalpe Sekt. 1",   "DSB Stafelalpe Sekt. 2",   "Glatthorn",    "Guggernülli",  "Übungslift Faschina",  "Skikindergarten",  "Uga",  "Hohes Licht",  "Sunnegg",  "Hasenbühel",   "Oberdamüls",   "Walisgaden",   "Furka",    "Elsenkopf",    "Hohe Wacht",   "Gratlift Sunnegg", "Ragaz",    "Rossstelle",   "Gipfelbahn",   "Wildgunten",   "Suttis",   "Mellaubahn"},
            {"1",           "1",    "1",    "1",    "1",    "1",        "2",        "2",    "2",    "2",    "2",        "3",        "3",    "3",    "3",        "4",    "4",    "4",        "4",        "5",    "5",        "5",    "6",    "6",    "6",    "6",        "7",        "7",    "8",    "9",    "10",   "10",   "11",   "12",   "13",   "13",   "14",   "14",   "15",   "16",   "21",   "22",   "23",   "24",   "25",   "26",   "28",   "29",   "29",   "29",   "30",   "32",       "33",       "34",       "35",   "1a",   "1a",       "2a",   "2b",   "3a",   "3a",   "5a",   "5a",   "5a",   "13",   "1a",                       "1b",                       "2",            "3",            "4",                    "5",                "1",    "2",            "3",        "4",            "5",            "6",            "7",        "8",            "9",            "10",               "14",       "16",           "17",           "18",           "19",       "20"},
            {"b",           "e",    "j",    "j",    "k",    "p",        "b",        "c",    "e",    "j",    "n",        "b",        "d",    "l",    "u",        "g",    "l",    "l",        "x",        "g",    "l",        "p",    "ai",   "p",    "p",    "t",        "ad",       "p",    "p",    "p",    "n",    "p",    "j",    "j",    "ac",    "t",   "ac",   "t",    "y",    "y",    "aa",   "aa",   "ac",   "aa",   "ad",   "am",   "am",   "ad",   "ad",   "al",   "al",   "af",       "af",       "af",       "ae",   "e",    "p",        "q",    "q",    "d",    "l",    "g",    "g",    "h",    "t",    "a",                        "b",                        "d",            "f",            "ah",                   "h",                "i",    "k",            "m",        "m",            "o",            "m",            "r",        "s",            "s",            "n",                "x",        "z",            "ab",           "ab",           "ae",       "ag"},
            {"d",           "d",    "i",    "k",    "i",    "m",        "a",        "d",    "d",    "m",    "m",        "a",        "f",    "k",    "m",        "f",    "m",    "m",        "m",        "f",    "m",        "o",    "ah",   "i",    "o",    "s",        "ae",       "o",    "r",    "r",    "r",    "r",    "s",    "s",    "s",    "s",    "s",    "s",    "x",    "x",    "z",    "z",    "ab",   "ab",   "ab",   "ab",   "ab",   "ab",   "al",   "ab",   "am",   "ae",       "ae",       "ae",       "ag",   "d",    "m",        "m",    "m",    "f",    "k",    "a",    "h",    "a",    "ac",   "b",                        "c",                        "e",            "g",            "ai",                   "h",                "j",    "l",            "n",        "l",            "p",            "q",            "p",        "j",            "t",            "u",                "y",        "aa",           "j",           "ad",           "af",       "z"},
            {"9",           "6",    "12",   "4",    "9",    "16",       "12",       "7",    "7",    "14",   "17",       "12",       "6",    "14",   "20",       "5",    "15",   "17",       "10",       "5",    "16",       "17",   "5",    "21",   "21",   "11",       "14",       "17",   "9",    "8",    "13",   "13",   "14",   "15",   "5",    "5",    "5",    "5",    "13",   "12",   "7",    "7",    "13",   "6",    "15",   "16",   "16",   "14",   "6",    "9",    "3",    "10",       "10",       "10",       "24",   "6",    "16",       "5",    "5",    "7",    "14",   "6",    "4",    "3",    "1",    "5",                        "2",                        "3",            "2",            "2",                    "1",                "6",    "5",            "6",        "4",            "6",            "2",            "3",        "5",            "3",            "2",                "1",        "3",            "7",            "6",            "6",        "5",}};


    public RoutingService() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Menü bekannt geben, dadurch kann unser Fragment Menü-Events verarbeiten
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final String[] vorhersageArray = {
                "Hier",
                "wird",
                "deine",
                "Route",
                "stehen"
        };
        final String[] Dis = {
                "",
                "",
                "",
                "",
                ""
        };
        final String[] Bit = {
                "li",
                "pi",
                "li",
                "pi",
                "li"
        };

        final List<String> Pfad = new ArrayList<String>(Arrays.asList(vorhersageArray));

        RoutingAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // Die aktuelle Umgebung (diese Activity)
                        R.layout.list_routing, // ID der XML-Layout Datei
                        R.id.PisteLift, // ID des TextViews
                        Pfad); // Beispieldaten in einer ArrayList

        View rootView = inflater.inflate(R.layout.fragment_ski_gebiet_navigation, container, false);
        RouteListView = (ListView) rootView.findViewById(R.id.RoutingList);
        //RouteListView.setAdapter(RoutingAdapter);
        RouteListView.setAdapter(new RoutingAdapter(getActivity(), vorhersageArray, Dis, Dis, Bit, 5));

        final Spinner startdrop = (Spinner)rootView.findViewById(R.id.spStartLifte);
        final ArrayAdapter<String> startadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Lift);
        startdrop.setAdapter(startadapter);

        final Spinner enddrop = (Spinner)rootView.findViewById(R.id.spEndeLifte);
        ArrayAdapter<String> endadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Lift);
        enddrop.setAdapter(endadapter);

        Button bNavi = (Button) rootView.findViewById(R.id.bNavigation);
        bNavi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for (int x=0;x<Karte[0].length;x++){
                    if (Karte[0][x]==startdrop.getSelectedItem().toString()){
                        START=Karte[2][x];
                    }
                }
                for (int x=0;x<Karte[0].length;x++){
                    if (Karte[0][x]==enddrop.getSelectedItem().toString()){
                        END=Karte[3][x];
                    }
                }
                if (START==END) Toast.makeText(getActivity(),"Du bist da :)", Toast.LENGTH_LONG).show();
                k = 0;
                Dijkstra dij = new Dijkstra();
                dij.execute();

            }
        });

        return rootView;
    }


    public class Dijkstra extends AsyncTask<String, String, String[]> {


        private final Graph.Edge[] GRAPH = {
                //for (int x=0; x<Karte[0].length; x++){
                new Graph.Edge(Karte[2][0], Karte[3][0], Integer.valueOf(Karte[4][0])),
                new Graph.Edge(Karte[2][1], Karte[3][1], Integer.valueOf(Karte[4][1])),
                new Graph.Edge(Karte[2][2], Karte[3][2], Integer.valueOf(Karte[4][2])),
                new Graph.Edge(Karte[2][3], Karte[3][3], Integer.valueOf(Karte[4][3])),
                new Graph.Edge(Karte[2][4], Karte[3][4], Integer.valueOf(Karte[4][4])),
                new Graph.Edge(Karte[2][5], Karte[3][5], Integer.valueOf(Karte[4][5])),
                new Graph.Edge(Karte[2][6], Karte[3][6], Integer.valueOf(Karte[4][6])),
                new Graph.Edge(Karte[2][7], Karte[3][7], Integer.valueOf(Karte[4][7])),
                new Graph.Edge(Karte[2][8], Karte[3][8], Integer.valueOf(Karte[4][8])),
                new Graph.Edge(Karte[2][9], Karte[3][9], Integer.valueOf(Karte[4][9])),
                new Graph.Edge(Karte[2][10], Karte[3][10], Integer.valueOf(Karte[4][10])),
                new Graph.Edge(Karte[2][11], Karte[3][11], Integer.valueOf(Karte[4][11])),
                new Graph.Edge(Karte[2][12], Karte[3][12], Integer.valueOf(Karte[4][12])),
                new Graph.Edge(Karte[2][13], Karte[3][13], Integer.valueOf(Karte[4][13])),
                new Graph.Edge(Karte[2][14], Karte[3][14], Integer.valueOf(Karte[4][14])),
                new Graph.Edge(Karte[2][15], Karte[3][15], Integer.valueOf(Karte[4][15])),
                new Graph.Edge(Karte[2][16], Karte[3][16], Integer.valueOf(Karte[4][16])),
                new Graph.Edge(Karte[2][17], Karte[3][17], Integer.valueOf(Karte[4][17])),
                new Graph.Edge(Karte[2][18], Karte[3][18], Integer.valueOf(Karte[4][18])),
                new Graph.Edge(Karte[2][19], Karte[3][19], Integer.valueOf(Karte[4][19])),
                new Graph.Edge(Karte[2][20], Karte[3][20], Integer.valueOf(Karte[4][20])),
                new Graph.Edge(Karte[2][21], Karte[3][21], Integer.valueOf(Karte[4][21])),
                new Graph.Edge(Karte[2][22], Karte[3][22], Integer.valueOf(Karte[4][22])),
                new Graph.Edge(Karte[2][23], Karte[3][23], Integer.valueOf(Karte[4][23])),
                new Graph.Edge(Karte[2][24], Karte[3][24], Integer.valueOf(Karte[4][24])),
                new Graph.Edge(Karte[2][25], Karte[3][25], Integer.valueOf(Karte[4][25])),
                new Graph.Edge(Karte[2][26], Karte[3][26], Integer.valueOf(Karte[4][26])),
                new Graph.Edge(Karte[2][27], Karte[3][27], Integer.valueOf(Karte[4][27])),
                new Graph.Edge(Karte[2][28], Karte[3][28], Integer.valueOf(Karte[4][28])),
                new Graph.Edge(Karte[2][29], Karte[3][29], Integer.valueOf(Karte[4][29])),
                new Graph.Edge(Karte[2][30], Karte[3][30], Integer.valueOf(Karte[4][30])),
                new Graph.Edge(Karte[2][31], Karte[3][31], Integer.valueOf(Karte[4][31])),
                new Graph.Edge(Karte[2][32], Karte[3][32], Integer.valueOf(Karte[4][32])),
                new Graph.Edge(Karte[2][33], Karte[3][33], Integer.valueOf(Karte[4][33])),
                new Graph.Edge(Karte[2][34], Karte[3][34], Integer.valueOf(Karte[4][34])),
                new Graph.Edge(Karte[2][35], Karte[3][35], Integer.valueOf(Karte[4][35])),
                new Graph.Edge(Karte[2][36], Karte[3][36], Integer.valueOf(Karte[4][36])),
                new Graph.Edge(Karte[2][37], Karte[3][37], Integer.valueOf(Karte[4][37])),
                new Graph.Edge(Karte[2][38], Karte[3][38], Integer.valueOf(Karte[4][38])),
                new Graph.Edge(Karte[2][39], Karte[3][39], Integer.valueOf(Karte[4][39])),
                new Graph.Edge(Karte[2][40], Karte[3][40], Integer.valueOf(Karte[4][40])),
                new Graph.Edge(Karte[2][41], Karte[3][41], Integer.valueOf(Karte[4][41])),
                new Graph.Edge(Karte[2][42], Karte[3][42], Integer.valueOf(Karte[4][42])),
                new Graph.Edge(Karte[2][43], Karte[3][43], Integer.valueOf(Karte[4][43])),
                new Graph.Edge(Karte[2][44], Karte[3][44], Integer.valueOf(Karte[4][44])),
                new Graph.Edge(Karte[2][45], Karte[3][45], Integer.valueOf(Karte[4][45])),
                new Graph.Edge(Karte[2][46], Karte[3][46], Integer.valueOf(Karte[4][46])),
                new Graph.Edge(Karte[2][47], Karte[3][47], Integer.valueOf(Karte[4][47])),
                new Graph.Edge(Karte[2][48], Karte[3][48], Integer.valueOf(Karte[4][48])),
                new Graph.Edge(Karte[2][49], Karte[3][49], Integer.valueOf(Karte[4][49])),
                new Graph.Edge(Karte[2][50], Karte[3][50], Integer.valueOf(Karte[4][50])),
                new Graph.Edge(Karte[2][51], Karte[3][51], Integer.valueOf(Karte[4][51])),
                new Graph.Edge(Karte[2][52], Karte[3][52], Integer.valueOf(Karte[4][52])),
                new Graph.Edge(Karte[2][53], Karte[3][53], Integer.valueOf(Karte[4][53])),
                new Graph.Edge(Karte[2][54], Karte[3][54], Integer.valueOf(Karte[4][54])),
                new Graph.Edge(Karte[2][55], Karte[3][55], Integer.valueOf(Karte[4][55])),
                new Graph.Edge(Karte[2][56], Karte[3][56], Integer.valueOf(Karte[4][56])),
                new Graph.Edge(Karte[2][57], Karte[3][57], Integer.valueOf(Karte[4][57])),
                new Graph.Edge(Karte[2][58], Karte[3][58], Integer.valueOf(Karte[4][58])),
                new Graph.Edge(Karte[2][59], Karte[3][59], Integer.valueOf(Karte[4][59])),
                new Graph.Edge(Karte[2][60], Karte[3][60], Integer.valueOf(Karte[4][60])),
                new Graph.Edge(Karte[2][61], Karte[3][61], Integer.valueOf(Karte[4][61])),
                new Graph.Edge(Karte[2][62], Karte[3][62], Integer.valueOf(Karte[4][62])),
                new Graph.Edge(Karte[2][63], Karte[3][63], Integer.valueOf(Karte[4][63])),
                new Graph.Edge(Karte[2][64], Karte[3][64], Integer.valueOf(Karte[4][64])),
                new Graph.Edge(Karte[2][65], Karte[3][65], Integer.valueOf(Karte[4][65])),
                new Graph.Edge(Karte[2][66], Karte[3][66], Integer.valueOf(Karte[4][66])),
                new Graph.Edge(Karte[2][67], Karte[3][67], Integer.valueOf(Karte[4][67])),
                new Graph.Edge(Karte[2][68], Karte[3][68], Integer.valueOf(Karte[4][68])),
                new Graph.Edge(Karte[2][69], Karte[3][69], Integer.valueOf(Karte[4][69])),
                new Graph.Edge(Karte[2][70], Karte[3][70], Integer.valueOf(Karte[4][70])),
                new Graph.Edge(Karte[2][71], Karte[3][71], Integer.valueOf(Karte[4][71])),
                new Graph.Edge(Karte[2][72], Karte[3][72], Integer.valueOf(Karte[4][72])),
                new Graph.Edge(Karte[2][73], Karte[3][73], Integer.valueOf(Karte[4][73])),
                new Graph.Edge(Karte[2][74], Karte[3][74], Integer.valueOf(Karte[4][74])),
                new Graph.Edge(Karte[2][75], Karte[3][75], Integer.valueOf(Karte[4][75])),
                new Graph.Edge(Karte[2][76], Karte[3][76], Integer.valueOf(Karte[4][76])),
                new Graph.Edge(Karte[2][77], Karte[3][77], Integer.valueOf(Karte[4][77])),
                new Graph.Edge(Karte[2][78], Karte[3][78], Integer.valueOf(Karte[4][78])),
                new Graph.Edge(Karte[2][79], Karte[3][79], Integer.valueOf(Karte[4][79])),
                new Graph.Edge(Karte[2][80], Karte[3][80], Integer.valueOf(Karte[4][80])),
                new Graph.Edge(Karte[2][81], Karte[3][81], Integer.valueOf(Karte[4][81])),
                new Graph.Edge(Karte[2][82], Karte[3][82], Integer.valueOf(Karte[4][82])),
                new Graph.Edge(Karte[2][83], Karte[3][83], Integer.valueOf(Karte[4][83])),
                new Graph.Edge(Karte[2][84], Karte[3][84], Integer.valueOf(Karte[4][84])),
                new Graph.Edge(Karte[2][85], Karte[3][85], Integer.valueOf(Karte[4][85])),
                new Graph.Edge(Karte[2][86], Karte[3][86], Integer.valueOf(Karte[4][86]))

        };


        public void main(String[] args) {
            ;
        }

        @Override
        protected String[] doInBackground(String... strings) {
            Graph g = new Graph(GRAPH);
            g.dijkstra(START);
            g.printPath(END);
            //g.printAllPaths();


            return null;

        }

        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);

            RouteListView.setAdapter(new RoutingAdapter(getActivity(), kdk, Bilder, Distanz, PisLift, Zahl));
        }
    }

    static class Graph {
        private final Map<String, Vertex> graph; // mapping of vertex names to Vertex objects, built from a set of Edges

        /**
         * One edge of the graph (only used by Graph constructor)
         */
        public static class Edge {
            public final String v1, v2;
            public final int dist;

            public Edge(String v1, String v2, int dist) {
                this.v1 = v1;
                this.v2 = v2;
                this.dist = dist;
            }
        }

        /**
         * One vertex of the graph, complete with mappings to neighbouring vertices
         */
        public static class Vertex implements Comparable<Vertex> {
            public final String name;
            public int dist = Integer.MAX_VALUE; // MAX_VALUE assumed to be infinity
            public Vertex previous = null;
            public final Map<Vertex, Integer> neighbours = new HashMap<>();

            public Vertex(String name) {
                this.name = name;
            }

            private void printPath() {
                String PisteLift = null;
                String Bild = null;
                String Piste;

                if (this == this.previous) {
                    System.out.printf("%s", this.name);
                    for (int i = 0; i < Karte[0].length; i++) {
                        if (this.name == Karte[3][i]) {
                            PisteLift = "Bergstation\n" + Karte[0][i];
                            PisLift[0] = "li";
                            Bild = "l" + Karte[1][i];
                        } else if (this.name == Karte[2][i]) {
                            PisteLift = "Talstation\n" + Karte[0][i];
                        }
                    }

                    kdk[k] = PisteLift;
                    Distanz[k] = "Start";
                    Bilder[k] = Bild;
                    k = k + 1;
                } else if (this.previous == null) {
                    System.out.printf("%s(unreached)", this.name);

                } else {
                    this.previous.printPath();
                    System.out.printf(" -> %s(%d)", this.name, this.dist);
                    kdk[k] = this.name + this.dist;
                    for (int i = 0; i < Karte[0].length; i++) {
                        if (this.name == Karte[3][i] && this.previous.name == Karte[2][i]) {
                            Piste = Karte[0][i];
                            if (Karte[0][i] != "skiroute") {
                                Bild = Piste.substring(0, 1) + Karte[1][i];
                            } else {
                                Bild = Piste.substring(0, 2) + Karte[1][i];
                            }
                            if (Karte[0][i] == "blau" || Karte[0][i] == "rot" || Karte[0][i] == "schwarz") {
                                PisLift[k] = "pi";
                                PisteLift = "Piste: " + Karte[1][i];
                            } else if (Karte[0][i] == "skiroute") {
                                PisteLift = "Skiroute: " + Karte[1][i];
                            }else{
                                PisLift[k] = "li";
                                Bild = "l" + Karte[1][i];
                                PisteLift = "Lift: " + Karte[0][i];
                            }
                        }
                    }

                    kdk[k] = PisteLift;
                    Bilder[k] = Bild;
                    Distanz[k] = String.valueOf(this.dist);
                    k = k + 1;
                    if (this.name == END) Zahl = k;
                }
            }

            public int compareTo(Vertex other) {
                return Integer.compare(dist, other.dist);
            }
        }

        /**
         * Builds a graph from a set of edges
         */
        public Graph(Edge[] edges) {
            graph = new HashMap<>(edges.length);

            //one pass to find all vertices
            for (Edge e : edges) {
                if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
                if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
            }

            //another pass to set neighbouring vertices
            for (Edge e : edges) {
                graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
                //graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
            }
        }

        /**
         * Runs dijkstra using a specified source vertex
         */
        public void dijkstra(String startName) {
            if (!graph.containsKey(startName)) {
                System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
                return;
            }
            final Vertex source = graph.get(startName);
            NavigableSet<Vertex> q = new TreeSet<>();

            // set-up vertices
            for (Vertex v : graph.values()) {
                v.previous = v == source ? source : null;
                v.dist = v == source ? 0 : Integer.MAX_VALUE;
                q.add(v);
            }

            dijkstra(q);

        }

        /**
         * Implementation of dijkstra's algorithm using a binary heap.
         */
        private void dijkstra(final NavigableSet<Vertex> q) {
            Vertex u, v;
            while (!q.isEmpty()) {

                u = q.pollFirst(); // vertex with shortest distance (first iteration will return source)
                if (u.dist == Integer.MAX_VALUE)
                    break; // we can ignore u (and any other remaining vertices) since they are unreachable

                //look at distances to each neighbour
                for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
                    v = a.getKey(); //the neighbour in this iteration

                    final int alternateDist = u.dist + a.getValue();
                    if (alternateDist < v.dist) { // shorter path to neighbour found
                        q.remove(v);
                        v.dist = alternateDist;
                        v.previous = u;
                        q.add(v);
                    }
                }
            }
        }

        /**
         * Prints a path from the source to the specified vertex
         */
        public void printPath(String endName) {
            if (!graph.containsKey(endName)) {
                System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
                return;
            }
            graph.get(endName).printPath();

            System.out.println();

        }

        /**
         * Prints the path from the source to every vertex (output order is not guaranteed)
         */
        public void printAllPaths() {
            for (Vertex v : graph.values()) {
                v.printPath();
                System.out.println();
            }
        }


    }

}