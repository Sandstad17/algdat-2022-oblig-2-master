package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;


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
        if (antall == 0) {
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

        if(verdi != null && indeks <= antall) {
            Node<T> q = hode.neste;

            Node<T> ny = new Node<T>(verdi);

            if (tom()) {
                hode.neste = ny;
                hale.forrige = ny;
                antall ++;
                endringer ++;
            }
            else{
                for (int i = 0; i < indeks; i++) {
                    q = q.neste;
                }
            }
            if (indeks == 0 && !tom()) {
                hode.neste = ny;
                ny.neste = q;
                q.forrige = ny;
            }
            else if (indeks == antall) {
                hale.forrige = ny;
                ny.forrige = q;
                q.neste = ny;
            }
            else {
                Node<T> etter = q.neste;

                ny.neste = etter;
                etter.forrige = ny;

                q.neste = ny;
                ny.forrige = q;
            }

            antall ++;
            endringer++;
        }
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

        Node<T> q = hode;
        Node<T> p;
        Node<T> n;

        int i = 0;

        while (q.neste != null) {
            q = q.neste;
            if (verdi.equals(q.verdi)) {

                q.verdi = null;
                if (antall != 0) {
                    if (i == 0) {
                        n = q.neste;

                        hode.neste = n;
                        n.forrige = null;
                    }
                    else if (i == antall - 1) {
                        p = q.forrige;

                        hale.forrige = p;
                        p.neste = null;
                    }
                    else {
                        n = q.neste;
                        p = q.forrige;

                        n.forrige = p;
                        p.neste = n;
                    }
                }
                q.neste = null;
                q.forrige = null;

                antall --;
                endringer ++;
                break;
            }
            i++;
        }
        return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);

        Node<T> q = hode.neste;

        for (int i = 0; i < indeks; i++) {
            q = q.neste;
        }

        if (indeks == 0) {
            q = hode.neste.neste;

            q.forrige = null;
            hode.neste = q;
        }
        else if (indeks == antall - 1) {
            hale.forrige = q.forrige;
            hale.forrige.neste = null;
        }
        else {
            q.forrige.neste = q.neste;
            q.neste.forrige = q.forrige;
        }

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
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    private void fratilKontroll(int indeks, boolean leggInn) {
        if (indeks < 0 || !leggInn && (indeks > antall - 1) || leggInn && (indeks > antall)) {
            throw new IndexOutOfBoundsException(melding(indeks));
        }
    }

} // class DobbeltLenketListe


