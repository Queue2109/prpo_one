to-do diagram

# entitete
**Film:**
- id (_primarni ključ_)
- naslov
- žanr (_tuj ključ_)
- ocena
- režiser, glavni igralci (cast) (_tuj ključ_)
- leto izida

**Uporabnik:**
- id (_primarni ključ_)
- uporabniško ime
- mail
- geslo
- starost
- spol
- všečkani filmi (_tuj ključ_)

**Ocena:**
- id (_primarni ključ_)
- id uporabnika (_tuj ključ_)
- id filma (_tuj ključ_)
- ocena
- komentar
- čas oddaje komentarja

**Žanr:**
- id (_primarni ključ_)
- naziv

**Cast:**
- id (_primarni ključ_)
- ime
- priimek

![diagram](./diagram.png)

