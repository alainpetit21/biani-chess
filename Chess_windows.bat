@echo off

java -classpath "dist;dist/lib;dist/Chess.jar" -Dsun.java2d.noddraw=true -Djava.library.path="dist;dist/lib/windows;dist/Chess.jar" com.bianisoft.games.chess.AppChess
