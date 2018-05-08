# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen tarkoitus on olla tietokonepeli, eli siis olla käyttäjälle huvittava tai kiinnostava. Pelin ideana on hallinnoida minimaallisella tasolla pelin rahaa, ja täten saada lisää ja nopeammin peli rahaa.

## Käyttäjät

On vain yksi käyttäjäryhmä, ja kaikki pelin ominaisuudet aukeavat kaikille käyttäjille peliä pelatessa.

## Käyttöliittymäluonnos

Sovelluksella on latausnäkymä, päänäkymä ja käyttäjä voi avata yksi kerrallaan alinäkymiä jotka peittävät osan "hukkatilasta" pää näkymässä.

<img src="https://raw.githubusercontent.com/GourmetHunter/otm-harjoitustyo/master/dokumentaatio/kuvat/kayttoliittyma_luonnos_1.png">

Sovellus aukeaa latausnäkymään, josta se sitten siirtyy automaattisesti päänäkymään. Alinäkymät siis eivät peitä tai estä käyttämästä päänäkymän ominaisuuksia, mutta vain yhtä alinäkymää voi hallinnoida samaan aikaan.

Lataus näkymä pitäisi olla näkyvissä vain erittäin rajaoitetun ajan, suurin osa tästä ajasta kuluu pelin offline ajan simulaatiion, jonka ei normaali tilanteissa pitäis kestää muutamaa sekunttia kauempaa.

## Tarjottu toiminnallisuus

### Perus ominaisuudet

Käyttäjä voi hiljentää pelin äänet ja vaihtaa pelin fullscreen muotoon, pelin eteneminen tallentuu automaattisesti ja peli etenee vaikka pelaaja ei pitäisi sovellusta päällä, tämä toteutetaan käyttäen järjestelmän kelloa.

Pelin _offline_ aikana tapahtuva eteneminen on rajoitettu siten, että jos yhden vastustajan päihittämiseen tasolla kuluisi yli 30 sekunttia, ei tasoissa enää mennä ylöspäin.

### Pelin ominaisuudet

Pelin ideana on kerätä rahaa, ja edetä tasoissa. Tämä kuitenkin voi alkaa tuntua hieman tylsältä, joten pelin voi resetoida, ja täten saada toista pelin valuuttaa jota voi käyttää eri kaupassa kuin normaalisti. Ideana on siis pelata peliä useaan kertaan erittäin pitkälle, että pääsee vielä pidemmälle.

Eteneminen pelissä tapahtuu kahdella eri tavalla, joko klikkaamalla ruutua, tai ajanmyötä. Vastustajalla on luvullinen raja kuinka paljon se kestää _vahinkoa_, ja sen päihittyessä pelaaja saa valuuttaa, jota hän voi käyttää pelin kaupoissa ostaakseensa itselleen entistä vahvemman klikkauksen tai entistä enemmän ajan myötä aiheutuvaa vahinkoa.

Vastustajia päihittäessä rupeaa pelaaja myös saamaan talteen sieluja, joka on pelin toinen valuutta. Nämä sielut eivät kuitenkaan tule suoraan pelaajan käyttöön, vaan peli pitää aiemman mukaan resotoida, että ne ovat pelaajan käytössä. Resetointi tapahtuu kuitenkin vain puoliksi, ja kaikki sielu kaupasta ostetut päivtykset ja sielut itse pysyvät tallessa.

Sieluilla voi ostaa lisää vahinko päivityksiä, ja pelkkä sielujen tallessapitäminen tarjoaa pelaajalle lisää rahapalkkiota pelissä edetessä.

- "Loputon" raha
  - Normaalisti raha rajoittuisi yhteen long tai integer muuttujaan, pelissä kuitenkin on tarkoitus saada sellaisia summia raheeta, että mainitut tietomuodot ovat aivan liian rajallisia, joten on käytettävä jotain erikoistunutta tietomuotoa.
  - Javan tarjoama BigInteger luokka oli tarpeeksi toimiva, oma teutus todettu turhaksi.

- Näkymät
  - Kauppa 1; Normaali raha
  - Kauppa 2; Pelin resettaamalla kerättävä raha
  - Asetukset

## Jatkokehitysideoita

- Lisätään peliin 3 reset vaihtoehto, joka antaa taas uutta valuuttaa sen perusteella, miten paljon pelaajalla on ollut normaali reset valuuttaa -> 4... 5... 6... 7 vaihtoehto.
- Lisää erilaisia asetuksia resoluutioille pelkän fullscreen skaalauksen sijaan.
- Useampi kuin vain 1 tallennus.
- Online ominaisuuksia kuten johtolistat.
- Peli ja sen tietokanta käyttää taso numerona normaalia Integeriä, tämän ei pitäisi sinällään olla ongelma, koska integerin tarjoama määrä tasoja pitäisi olla lähes mahdotonta saavuttaa. Mutta saattaisi olla temaattisesti sopivaa vaihtaa tämäkin luku "loputtomaan" BigIntegeriin.
