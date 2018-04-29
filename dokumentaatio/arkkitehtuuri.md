# Arkkitehtuurikuvaus

## Käyttöliittymä

Käyttöliittymä sisältää kaksi erillistä päänäkymää (Vain toinen on tällähetkellä toteutettu). Ja alinäkymiä jotka ovat osana toista päänäkymää.

## Käyttäjät

On vain yksi käyttäjäryhmä, ja kaikki pelin ominaisuudet aukeavat kaikille käyttäjille peliä pelatessa.

## Käyttöliittymäluonnos

Sovelluksella on latausnäkymä, päänäkymä ja käyttäjä voi avata yksi kerrallaan alinäkymiä jotka peittävät osan "hukka tilasta" pää näkymässä.

- Lataus Näkymä
- Peli Näkymä
  - Normaali Kauppa
  - Erikois Kauppa
  - Asetukset

## Sovelluslogiikka

Sovelluksen looginen mallin muodostuu luokan [Game](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/Clicker/src/main/java/com/mycompany/clicker/core/Game.java) ympärille.

Game hallinnoi kaikkia tapahtumia, ja erinäiset luokat sisältävät niiden toteutuksia.

 - [Loader](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/Clicker/src/main/java/com/mycompany/clicker/core/Loader.java) hallinnoi osaa initialisaatiosta ja pelin offline ajan simuloinnista.
 - [UIManager](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/Clicker/src/main/java/com/mycompany/clicker/core/UIManager.java) hallinnoi pelin graaffista käyttöliittymää ja sen interaktiivisa toimintoja.
 - [Creature](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/Clicker/src/main/java/com/mycompany/clicker/domain/Creature.java) hallinnoi osaa pelin etenemisestä.

Game luokan tarkempia tehtäviä ovat esimerkiksi seurata onko peli valmis siirtymään seuraavalle tasolle, ja tasojen navigointia.

## Tietojen tallennus

Pakkauksesta clicker.dao löytyy luokka [Database](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/Clicker/src/main/java/com/mycompany/clicker/dao/Database.java), ja sen avulla toimivat luokat [SaveDAO](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/Clicker/src/main/java/com/mycompany/clicker/dao/SaveDAO.java), [SettingsDAO](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/Clicker/src/main/java/com/mycompany/clicker/dao/SettingsDAO.java) ja [UpgradeDAO](https://github.com/GourmetHunter/otm-harjoitustyo/blob/master/Clicker/src/main/java/com/mycompany/clicker/dao/UpgradeDAO.java), jotka vastaavaat tiedon tallettamisesta.

Databaseen ja DAO:hin pääsee käsiksi staattisen Commons luokan avulla. DAO luokkien toteutus on erillään muusta sovelluksesta ja kunhan metodien nimet pysyvät ennallaan, on niiden toteutusta helppo muunnella.

Database on connection luodaan SQLite connectorion avulla.

Vaikkakin pelissä on vain yksi tallennus, on valittu tallennus tavaksi database, sillä haluttaessa laajennus monille tallennuksille näin erittäin helppoa.

## Luokkakaavio

<img src="https://raw.githubusercontent.com/GourmetHunter/otm-harjoitustyo/master/dokumentaatio/kuvat/kaavio.png">

Kaavioon ei ole merkitty staattista luokkaa Commons, jonka Display initialisatioi. Se sisältää geneerisiä muuttujia, joiden kutsulle voi olla hyötyö eri puolilla sovellusta.

## Pelin päivitys, repeat cycle / tick / update

<img src="https://raw.githubusercontent.com/GourmetHunter/otm-harjoitustyo/master/dokumentaatio/kuvat/gametickupdate.png">

Pelin päivittyminen tällä hetkellä.