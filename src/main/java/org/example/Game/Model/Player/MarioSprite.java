package org.example.Game.Model.Player;

public enum MarioSprite {
  // LITTLE MARIO
  MARIO_LITTLE_STAND_LEFT("/player/mario/little_left.png"),
  MARIO_LITTLE_STAND_RIGHT("/player/mario/little_right.png"),
  MARIO_LITTLE_RUN_LEFT_1("/player/mario/little_run_left.png"),
  MARIO_LITTLE_RUN_RIGHT_1("/player/mario/little_run_right.png"),
  MARIO_LITTLE_JUMP_LEFT("/player/mario/little_jump_left.png"),
  MARIO_LITTLE_JUMP_RIGHT("/player/mario/little_jump_right.png"),

  // BIG MARIO
  MARIO_STAND_LEFT("/player/mario/left.png"),
  MARIO_STAND_RIGHT("/player/mario/right.png"),
  MARIO_RUN_LEFT_1("/player/mario/run_left.png"),
  MARIO_RUN_RIGHT_1("/player/mario/run_right.png"),
  MARIO_JUMP_LEFT("/player/mario/jump_left.png"),
  MARIO_JUMP_RIGHT("/player/mario/jump_right.png");


  private String path;

  MarioSprite(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }

}
