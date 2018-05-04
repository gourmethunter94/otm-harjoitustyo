# Testausdokumentti

Ohjelmaa on testattu manuaallisesti sekä automaattisten JUnit testien avulla.

## JUnit testit

Suurin osa automaattisesta testuaksesta testaa ohjelman yksittäisten osien toimivuutta, eikä testien onnistunut ajaminen itsessään riitä siihen, että ohjelma toimisi välttämättä oikein. Ohjelma on luonteeltana peli, jonka tapahtumat aiheuttavat pitkiä metodi kutsu ketjuja. Tämän takia tarkkojen ohjelman toimintaa tarkastelevien testien kirjoittaminen olisi vaikeaa, sekä ohjelman toimivuuden toteaminen niiden avulla veisi pitkän aikaa.

### UI

User Interface on toteutettu JavaFX toiminnallisuuksien päälle, mutta ei kuitenkaan pelkästään valmiita ominaisuuksia käyttäen.

 - [UIManagerTest](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/Clicker/src/test/java/com/mycompany/clicker/core/UIManagerTest.java), joka testaa koko UI ryhmittelyn initialisaatiota.
 - [GameTest](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/Clicker/src/test/java/com/mycompany/clicker/core/GameTest.java), jonka metodi gameUI() testaa toimiiko osa UI:n nappuloista interaktiivisesti.
 - [ui Package](https://github.com/GourmetHunter/otm-harjoitustyo/tree/master/Clicker/src/test/java/com/mycompany/clicker/ui), jonka kaikki luokat testaavat eri osia UI:sta.

### Data Objectit

Dataa säilövien ja helpompaa siirtämistä varten olevat luokat testataan testeillä [domain](https://github.com/GourmetHunter/otm-harjoitustyo/tree/master/Clicker/src/test/java/com/mycompany/clicker/domain) paketissa.

### DAO-luokat

Database ja DAO-luokkien testaus on tehty varovaisesti, sillä ne käyttävät varsinaista databasea, jota myös peli käyttää.

Luokkien testauksesta ovat paketissa [dao](https://github.com/GourmetHunter/otm-harjoitustyo/tree/master/Clicker/src/test/java/com/mycompany/clicker/dao) olevat luokat.

### Testauskattavuus

Osaa käyttöliittymän luovasta koodista huomioon ottamatta ohjelman testien rivi kattavuus on 70% ja haarautuma kattavuus on 49%.

Tämä johtuu osittain siitä, että osaa metodeista joita pitäisi testava voitaisiin testata vain käyttöliittymää käyttämällä, jonka takia niillei ole testejä vaan niitä testataan manuaallisesti.

<img src="https://raw.githubusercontent.com/GourmetHunter/otm-harjoitustyo/master/dokumentaatio/kuvat/testikattavuus.png">

## Manuaallinen testaus

Ohjelma sisältää osia jotka on testattu automatisoitujen testien sijaan manuaallisesti, varsinkin luokkaa [Loader](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/Clicker/src/main/java/com/mycompany/clicker/core/Loader.java) on testattu paljon manuaallisesti.

## Järjestelmä testaus

Ohjelma on asennettu [käyttöohjeen](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) mukaisesti Windows, Linux ja OSX käyttöjärjestelmillä.

## Virhe tilanteiden testaus

Ohjelma pyytä käyttäjältä syötteitä erittäin rajallisesti, vain hiirtä käyttäen, joten käyttäjän on miltein mahdotonta antaa virheellisiä syötteitä.

Tästä johtuen ei myöskään testejä ei ole tarvinnut tehdä virheellisille syötteille.

## Laatu ongelmat

Sovellus ei ole mitenkään varautunut tilanteisiin, joissa ei sillä ole luku, kirjoitus tai käyttöoikeutta vaadittuihin tiedostoihin.