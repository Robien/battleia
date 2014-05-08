#!/bin/bash


for file in ./IA*.txt ; do
fileIn=""

	fileIn="$fileIn  \"$file.bois.txt\" with linespoints ps 1 lw 3, " 
	fileIn="$fileIn  \"$file.pierre.txt\" with linespoints ps 1 lw 3, " 
	fileIn="$fileIn  \"$file.metal.txt\" with linespoints ps 1 lw 3, " 



cd parsed

fileout=../out/$file.res
gnuplot << EOF
set terminal png size 2048,1536  enhanced font "Helvetica, 18"
set output "${fileout}.png"
set pointsize 1
plot ${fileIn}
EOF

cd ..

done