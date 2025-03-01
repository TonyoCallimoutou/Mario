package org.example.Game.Model.Element;

import javafx.scene.Node;
import org.example.Game.Model.Utils.Position;

public interface Element {

  Node getShape();

  int getSizeXElement();
  int getSizeYElement();

  Position getPosition();
}
