package view;


import controller.GameController;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;


    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE ;
    private JLabel statusLabel;


    public void setStatusLabel(JLabel statusLabel){
        this.statusLabel = statusLabel;
    }

    public ClickController getClickController() {
        return clickController;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        repaint();

        initKnightOnBoard(0,1,ChessColor.BLACK);
        initKnightOnBoard(0,CHESSBOARD_SIZE-2,ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE-1,1,ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-2,ChessColor.WHITE);


        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,CHESSBOARD_SIZE -3,ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE -1, 2 ,ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE -1,CHESSBOARD_SIZE -3,ChessColor.WHITE);

        initQueenOnBoard(0, 3 ,ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE-1, 3 ,ChessColor.WHITE);

        initKingOnBoard(0,4,ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE -1 ,4,ChessColor.WHITE);

        for(int j = 0; j<8 ; j++) {
            initPawnOnBoard(1, j, ChessColor.BLACK);
        }
        for(int i = 0; i<8; i++){
            initPawnOnBoard(CHESSBOARD_SIZE-2,i,ChessColor.WHITE);
        }
    }



    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor color){
        this.currentColor = color;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        statusLabel.setText(currentColor + "  " + (ClickController.steps + 1));
    }

    public void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public   void initBishopOnBoard(int row,int col,ChessColor color){
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initQueenOnBoard(int row,int col,ChessColor color){
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initKingOnBoard(int row,int col,ChessColor color){
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initKnightOnBoard(int row,int col,ChessColor color){
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initPawnOnBoard(int row, int col , ChessColor color){
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }
//
    public boolean loadGame(List<String> chessData) {
        initiateEmptyChessboard();
        chessData.forEach(System.out::println);
        if (chessData.size() != 9){
            return false;
        }
        for (int i = 0; i < 8; i++) {
            if (chessData.get(i).length() != 8 ){
            return false;
            }
        }
        int[][] Check = new int[6][2];
        for (int i = 0; i < chessData.size() - 1; i++){
            for (int j = 0; j < chessData.get(i).length(); j++) {
                if (chessData.get(i).charAt(j) == 'R'){
                    initRookOnBoard(i,j,ChessColor.BLACK);
                    Check[3][0]++;
                }
                else if (chessData.get(i).charAt(j) == 'N') {
                    initKnightOnBoard(i,j,ChessColor.BLACK);
                    Check[4][0]++;
                }
                else if (chessData.get(i).charAt(j) == 'B') {
                    initBishopOnBoard(i,j,ChessColor.BLACK);
                    Check[2][0]++;
                }
                else if (chessData.get(i).charAt(j) == 'P') {
                    initPawnOnBoard(i,j,ChessColor.BLACK);
                    Check[5][0]++;
                }
                else if (chessData.get(i).charAt(j) == 'Q') {
                    initQueenOnBoard(i,j,ChessColor.BLACK);
                    Check[1][0]++;
                }
                else if (chessData.get(i).charAt(j) == 'K') {
                    initKingOnBoard(i,j,ChessColor.BLACK);
                    Check[0][0]++;
                }
                else if (chessData.get(i).charAt(j) == 'r'){
                    initRookOnBoard(i,j,ChessColor.WHITE);
                    Check[3][1]++;
                }
                else if (chessData.get(i).charAt(j) == 'b'){
                    initBishopOnBoard(i,j,ChessColor.WHITE);
                    Check[4][1]++;
                }
                else if (chessData.get(i).charAt(j) == 'n'){
                    initKnightOnBoard(i,j,ChessColor.WHITE);
                    Check[2][1]++;
                }
                else if (chessData.get(i).charAt(j) == 'p'){
                    initPawnOnBoard(i,j,ChessColor.WHITE);
                    Check[5][1]++;

                }
                else if (chessData.get(i).charAt(j) == 'q'){
                    initQueenOnBoard(i,j,ChessColor.WHITE);
                    Check[1][1]++;
                }
                else if (chessData.get(i).charAt(j) == 'k'){
                    initKingOnBoard(i,j,ChessColor.WHITE);
                    Check[0][1]++;
                }else if (chessData.get(i).charAt(j) == '_'){
                    continue;
                }else {
                    return false;
                }
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (Check[i][j] > 1){
                    return false;
                }
            }
        }
        for (int i = 2; i <5; i++) {
            for (int j = 0; j < 2; j++) {
                if (Check[i][j] > 2){
                    return false;
                }
            }
        }
        if (Check[5][0] > 8 |Check[5][1] > 8){
            return false;
        }

        if (chessData.get(8).charAt(0) == 'W'){
            setCurrentColor(ChessColor.WHITE);
            statusLabel.setText(currentColor.toString()+ "  " + ClickController.steps);
        }else if (chessData.get(8).charAt(0) == 'B'){
            setCurrentColor(ChessColor.BLACK);
            statusLabel.setText(currentColor.toString()+ "  " + ClickController.steps);
        }
        else {
            return false;
        }
//        int a = Integer.parseInt(chessData.get(9));
//        ClickController.setSteps(a);
        repaint();
        return true;
    }



    public String saveGame(ChessComponent[][] chessComponents,String name){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j]instanceof PawnChessComponent){
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE){
                        builder.append('p');
                    }else {
                        builder.append('P');
                    }
                }
                else if (chessComponents[i][j]instanceof KingChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        builder.append('k');
                    } else {
                        builder.append('K');
                    }
                }
                else if (chessComponents[i][j]instanceof QueenChessComponent){
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE){
                        builder.append('q');
                    }else {
                        builder.append('Q');
                    }
                }
                else if (chessComponents[i][j]instanceof BishopChessComponent){
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE){
                        builder.append('b');
                    }else {
                        builder.append('B');
                    }
                }
                else if (chessComponents[i][j]instanceof KnightChessComponent){
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE){
                        builder.append('n');
                    }else {
                        builder.append('N');
                    }
                }
                else if (chessComponents[i][j]instanceof RookChessComponent){
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE){
                        builder.append('r');
                    }else {
                        builder.append('R');
                    }
                }
                else {
                    builder.append('_');
                }
            }
            builder.append('\n');
        }
        if (currentColor == ChessColor.WHITE){
            builder.append("W");
        }else {
            builder.append("B");
        }
//        builder.append(ClickController.steps);

        write(builder.toString(),name);

        return builder.toString();
    }

    // 这个方法用来把String写入txt
    private void write(String save, String name) {
        try {
            File file = new File("C:/Users/HP/IdeaProjects/NewProject/resource");
            file.createNewFile();
            FileWriter writer = new FileWriter("C:/Users/HP/IdeaProjects/NewProject/resource/" + name + ".txt");
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(String.valueOf(save));
            bw.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void autoSave(){
        int a = 0;
        if (a != ClickController.steps){
            saveGame(chessComponents,"saveboard"+ ClickController.steps);
            a = ClickController.steps;
        }
    }



}
