package no.oslomet.cs.algdat.Oblig2;

public class Main_For_Test {
    public static void main(String[] args) {

        String[] s1 = {}, s2 = {"A"}, s3 = {null,"A",null,"B",null, "C"};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);

        System.out.println(l1.toString() + " " + l2.toString()
                + " " + l3.toString() + " " + l1.omvendtString() + " "
                + l2.omvendtString() + " " + l3.omvendtString());

        // Utskrift: [] [A] [A, B] [] [A] [B, A]
    }
}
