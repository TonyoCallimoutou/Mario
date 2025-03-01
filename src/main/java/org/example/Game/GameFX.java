package org.example.Game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.example.Game.Model.Element.Block.Block;
import org.example.Game.Model.Element.Element;
import org.example.Game.Model.LevelGame.LevelGame;
import org.example.Game.Model.LevelGame.Map.GameMap;
import org.example.Game.Model.Player.Mario;
import org.example.Game.Model.Scene.MyScene;
import org.example.Game.Model.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameFX extends Application {
  private Player player;
  private List<Element> elements = new ArrayList<>();

  @Override
  public void start(Stage primaryStage) {

    LevelGame levelGame = new LevelGame(1);

    MyScene myScene = MyScene.getInstance(levelGame.getSizeScreen(), levelGame.getSizeMap());

    player = new Mario(levelGame.getPositionPlayer(), levelGame.getTileSize());

    // Génération de la carte
    GameMap gameMap = levelGame.getMap();

    elements.addAll(gameMap.getElements());

    for (Element element : elements) {
      myScene.addElement(element.getShape());
    }
    myScene.addPlayer(player);

    // Boucle du jeu
    AnimationTimer gameLoop = new AnimationTimer() {
      @Override
      public void handle(long now) {
        player.update();
        myScene.updateCamera(player.getPosition());
        for (Element element : elements) {
          Node shape = element.getShape();
          if (player.getShape().getBoundsInParent().intersects(shape.getBoundsInParent())) {
            if (element instanceof Block) {
              player.impactWithShape(element);
            }
            else {
              player.impactWithShape(element);
            }
          }
        }
      }
    };
    gameLoop.start();

    primaryStage.setTitle("Mario en JavaFX");
    primaryStage.setScene(myScene.getScene());
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
