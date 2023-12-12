# Poizvedbe v okviru 5. naloge:

GET v1/igralci?filter=priimekNEQIC:'doe' <br>
GET v1/ocene?filter=ocena:GT:8<br>
GET v1/filmi?filter=naslov:LIKEIC:'shrek%'<br>

[//]: # (filmi med letom 2000 in 2004)
GET v1/filmi?filter=leto_izzida:GT:2000 and leto_izzida:LT:2004<br>

[//]: # (Moski, starejsi od 20 let)
GET v1/uporabniki?filter=starost:GT:20 and spol:EQ:M<br>

[//]: # (moski mlajsi od 10 let)
GET v1/uporabniki?f ilter=starost:LT10 and spol:EQ:M<br>

GET v1/ocene?filter=film:ISNULL<br>
GET v1/filmi?filter=ocena:GT:7<br>
GET v1/uporabniki?filter=spol:EQ:Z<br>

[//]: # (zanri ki se koncajo na a ali na r)
GET v1/zanri?filter=naziv:LIKE:'%a' OR naziv:LIKE:'%r'<br>
GET v1/ocene?filter=komentar:ISNOTNULL and ocena:LTE:5<br>
GET v1/igralci?filter=ime:ISNOTNULL and priimek:LIKEIC:'%son%'<br>