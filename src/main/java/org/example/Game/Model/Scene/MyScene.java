package org.example.Game.Model.Scene;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.example.Game.Model.Player.Player;
import org.example.Game.Model.Utils.Position;
import org.example.Game.Model.Utils.Size;

public class MyScene {

  private static MyScene instance;

  private final Scene scene;

  private final Pane map;

  private Player player;

  private final Size sizeScreen;
  private final Size sizeMap;

  private MyScene(Size sizeScreen, Size sizeMap) {
    this.sizeScreen = sizeScreen;
    this.sizeMap = sizeMap;

    map = new Pane();
    map.setPrefSize(sizeMap.getWidth(), sizeMap.getHeight());
    scene = new Scene(map, sizeScreen.getWidth(), sizeScreen.getHeight());
    scene.setFill(Color.SKYBLUE); // Fond bleu pour le ciel

    // Gestion des touches
    scene.setOnKeyPressed(event -> {
      player.onKeyPressed(event);

    });

    scene.setOnKeyReleased(event -> {
      player.onKeyReleased(event);
    });
  }

  public void addElement(Node shape) {
    map.getChildren().add(shape);
  }

  public void addPlayer(Player player) {
    this.player = player;
    map.getChildren().add(player.getShape());
  }

  public static MyScene getInstance(Size sizeScreen, Size sizeMap) {
    if (instance == null) {
      instance = new MyScene(sizeScreen, sizeMap);
    }
    return instance;
  }

  public Scene getScene() {
    return scene;
  }

  public void updateCamera(Position positionJoueur) {
    double cameraX = positionJoueur.getX() - sizeScreen.getWidth() / 2; // moitié de l'écran

    // Empêcher la caméra d'aller en dehors des limites de la map
    if (cameraX < 0) cameraX = 0;
    if (cameraX > sizeMap.getWidth() - sizeScreen.getWidth()) cameraX = sizeMap.getWidth() - sizeScreen.getWidth();

    map.setLayoutX(-cameraX); // Déplace toute la map
  }

}
