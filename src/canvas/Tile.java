/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvas;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Obituary
 */
public class Tile extends StackPane {

    public int x, y;
    public boolean hasBomb;
    public boolean isOpen = false;
    // border of our tile, square
    private Rectangle border = new Rectangle(Main.TILE_SIZE - 2, Main.TILE_SIZE - 2);
    public Text text = new Text();

    public Tile(int x, int y, boolean hasBomb) {

        this.x = x;
        this.y = y;
        this.hasBomb = hasBomb;

        border.setStroke(Color.LIGHTGRAY);
        text.setFont(Font.font(18));
        // if it hasBomb write X else "" 
        text.setText(hasBomb ? "X" : "");
        text.setVisible(false);

        getChildren().addAll(border, text);

        // position the tiles 
        setTranslateX(x * Main.TILE_SIZE);
        setTranslateY(y * Main.TILE_SIZE);

        setOnMouseClicked(e -> open());
    }

    public void open() {
        
        Main main = new Main();
        
        if (isOpen) {
            return;
        }

        if (hasBomb) {
            System.out.println("Game Over");
            Main.scene.setRoot(Main.createContent());
            return;
        }

        isOpen = true;
        text.setVisible(true);
        border.setFill(null);

        if (text.getText().isEmpty()) {
            Main.getNeighbors(this).forEach(Tile::open);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isHasBomb() {
        return hasBomb;
    }

    public void setHasBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }

    public boolean isIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
