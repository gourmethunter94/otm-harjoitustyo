# Käyttöohje

Lataa ohjelma [täältä](https://github.com/GourmetHunter/otm-harjoitustyo/releases/tag/v1.0).

## Asennus

Ohjelma olettaa että sen käynnistys hakemistossa on latauslinkin sisältämä hakemisto *lib*, jonka sisällä on tiedosto _sqlite-jdbc-3.8.10.1.jar_, ja hakemisto *classes*, jonka sisällä on tiedosto _database.db_.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla



```

java -jar Clicker-1.0-SNAPSHOT.jar
```

Komennon viimeinen osa on tietysti vaihdettava, jos tiedoston nimeä on muutettu.

Jos tämä on ensimmäinen käynnistys kerta, pitäisi pelin avautua hetken kuluttua, jos _asennus_ on tehty ohjeen mukaisesti.

Peli simuloi offline ajan, joten käynnistys saattaa kestää muutamia sekuntteja, jos peliä on pelattu aiemmin. Tämä tapahtuu vain jos damage per second päivitystä on ostettu pelin kaupasta.

## Ohjelman käyttäminen

Pelin alussa ainut tapa edetä on klikkaamalla toistuvasti ruutua, jolloin tehdään vastustajaan vahinkoa. Kun tarpeeksi vastustajia on peitottu saa käyttäjä rahaa, jolla hän voi ostaa pelin kaupasta päivityksiä.

Pelissä voi vaihdella tasoja ensimmäisen tason ja suurimman saavutetun tason välillä, tämä tapahtuu nappuloilla jotka ovat ruudun keskellä olevan informaatio ruudun vieressä.

Yksi näistä päivityksistä tekee vahinkoa automaattisesti vastustajaa, joka tarkoittaa, että halutessaan pelaajan ei tarvitse tehdä klikkaamalla enää yhtää vahinkoa jos hän näin haluaa.

Peli tallentuu itsestään aina kun pelaaja joko ostaa jotain pelin kaupasta tai peittoaa vastustajan.

## Offline simulaatio

Jos pelaaja on ostanut damage per second (vahinkoa sekunteittain) päivitystä, tulee peli tekemään käynnistyksen yhteydessä offline simulaation.

Simulaation ideana on edetä pelissä aivan kuin se olisi ollut auki koko sen ajan kun se on ollut kiinni. Simulaatio taistelee vastustajia vastaan sellaisella tasolla peliä, jossa vastustajan peittoamiseen kuluu maksimissaan 30 sekunttia.

## Asetukset

Peli sisältää kaksi asetus vaihtoehtoa, ja niitä voi muuttaa pelin sisäisestä asetus valikosta.

- Fullscreen
- Sounds

Äänen pitäisi soida vain kun vastustaja peitotaan.


