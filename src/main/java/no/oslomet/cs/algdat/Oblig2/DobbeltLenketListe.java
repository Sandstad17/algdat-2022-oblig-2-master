package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = new Node<T>(null);
        hale = new Node<T>(null);

        antall = 0;
        endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {

        if (a == null) {
            throw new NullPointerException("Tabellen a er null!");
        }

        Node<T> p; //Previous
        Node<T> q; //Qurrent

        antall = 0;
        endringer = 0;

        hode = new Node<T>(null);
        hale = new Node<T>(null);

        if (a.length != 0) {
            p = hode;
            int i = 0;
            while (i < a.length) {
                if (a[i] != null) {
                    q = new Node<T>(a[i]);
                    p.neste = q;
                    antall++;
                    p = q;
                }
                i++;
            }

            if (antall > 0) {
                p = q = hode.neste;
                if(antall > 1) {
                    while (q.neste != null) {
                        q = q.neste;
                        q.forrige = p;
                        p = q;
                    }
                }
                hale.forrige = q;
            }
        }
    }

    //OPPGAVE 3.B
    public Liste<T> subliste(int fra, int til) {


        //Kontroll for fra og til indeksene
        fratilKontroll(fra,true);
        fratilKontroll(til,true);

        if (fra > til) {
            throw new IllegalArgumentException("Til er før i listen enn fra");
            //Kunne eventuelt returnert "[]"
        }
        //Ny liste med navn "sublisten"
        DobbeltLenketListe<T> subListen = new DobbeltLenketListe<T>();

        //Looper gjennom orgianle listen for å legge inn i "sublisten"
        for (int i = fra; i < til; i++) {
            subListen.leggInn(hent(i));
        }

        //returnerer sublisten
        return subListen;
    }
    //

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        if(antall == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi);

        Node<T> p;
        Node<T> q;

        q = new Node<T>(verdi,null,null);

        if(tom()){
            hode.neste = q;

            antall ++;
            endringer ++;
        }
        else{
            p = hale.forrige;
            p.neste = q;
            q.forrige = p;

            antall ++;
            endringer ++;
        }
        hale.forrige = q;
        return true;

    }

    //Funker ikke, kom tilbake i oppgave 5
    @Override
    public void leggInn(int indeks, T verdi) {
        //indeks må være gyldig ( fra 0 til n-tall )
        //verdien skal legges inn på indeksen som er satt
        //
        Objects.requireNonNull(verdi);
        indeksKontroll(indeks, true);

        Node<T> h = hode;   //Hode eller node før ny settes inn
        Node<T> t = hale;   //Hale eller noden etter der ny settes inn
        Node<T> ny = new Node<T>(verdi);

        if (tom()) {
            h.neste = ny;
            t.forrige = ny;
        }
        else{
            int i = 0;
            for (;i < indeks; i++) {
                h = h.neste;
            }

            if (i == 0) {
                t = h.neste;
                ny.neste = h.neste;
                h.neste = ny;
                t.forrige = ny;
            }
            else if (i == antall) {
                t.forrige = ny;
                ny.forrige = h;
                h.neste = ny;
            }
            else {
                t = h.neste;

                h.neste = ny;
                t.forrige = ny;

                ny.neste = t;
                ny.forrige = h;

            }
        }
        antall ++;
        endringer ++;
    }

    @Override
    public boolean inneholder(T verdi) {
        Node<T> q = hode;

        while (q.neste != null) {
            q = q.neste;
            if (q.verdi.equals(verdi)) {
                return true;
            }
        }
        return false;
    }

    //OPPGAVE 3
    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    private Node<T> finnNode(int indeks){
        //Iterativ løsnin
        Node<T> p;

        int midten = antall/2;

        if (indeks <= midten) {
            //skriv hendelse som leter fra Head og gå mot Høyre ved hjelp av neste-pekere
            p = hode.neste;
            for(int i = 0; i < indeks; i++){
               p = p.neste;
            }
        }
        else {
            //skriv hendelse som leter fra Tail og gå mot venstre ved hjelp av forrige-pekere
            p = hale.forrige;
            for(int i = antall - 1 ; i > indeks; i--){
                p = p.forrige;
            }
        }
        return p;
    }



    @Override
    public int indeksTil(T verdi) {
        int i = 0;
        if (antall > 0) {
            Node<T> q = hode;
            while (q.neste != null) {
                q = q.neste;
                if (q.verdi.equals(verdi)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        //Tillater ikke nullverdier i nytt objekt
        Objects.requireNonNull(nyverdi);

        //Sjekk om det er lovlig innput
        indeksKontroll(indeks, false);

        //Lager en variabel av typen Node
        Node<T> node = finnNode(indeks);

        //Lagrer forrige verdi i gammelVerdi variabel, slik at vi kan returnere senere
        T gammelVerdi = node.verdi;

        //Oppdaterer indeks til nyverdi
        node.verdi = nyverdi;

        //Endringer må oppdateres også
        endringer++;

        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {

        Node<T> q = hode.neste;     //Nåværende node, starter i indeks 0

        int i = 0;
        if (verdi != null) {
            if (antall > 0) {
                while (q.neste != null && i < antall && !q.verdi.equals(verdi)) {
                    q = q.neste;
                    i++;
                }
                if (!q.verdi.equals(verdi)){
                    return false;
                }

                Node<T> p = q.forrige;                  //Node før
                Node<T> n = q.neste;                  //Node etter
                if (antall == 1) {
                    hode.neste = null;
                    hale.forrige = null;
                }
                else if (i == 0) {
                    n = hode.neste.neste;
                    n.forrige = null;
                    hode.neste = n;
                }
                else if (i == antall - 1) {
                    p = hale.forrige.forrige;
                    p.neste = null;
                    hale.forrige = p;
                }
                else {
                    n.forrige = p;
                    p.neste = n;
                }
                //Fjerner q
                q.neste = null;
                q.forrige = null;
                q.verdi = null;

                antall --;
                endringer ++;
                return true;
            }
        }

        return false;
    }

    @Override
    public T fjern(int indeks) {
        fratilKontroll(indeks, false);

        Node<T> q = hode.neste;
        Node<T> p = hode;
        Node<T> n = hale;

        if (antall == 1) {
            p.forrige = null;
            n.neste = null;
        }
        else {
            for (int i = 0; i < indeks; i++) {
                q = q.neste;
            }
            if (indeks == 0) {
                n = q.neste;

                n.forrige = null;
                p.neste = n;
            }
            else if (indeks == antall - 1) {
                p = q.forrige;

                n.forrige = p;
                p.neste = null;
            }
            else {
                p = q.forrige;
                n = q.neste;

                p.neste = n;
                n.forrige = p;
            }
        }
        //Fjerner q
        q.neste = null;
        q.forrige = null;

        antall --;
        endringer ++;

        return q.verdi;
    }

    @Override
    public void nullstill() {

        if (antall != 0) {
            Node<T> p = hode;
            Node<T> q = p.neste;

            while (q.neste != null) {
                p.verdi = null;
                p.neste = null;
                p.forrige = null;
                p = q;
                q = q.neste;
            }
            q.verdi = null;
            q.forrige = null;
        }

        hode.neste = null;
        hale.forrige = null;

        antall = 0;
        endringer ++;
    }

    @Override
    public String toString() {
        if (tom()){
            return "[]";
        }
        StringBuilder s = new StringBuilder();
        s.append("[");

        Node<T> q = hode.neste;

        while (q.neste != null) {
            s.append(q.verdi.toString());
            s.append(", ");
            q = q.neste;
        }
        s.append(q.verdi);
        s.append("]");

        return s.toString();
    }

    public String omvendtString() {
        if (tom()){
            return "[]";
        }
        StringBuilder s = new StringBuilder();
        s.append("[");

        Node<T> q = hale.forrige;

        while (q.forrige != null) {
            s.append(q.verdi.toString());
            s.append(", ");
            q = q.forrige;
        }
        s.append(q.verdi);
        s.append("]");

        return s.toString();
    }



    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks,false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode.neste;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks);
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            //Antar at vi har gjort feil i while-løkken ved å ikke returnere "denne" for hver itterasjon
            //Erik ikke rør med mindre det er liv og død (husk å legge til kommentarer om du endrer noe)

            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException();
            }

            if (hasNext()) {
                T temp = denne.verdi;
                fjernOK = true;
                denne = denne.neste;
                return temp;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            if(tom()){

                throw new IllegalStateException();
            }
            if (!fjernOK) {
                throw new IllegalStateException();
            }


            if (endringer != iteratorendringer) {
                throw new ConcurrentModificationException();
            }

            if(antall == 1){
                hode.neste = null;
                hale.forrige = null;
            }

            else if(denne == null){
                hale.forrige = hale.forrige.forrige;
                hale.forrige.neste = null;
            }
            else if(denne == hode.neste.neste){ //første element
                hode.neste = hode.neste.neste;
                hode.neste.forrige = null;
            }
            else{
                Node<T> q = denne.forrige;
                Node<T> p = q.forrige;
                Node<T> r = q.neste;

                p.neste = r;
                r.forrige = p;
            }
            endringer++;
            iteratorendringer++;
            antall--;
            fjernOK = false;

        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        for (int i = 0; i<liste.antall(); i++){
            for (int j = 0; j <liste.antall(); j++){
                if(c.compare(liste.hent(i),liste.hent(j)) < 0){
                    T temp = liste.hent(i);
                    liste.oppdater(i,liste.hent(j));
                    liste.oppdater(j,temp);
                }
            }
        }
    }



    private void fratilKontroll(int indeks, boolean leggInn) {
        if (indeks < 0 || (!leggInn && (indeks >= antall)) || (leggInn && (indeks > antall))) {
            throw new IndexOutOfBoundsException(melding(indeks));
        }
    }

} // class DobbeltLenketListe


