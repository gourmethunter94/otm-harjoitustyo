**Authored by:** Olli Kärki, 014807723

For course [Ohjelmistotekniikan menetelmät, kevät 2018](https://github.com/mluukkai/otm-2018).

# Tietokonepeli: Clicker

Tietokonepeli, jonka ideana on käytännössä vain tuhlata käyttäjän aikaa. Pelissä odotetaan rahan keräytymistä ja sitten ostetaan asioita, että rahaa tulisi entistä nopeammin. Vaativammat osat pelin toteutuksessa tulee automaattisesta kokoaikaisesta talletamisesta, ja pelin etenemisestä vaikka sovellus ei ole päällä.

## Releaset

[Viikko 5](https://github.com/GourmetHunter/otm-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/GourmetHunter/otm-harjoitustyo/releases/tag/viikko6)

## Ongelmat

Jos Netbeans ei pysty suorittamaan suoraa vihreää 'play' nappia painamalla, koikeile projectin buildaamista ja sitten nappi saattaa toimia.

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/dokumentaatio/Vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Käyttöohje](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Testausdokumentti](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/dokumentaatio/testaus.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla


```
mvn test
```

Testikattavuus luodaan komennolla

```
mvn jacoco:report
```

Tai, jos testejä ei ole ajettu vielä kertaakaan

```
mvn test jacoco:report
```

Kattavuus raportti löytyy polusta _../target/site/jacoco/index.html_.

### Suoritettavan javan generointi

Komento

```
mvn package
```

Generoi hakemistoon _target_ suoritettavan jar tiedoston _Clicker-1.0-SNAPSHOT.jar_.

Ohjelman testit testaavat myös threadeja, ja on teoreettisesti mahdollista, että testit on hylätty, liiallisen ajan käytön takia thread testeissä.

Jos testit epäonnistuvat on silti mahdollista, että generoitu ohjelma voisi toimia, käytä halutessasi seuraavaa komentoa

```
mvn package -Dmaven.test.skip=true
```

Komento generoi tiedoston samoin kuin aiemmin mainittu.


### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

Generoitu JavaDoc löytyy polusta _../target/site/apidocs/index.html_.

### Checkstyle

Tiedoston [checkstyle.xml](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/Clicker/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset löytyvät tiedostosta _../target/site/checkstyle.html_.