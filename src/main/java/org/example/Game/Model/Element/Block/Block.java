package org.example.Game.Model.Element.Block;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.example.Game.Model.Element.Element;
import org.example.Game.Model.Utils.Position;

import java.util.Objects;

public class Block implements Element {
  private final Position position;
  private final ImageView shape;
  private final int sizeX;
  private final int sizeY;

  public Block(Position position, int tileSize, String resource) {
    this.position = position;
    this.sizeX = tileSize;
    this.sizeY = tileSize;


    Image blockImage = new Image(Objects.requireNonNull(getClass().getResource(resource)).toExternalForm());
    this.shape = new ImageView(blockImage);
    this.shape.setFitWidth(sizeX);
    this.shape.setFitHeight(sizeY);
    shape.setX(position.getX());
    shape.setY(position.getY());
  }

  @Override
  public ImageView getShape() {
    return shape;
  }

  @Override
  public int getSizeXElement() {
    return sizeX;
  }

  @Override
  public int getSizeYElement() {
    return sizeY;
  }

  @Override
    public Position getPosition() {
        return position;
    }
}
