package org.example.Game.Model.LevelGame.Map;

public enum MapElementEnum {
  EMPTY_00(00, ""),
  GROUND_10(10, "/Element/Ground/ground.png"),
  BLOCK_20(20, "/Element/Block/block.png"),
  BLOCK_21(21, "/Element/Block/block_surprise.png"),
  BLOCK_22(22, "/Element/Block/block_indestructible.png"),
  PIPE_30(30, "/Element/Pipe/pipe.png"),;

  private final int value;
  private final String resource;

  MapElementEnum(int value, String resource) {
    this.value = value;
    this.resource = resource;
  }

  public int getValue() {
    return value;
  }

  public String getResource() {
    return resource;
  }

  public static MapElementEnum getMapElementEnum(int value) {
    for (MapElementEnum mapElementEnum : MapElementEnum.values()) {
      if (mapElementEnum.getValue() == value) {
        return mapElementEnum;
      }
    }
    return null;
  }
}
