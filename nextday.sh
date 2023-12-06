#!/bin/zsh

padded=`echo ${(l:2::0:)1}`

if [[ ! -a src/main/kotlin/Day${padded}.kt ]]; then
  sed -e "s/1/$1/g" src/main/kotlin/Template.kt > src/main/kotlin/Day${padded}.kt
fi

mkdir -p input

touch input/d${padded}p00.txt
touch input/d${padded}p01.txt
