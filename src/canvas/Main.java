/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvas;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Obituary
 */
public class Main extends Application {

    public static int TILE_SIZE = 40;
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    // Horizontal 
    public static int X_TILES = WIDTH / TILE_SIZE;
    // Vertical
    public static int Y_TILES = HEIGHT / TILE_SIZE;
    public static Scene scene;

    // declare our grid 
    public static Tile[][] grid = new Tile[X_TILES][Y_TILES];

    public static Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        // create/generate tiles with bombs 20% of the time
        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = new Tile(x, y, Math.random() < 0.2);

                // assign to the grid 
                grid[x][y] = tile;
                // add the tile to the children to the root so it can be added to the layout displayed on the screen
                root.getChildren().add(tile);
            }
        }

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_TILES; x++) {
                Tile tile = grid[x][y];
                // set bombs 

                if (tile.hasBomb) {
                    continue;
                }

                // calculate number of bombs 
                // long because stream() returns a long
                // We've obtained a list of neighbors of that tile that we're going through
                // we've obtained a stram of elements then we've filtered thee elements: neighbours 
                // if the neighbours has a bomb then leave it in the stream or discard
                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();
                
                if(bombs > 0 )
                    tile.text.setText(String.valueOf(bombs));
            }
        }

        return root;
    }

    public static List<Tile> getNeighbors(Tile tile) {

        List<Tile> neighbors = new ArrayList<>();

        int[] points = new int[]{
            // 8 neighbors 3 and 3 on top and bottom an 1 and 1 on left and right

            // ttt
            // tXt
            // ttt

            // top left neighbor
            -1, -1,
            // left
            -1, 0,
            // bottom left 
            -1, 1,
            // top
            0, -1,
            // bottom
            0, 1,
            // top right 
            1, -1,
            // right 
            1, 0,
            // bottom right 
            1, 1

        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if (newX >= 0 && newX < X_TILES && newY >= 0 && newY < Y_TILES) {
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;

    }
    /*
    public class Tile extends StackPane {

        private int x, y;
        private boolean hasBomb;
        private boolean isOpen = false;
        // border of our tile, square
        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
        private Text text = new Text();

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
            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);
            
            setOnMouseClicked(e -> open());

        }
        public void open(){
            if (isOpen)
                return;
            
            if(hasBomb){
                System.out.println("Game Over");
                scene.setRoot(createContent());
                return;
            }
            
            isOpen = true; 
            text.setVisible(true);
            border.setFill(null);
            
            if (text.getText().isEmpty()){
                getNeighbors(this).forEach(Tile::open);
            }
        }
    }
    */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        scene = new Scene(createContent());

        window.setTitle("Canvas in Java FX");
        window.setScene(scene);
        window.show();

    }

}
