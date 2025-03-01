package org.example.Game.Model.Player;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Animation {

  private final ImageView imageView;
  private final Image idleImageRight;
  private final Image idleImageLeft;
  private final Image[] runningRightFrames;
  private final Image[] runningLeftFrames;
  private final Image jumpingFrame;
  private final Image jumpingRightFrame;
  private final Image jumpingLeftFrame;

  boolean wasGoingRight = true;

  private int currentFrame = 0;
  private long lastFrameTime = 0;
  private final long frameDuration = 150_000_000; // 150ms entre chaque frame

  private final AnimationTimer animationTimer;
  private Image[] currentAnimationFrames;

  public Animation(ImageView imageView, Image idleImageRight, Image idleImageLeft,
                   Image[] runningRightFrames, Image[] runningLeftFrames,
                   Image jumpingFrame, Image jumpingRightFrame, Image jumpingLeftFrame) {
    this.imageView = imageView;
    this.idleImageRight = idleImageRight;
    this.idleImageLeft = idleImageLeft;
    this.runningRightFrames = runningRightFrames;
    this.runningLeftFrames = runningLeftFrames;
    this.jumpingFrame = jumpingFrame;
    this.jumpingRightFrame = jumpingRightFrame;
    this.jumpingLeftFrame = jumpingLeftFrame;

    this.imageView.setImage(idleImageRight);

    this.animationTimer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (now - lastFrameTime >= frameDuration) {
          lastFrameTime = now;
          updateFrame();
        }
      }
    };
  }

  private void updateFrame() {
    if (currentAnimationFrames == null || currentAnimationFrames.length == 0) return;

    currentFrame = (currentFrame + 1) % currentAnimationFrames.length;
    imageView.setImage(currentAnimationFrames[currentFrame]);
  }

  public void startRunningRight() {
    wasGoingRight = true;
    currentAnimationFrames = runningRightFrames;
    currentFrame = 0;
    animationTimer.start();
  }

  public void startRunningLeft() {
    wasGoingRight = false;
    currentAnimationFrames = runningLeftFrames;
    currentFrame = 0;
    animationTimer.start();
  }

  public void stopRunning() {
    animationTimer.stop();
    imageView.setImage(wasGoingRight ? idleImageRight : idleImageLeft);
  }

  public void jump() {
    animationTimer.stop();
    imageView.setImage(jumpingFrame);
  }

  public void jumpRight() {
    wasGoingRight = true;
    animationTimer.stop();
    imageView.setImage(jumpingRightFrame);
  }

  public void jumpLeft() {
    wasGoingRight = false;
    animationTimer.stop();
    imageView.setImage(jumpingLeftFrame);
  }

  public void land() {
    imageView.setImage(wasGoingRight ? idleImageRight : idleImageLeft);
  }
}
