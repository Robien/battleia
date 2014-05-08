#!/bin/bash

fileIn=""
for file in ./IA*.txt ; do
	fileIn="$fileIn  \"$file.$1.txt\" with linespoints ps 1 lw 3, " 
done


cd parsed

fileout=../out/$1
gnuplot << EOF
set terminal png size 2048,1536 enhanced font "Helvetica, 18"
set output "${fileout}.png"
set pointsize 1
plot ${fileIn}
EOF

cd ..