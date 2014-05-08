#!/bin/bash


cat $1 | cut -d"/" -f1 > $2.bois.txt
cat $1 | cut -d"/" -f2 > $2.pierre.txt
cat $1 | cut -d"/" -f3 > $2.metal.txt
cat $1 | cut -d"/" -f4 > $2.pop.txt
cat $1 | cut -d"/" -f5 > $2.lvlbucheron.txt
cat $1 | cut -d"/" -f6 > $2.lvlcarriere.txt
cat $1 | cut -d"/" -f7 > $2.lvlmine.txt
cat $1 | cut -d"/" -f8 > $2.lvlferme.txt
cat $1 | cut -d"/" -f9 > $2.temps.txt
cat $1 | cut -d"/" -f10 > $2.tempsCumul.txt
cat $1 | cut -d"/" -f11 > $2.tempsConstruct.txt
cat $1 | cut -d"/" -f12 > $2.tempsPasConstruct.txt
cat $1 | cut -d"/" -f13 > $2.popBucheron.txt
cat $1 | cut -d"/" -f14 > $2.popCarriere.txt
cat $1 | cut -d"/" -f15 > $2.popMine.txt
cat $1 | cut -d"/" -f16 > $2.emplois.txt
cat $1 | cut -d"/" -f17 > $2.prodBois.txt
cat $1 | cut -d"/" -f18 > $2.prodMetal.txt
cat $1 | cut -d"/" -f19 > $2.prodPierre.txt
cat $1 | cut -d"/" -f20 > $2.prodGenerale.txt
