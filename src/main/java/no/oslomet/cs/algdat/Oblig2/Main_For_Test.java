package no.oslomet.cs.algdat.Oblig2;

public class Main_For_Test {
    public static void main(String[] args) {

        String[] s = {"Ole", "kari", "NESTE", "Per", "Kari"};
        Liste<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste.antall() + " " + liste.tom());

    }
}
