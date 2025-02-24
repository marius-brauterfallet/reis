# Reis

Appen benytter seg av MVVM, og strukturerer således all kode knyttet til views i screens og components under "ui"-mappa, mens business-logikk er håndtert av viewmodels og services. Jeg vil si mappestrukturen i stor grad er ganske selvforklarende.

For vanlige REST-kall har jeg benyttet meg av Ktor Client, og for dependency injection har jeg brukt Koin. Disse bibliotekene valgte jeg, rett og slett fordi jeg er mest kjent med dem, og jeg synes de er enklere å forholde seg til enn andre alternativer som Dagger-Hilt og Retrofit. Ellers har jeg brukt Googles Accompanist for å håndtere rettigheter knyttet til henting av posisjon.

Data hentes direkte fra APIer i data sources som henter dataene uten å gjøre så mye mer med dem. Denne dataen håndteres så av repositories (kun et EnturRepository i dette tilfellet), som behandler og transformerer dataen. Deretter eksponerer EnturService dataen til viewmodel-ene. 

Formen på appen er veldig enkel, med en skjerm som alltid viser nærmeste stopp, og en skjerm hvor brukeren kan søke på stopp.

Dersom jeg skulle brukt mer tid på appen, ville jeg implementert en mer robust exception-håndtering, mens nå bruker jeg veldig mye runCatching uten å håndtere noe. I såfall ville jeg nok behandlet ting som kan gå galt med nettverkskall og liknende i datasourcene, og konvertert dette til enklere exceptions som kan håndteres i view-laget med mer beskrivende feilmeldinger.

Jeg kunne også tenkt meg å cache søk gjort av brukeren, så de 3 seneste stoppene brukeren har søkt på kom opp øverst, eller eventuelt vist de nærmeste stoppene i toppen av søkevinduet.

Annet enn dette, ville jeg nok dokumentert funksjoner og slikt bedre, samt skrevet enhets- og integrasjonstester, men ellers er det ikke en så omfattende app at jeg føler det er så mye å si. I tillegg ville jeg ryddet litt opp i lagene. Ansvarsområdene er nå litt blandet mellom datakildene, EnturRepository og EnturService. Både GraphQL og henting av posisjon var ting som var ganske nytt for meg, men alt i alt, vil jeg si jeg er passe fornøyd med det jeg har gjort her.