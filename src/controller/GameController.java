package controller;

import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public boolean loadGameFromFile(String path) {
        System.out.println("load game is now");
        try {
            List<String> chessData = Files.readAllLines(Paths.get(path));
            StringBuilder builder = new StringBuilder();
            builder.append(path.charAt(path.length()-3)).append(path.charAt(path.length()-2)).append(path.charAt(path.length()-1));
            if (!builder.toString().equals("txt")){
                return false;
            }
            if (chessboard.loadGame(chessData)){
            chessboard.loadGame(chessData);
            return true;
            }
            else {
                return false;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void saveGameFile(String name){
        chessboard.saveGame(chessboard.getChessComponents(),name);
    }

    public void autoSave() {
        chessboard.autoSave();
    }

}

//选文件、判断读取、换背景、撤回、时间