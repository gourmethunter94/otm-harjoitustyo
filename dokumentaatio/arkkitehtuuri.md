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

## Luokkakaavio

<img src="https://raw.githubusercontent.com/GourmetHunter/otm-harjoitustyo/master/dokumentaatio/kuvat/kaavio.png">

Kaavioon ei ole merkitty staattista luokkaa Commons, jonka Display initialisatioi. Se sisältää geneerisiä muuttujia, joiden kutsulle voi olla hyötyö eri puolilla sovellusta.