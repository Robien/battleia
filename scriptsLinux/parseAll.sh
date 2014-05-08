#!/bin/bash

rm -rf ./parsed
mkdir parsed
rm -rf ./out
mkdir out

for file in ./IA*.txt ; do
	./parse.sh $file parsed/$file
done


./plotOne.sh bois
./plotOne.sh pierre
./plotOne.sh metal
./plotOne.sh pop
./plotOne.sh lvlbucheron
./plotOne.sh lvlcarriere
./plotOne.sh lvlmine
./plotOne.sh lvlferme
./plotOne.sh temps
./plotOne.sh tempsCumul
./plotOne.sh tempsConstruct
./plotOne.sh tempsPasConstruct
./plotOne.sh popBucheron
./plotOne.sh popCarriere
./plotOne.sh popMine
./plotOne.sh emplois
./plotOne.sh prodBois
./plotOne.sh emplois
./plotOne.sh prodMetal
./plotOne.sh prodPierre
./plotOne.sh prodGenerale

./plotRes.sh
