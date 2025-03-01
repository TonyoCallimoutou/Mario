package org.example.Game.Model.Player;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import org.example.Game.Model.Element.Element;
import org.example.Game.Model.Utils.Position;

public interface Player {

  Node getShape();

  void onKeyPressed(KeyEvent event);

  void onKeyReleased(KeyEvent event);

  void impactWithShape(Element element);

  void update();

  Position getPosition();

  Animation getAnimation();
}
