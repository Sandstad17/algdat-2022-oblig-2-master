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
Denne konstruktøren starter med to tomme noder som hode og hale, og bygger derfra ut en enkeltlenket
liste av ytterligere elementer i listen det bygges fra. Etter den enkeltlenkede listen er opprettet,
går den gjennom igjen og legger inn bakoverpekerene slik at det blir en dobbeltlenket liste.

I oppgave 2 så gikk vi frem ved å hoppe over hode-noden, grunnet strukturen vi har kodet, 
og hente hver verdi etter, som legges til i en stringbuilder. Returnerer strengen når alle noder
er gjennomløpt. LeggInn-metoden er laget slik at den legger inn nye verdier først i listen ved å
binde om hode-pekeren til den nye noden, nye noden får neste-peker til gamle første, og gamle første
får forrige-peker til ny noden.

I oppgave 3a)
setter vi Node til p og så finner vi "midten" ved å ta antall og dele det på to.
derretter har vi if setninger som sier hvis indeksen vi leter etter er større en midten,
så skal vi gå fra Noden som blir satt til hode.neste og videre ved hjelp av en for løkke
fram til indeksen er nådd. hvis ikke skal vi gå fra noden som er hale.forrige og gå igjennom
til vi finner indeksen, tilslutt returneres Noden(p). I oppgave b) sjekker vi fratilkontroll først.
Etter dette sjekker vi om "til" er før "fra" og gir en illegalArgumentexception om dette stemmer.
derretter oppretter vi en ny dobbeltlenkeliste ved navn sublisten. Derretter looper vi gjennom
og bruker leggInn for å legge inn verdiene fra "fra" til "til". tilslutt returneres sublisten.
Fratilkontroll er kodet slik at den enten sjekker om indeksen er innenfor antallet noder, 
eller antallet plasser mellom, og på endene av nodene som er i listen. Hvis oppgitt indeks 
faller utenfor kravene, kastes en exception.

I oppgave 4 så gikk vi frem ved å gjennomløpe listen til den gitte verdien enten finnes, eller slutten
av listen nås. For siste verdie sjekkes igjen nåværende verdi mot søkeverdi, og om de ikke matcher, 
returneres false. For tom søkeverdi (null), returneres også false. Inneholder-metoden fungerer noe likt,
i at den gjennomløper alle nodene, til den finner ønsket verdi, og i så fall returnerer true. Ellers returneres false.

I oppgave 5 så gikk vi frem ved å gjennomløpe nodene til oppgitt indeks, så sette nodene på hver side 
inn i hjelpenoder, slik at ny node kan settes inn mellom de eksisterende. Så settes pekere på plass igjen.

I oppgave 7 så gikk vi frem ved å gjennomløpe alle nodene, sette verdien deres til null, fjerne pekerene deres. 
Dermed kan søppeltømmeren fjerne dem fra minnet.


I oppgave 9 skulle vi lage kode for remove(). Oppgaven ble først løst ved å behandle edge-cases som at listen er tom 
eller at man ikke ha lov til å fjerne, og at endringer != itterattorendringer. I disse tilfelle ble det kastet en error.
Så sjekker vi om antall i listen er 1, isåfall skal hode.neste og hale.forrige bli nullet. Om ikke det er tilfellet
sjekker vi om vi er på siste element, altså denne == null, da må vi endre hale til å peke på det som da var nest siste 
element. Om vi ikke er bakerst så sjekker vi om vi er forrest i listen. I dette tilfellet så endre vi hode til å peke 
på det da var nest først. Om ingen av de overnevnte situasjonene påtreffes så peker vi nodene på hver side av noden 
vi skal fjerne, på hverandre. Det fører til at java sin garbage-collector tar av seg jobben ved å fjerne noden vi 
behandlet.