package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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


    }

    public DobbeltLenketListe(T[] a) {

        if (a == null) {
            throw new NullPointerException("Tabellen a er null!");
        }
        Node<T> p; //Previous
        Node<T> q; //Qurrent

        antall = 0;

        if (a.length != 0) {
            int i = 0;
            while (i < a.length && a[i] == null) {
                i++;
            }
            if (i < a.length) {

                hode = new Node<T>(null);
                hode.neste = q = new Node<T>(a[i]);
                antall++;
                i++;
                p = q;

                while (i < a.length) {
                    if (a[i] != null) {
                        p.neste = q = new Node<T>(a[i]);
                        antall++;
                        p = q;
                    }
                    i++;
                }

                hale = new Node<T>(null);
                hale.forrige = p;

                p = hode.neste;
                while (p.neste != null) {
                    q = p.neste;
                    q.forrige = p;
                    p = q;
                }
            }
        }
    }

    //OPPGAVE 3.B
    public Liste<T> subliste(int fra, int til) {


        //Kontroll for fra og til indeksene
        indeksKontroll(fra, false);
        indeksKontroll(til, false);

        //Ny liste med navn "sublisten"
        DobbeltLenketListe subListen = new DobbeltLenketListe<T>();

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
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi);
        Node<T> p;
        Node<T> q;
        if(tom()){
            q = new Node<T>(verdi);
            hode.neste = q;
            hale.forrige = q;
            antall++;
        }else{
            q = new Node<T>(verdi);
            p = hale.forrige;
            p.neste = q;
            hale.forrige = q;
            antall++;
        }
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
        throw new UnsupportedOperationException();
    }

    //OPPGAVE 3
    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    private Node<T> finnNode(int indeks){
        //iterativ løsnin
        Node<T> p = null;

        int midten = antall/2;

        if (indeks < midten) {
            p = hode;
            for(int i = 0; i < indeks; i++){
               p = p.neste;
            }
            return p;
            //skriv hendelse som leter fra Head og gå mot Høyre ved hjelp av neste-pekere
        }
        if(indeks > midten) {
            p = hale;
            for(int i = antall; i > indeks; i--){
                p = p.forrige;
            }
            return p;
            //skriv hendelse som leter fra Tail og gå mot venstre ved hjelp av forrige-pekere
        }

        return p; //skriv hendelse som leter fra Head og gå mot Høyre ved hjelp av neste-pekere
    }
            //skriv hendelse som leter fra Tail og gå mot venstre ved hjelp av forrige-pekere


    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {

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
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {

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

        return q.verdi;

    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
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

} // class DobbeltLenketListe


