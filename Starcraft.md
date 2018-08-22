#Vertonung von Starcraft 2 Replaydateien

Zunächsteinmal wird die Replaydatei mithilfe zweier Pythonscripts und der sc2reader-library analysiert und die Events gefiltert. Danach werden OSC-Messages in Echtzeit mit pythonosc an Supercollider gesendet. 

Der Supercolliderteil besteht hauptsächlich aus 3 Klassen, die sowohl für das Eventhandling, als auch für die Klangerzeugung verantwortlich sind. 

##SC2Game
Diese Klasse repräsentiert das ganze Spiel an sich. Sie bestimmt, wie mit den OSC-Messages verfahren wirdund bereitet die Synthdefs und den Server vor. Weiterhin beinhaltet sie auch Klassen, die die Spiele repräsentieren
r
##SC2Player
Repräsentiert einen Spieler und enthält alle informationen, die von Events, die Spieler betreffen empfangen werden. Momentan managed die Klasse hauptsächlich nur seine Einheiten. Daten wie APM, Position oder momentane Auswahl werden noch nicht verwendet.

##SC2Unit
Einheiten werden durch die Klasse SC2Unit repräsentiert. Diese sind momentan für die gesamte Klangerzeugung zuständig.

##Klangerzeugung
Klänge werden durch die Klasse SC2Unit erzeugt. Dabei gibt es für jede Rasse und Einheitentyp unterschiedliche Klänge. Somit sind momentan bis zu 9 verschiedene Klänge möglich. Hierbei wird der Gesamtklang durch das Mengenerhältniss der verschiedenen Einheitentypen zueinander bestimmt. Zusätzlich werden die einzelnen Klänge auch noch mithilfe von Ambisonics, abhängig von ihrer Position, verteilt