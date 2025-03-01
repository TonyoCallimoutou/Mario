package org.example.Game.Model.Player;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.Game.Model.Element.Block.Block;
import org.example.Game.Model.Element.Element;
import org.example.Game.Model.Element.Ground.Ground;
import org.example.Game.Model.Element.Pipe.Pipe;
import org.example.Game.Model.Utils.Position;

public class Goombas implements Element {
  private double playerX;
  private double playerY;

  private final int sizeY;
  private final int sizeX;

  private double velocityX;
  private double velocityY;

  private boolean onGround = false;
  private final double gravity;
  private final double jumpStrength;

  private final double speed;

  private Animation marioAnimation;

  private ImageView shape;

  public Goombas(Position position, int tileSize) {
    this.playerX = position.getX();
    this.playerY = position.getY();
    velocityX = 0;
    velocityY = 0;

    speed = (double) tileSize / 4;
    jumpStrength = (double) -tileSize / 3;
    gravity = 0.5;

    this.sizeX = (int) (tileSize * 1.2);
    this.sizeY = (int) (tileSize * 1.2);

    this.shape = new ImageView();
    this.shape.setFitWidth(sizeX);
    this.shape.setFitHeight(sizeY);
    this.shape.setX(playerX);
    this.shape.setY(playerY);

    setAnimation();
  }

  private void setAnimation() {
    Image idleImageRight = new Image(MarioSprite.MARIO_LITTLE_STAND_RIGHT.getPath());
    Image idleImageLeft = new Image(MarioSprite.MARIO_LITTLE_STAND_LEFT.getPath());
    Image[] runningRightFrames = {
        new Image(MarioSprite.MARIO_LITTLE_STAND_RIGHT.getPath()),
        new Image(MarioSprite.MARIO_LITTLE_RUN_RIGHT_1.getPath()),
    };
    Image[] runningLeftFrames = {
        new Image(MarioSprite.MARIO_LITTLE_STAND_LEFT.getPath()),
        new Image(MarioSprite.MARIO_LITTLE_RUN_LEFT_1.getPath()),
    };
    Image jumpingFrame = new Image(MarioSprite.MARIO_LITTLE_JUMP_RIGHT.getPath());
    Image jumpingRightFrame = new Image(MarioSprite.MARIO_LITTLE_JUMP_RIGHT.getPath());
    Image jumpingLeftFrame = new Image(MarioSprite.MARIO_LITTLE_JUMP_LEFT.getPath());

    marioAnimation = new Animation(shape, idleImageRight, idleImageLeft, runningRightFrames, runningLeftFrames, jumpingFrame, jumpingRightFrame, jumpingLeftFrame);
  }

  private void updateImage() {
    boolean isGoingRight = velocityX > 0;
    boolean isGoingLeft = velocityX < 0;
    boolean isJumping = !onGround;

    if (!isJumping && isGoingRight) {
      marioAnimation.startRunningRight();
    }
    else if (!isJumping && isGoingLeft) {
      marioAnimation.startRunningLeft();
    }
    else if (isJumping && isGoingRight) {
      marioAnimation.jumpRight();
    }
    else if (isJumping && isGoingLeft) {
      marioAnimation.jumpLeft();
    }
    else if (isJumping) {
      marioAnimation.jump();
    }
    else {
      marioAnimation.land();
    }

  }

  public Position getPosition() {
    return new Position(playerX, playerY);
  }

  public Node getShape() {
    return shape;
  }

  public int getSizeXElement() {
    return sizeX;
  }

  public int getSizeYElement() {
    return sizeY;
  }

  public void onKeyPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.RIGHT) {
      velocityX = speed; // Aller à droite
      updateImage();
    }
    if (event.getCode() == KeyCode.LEFT) {
      velocityX = -speed; // Aller à gauche
      updateImage();
    }
    if (event.getCode() == KeyCode.SPACE && onGround) {
      velocityY = jumpStrength; // Sauter
      onGround = false;
      updateImage();
    }
  }

  public void onKeyReleased(KeyEvent event) {
    if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
      velocityX = 0;
      marioAnimation.stopRunning();
    }
  }

  public void update() {
    // Appliquer la gravité
    velocityY += gravity;
    playerX += velocityX;
    playerY += velocityY;


    if (playerX < 0) {
      playerX = 0;
    }

    // Mise à jour de la position
    shape.setX(playerX);
    shape.setY(playerY);
  }

  public void impactWithShape(Element element) {
    if (element instanceof Block || element instanceof Pipe) {

      Position lastPosition = new Position(playerX - velocityX, playerY - velocityY);
      Position currentPosition = new Position(playerX, playerY);
      Position elementPosition = element.getPosition();

      // Taille de l'élément
      int elementWidth = element.getSizeXElement();
      int elementHeight = element.getSizeYElement();

      boolean isGoingDown = lastPosition.getY() < currentPosition.getY();
      boolean isGoingUp = lastPosition.getY() > currentPosition.getY();
      boolean isGoingRight = lastPosition.getX() < currentPosition.getX();
      boolean isGoingLeft = lastPosition.getX() > currentPosition.getX();
      boolean wasOnTop = lastPosition.getY() + sizeY - element.getPosition().getY() <=  gravity;
      boolean wasOnLeft = lastPosition.getX() + sizeX <= element.getPosition().getX();
      boolean wasOnRight = lastPosition.getX() >= element.getPosition().getX() + element.getSizeXElement();
      boolean isOnTop = Math.abs(currentPosition.getY() + sizeY - element.getPosition().getY()) <= gravity;
      boolean isOnLeft = currentPosition.getX() + sizeX == element.getPosition().getX();
      boolean isOnRight = currentPosition.getX() == element.getPosition().getX() + element.getSizeXElement();



      // Falling and touch element
      if ((isGoingDown && wasOnTop && !(isOnRight || isOnLeft)) || isOnTop) {
        playerY = elementPosition.getY() - sizeY; // Pose Mario sur le sol
        velocityY = 0;
        onGround = true;
      }
      // Going Right and touch element
      else if ((isGoingRight && wasOnLeft) || isOnLeft) {
        playerX = elementPosition.getX() - sizeX; // Bloquer à gauche de l'élément
      }
      // Going left and touch element
      else if ((isGoingLeft && wasOnRight) || isOnRight) {
        playerX = elementPosition.getX() + elementWidth; // Bloquer à droite de l'élément
      }
      // Jump and touch element
      else if (isGoingUp) {
        playerY = elementPosition.getY() + elementHeight; // Bloquer sous le bloc
        velocityY = 0;
      }
    }

    if (element instanceof Ground) {
      onGround = true;
      playerY = element.getPosition().getY() - sizeY;
      velocityY = 0;
    }

  }
}
