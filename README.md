# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer.
Oppgaven er levert av følgende studenter:
* Erik Christopher Kierulf Næsguthe, s356251, s356251@oslomet.no
* Christian Ø Sandstad, s364752, s364752@oslomet.no
* Mathias Ø Schjetne, s364744, s364744@oslomet.no
* Sivert Aasdalen, s362113, s362113@oslomet.no
* Even Andreas Ingeberg, s362107, s362107@oslomet.no

# Arbeidsfordeling

I oppgaven har vi hatt følgende arbeidsfordeling:
* Vi har delegert ansvar slik at vi satt sammen og jobbet med hver oppgave i rekkefølge til den ble løst, så gå videre til den neste.
* 


# Oppgavebeskrivelse

I oppgave 1 så gikk vi sette opp en konstruktør for klassen dobbeltlenket liste.
Denne konstruktøren starter med to tomme noder som hode og hale, og bygger derfra ut en enkeltlenket liste av ytterligere elementer i listen det bygges fra.
Etter den enkeltlenkede listen er opprettet, går den gjennom igjen og legger inn bakoverpekerene slik at det blir en dobbeltlenket liste.

I oppgave 2 så brukte vi en ... til å ...

I oppgave 3a)
setter vi Node til p og så finner vi "midten" ved å ta antall og dele det på to. 
derretter har vi if setninger som sier hvis indeksen vi leter etter er større en midten, 
så skal vi gå fra Noden som blir satt til hode.neste og videre ved hjelp av en for løkke
fram til indeksen er nådd. hvis ikke skal vi gå fra noden som er hale.forrige og gå igjennom 
til vi finner indeksen, tilslutt returneres Noden(p). I oppgave b) sjekker vi fratilkontroll først.
Etter dette sjekker vi om "til" er før "fra" og gir en illegalArgumentexception om dette stemmer.
derretter oppretter vi en ny dobbeltlenkeliste ved navn sublisten. Derretter looper vi gjennom
og bruker leggInn for å legge inn verdiene fra "fra" til "til". tilslutt returneres sublisten.