# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen tarkoitus on olla tietokonepeli, eli siis olla käyttäjälle huvittava tai kiinnostava. Pelin ideana on hallinnoida minimaallisella tasolla pelin rahaa, ja täten saada peli rahaa.

## Käyttäjät

On vain yksi käyttäjäryhmä, ja kaikki pelin ominaisuudet aukeavat kaikille käyttäjille peliä pelatessa.

## Käyttöliittymäluonnos

Sovelluksella on latausnäkymä, päänäkymä ja käyttäjä voi avata yksi kerrallaan alinäkymiä jotka peittävät osan "hukka tilasta" pää näkymässä.

<img src="https://raw.githubusercontent.com/GourmetHunter/otm-harjoitustyo/master/dokumentaatio/kuvat/kayttoliittyma_luonnos_1.png">

Sovellus aukeaa latausnäkymään, josta se sitten siirtyy automaattisesti päänäkymään. Alinäkymät siis eivät peitä tai estä käyttämästä päänäkymän ominaisuuksia, mutta vain yhtä alinäkymää voi hallinnoida samaan aikaan.

## Tarjottu toiminnallisuus

### Perus ominaisuudet

Käyttäjä voi hiljentää pelin äänet ja vaihtaa pelin fullscreen muotoon, pelin kehitys tallentuu automaattisesti. Peli "etenee" vaikka pelaaja pitäisi sovellusta päällä, tämä toteutetaan käyttäen järjestelmän kelloa.

### Pelin ominaisuudet

Pelin ideana on kerätä rahaa, ja edetä tasoissa. Tämä kuitenkin voi alkaa tuntua hieman tylsältä, joten pelin voi resetoida, ja täten saada erikois valuuttaa jota voi käyttää toisessa pelin kaupassa. Ideana on siis pelata peliä useaan kertaan erittäin pitkälle, että pääsee vielä pidemmälle.

- "Loputon" raha
  - Normaalisti raha rajoittuisi yhteen long tai integer muuttujaan, pelissä kuitenkin on tarkoitus saada sellaisia summia raheeta, että mainitut tietomuodot ovat aivan liian rajallisia, joten toteutetaan erittäinpaljon laajempi tietotyyppi.
  - Copper -> Silver -> Gold -> Platinum -> Diamond -> ... Eli ideana on tehdä olio jossa on taulukko integereitä tai longeja, ja toimivia matemaattisia operaatioita tälle oliolle.

- Näkymät
  - Kauppa 1; Normaali raha
  - Kauppa 2; Pelin resettaamalla kerättävä raha
  - Asetukset

## Jatkokehitys ideoita

- Lisätään peliin 3 reset vaihtoehto, joka antaa taas uutta valuuttaa sen perusteella, miten paljon pelaajalla on ollut normaali reset valuuttaa -> 4... 5... 6... 7 vaihtoehto.
- Lisää erilaisia asetuksia resoluutioille pelkän fullscreen skaalauksen sijaan.
- Useampi kuin vain 1 tallennus.
- Online ominaisuuksia kuten johtolistat.
