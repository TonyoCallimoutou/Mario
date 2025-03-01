package org.example.Game.Model.LevelGame.Map;

import org.example.Game.Model.Element.Block.Block;
import org.example.Game.Model.Element.Element;
import org.example.Game.Model.Element.Ground.Ground;
import org.example.Game.Model.Element.Pipe.Pipe;
import org.example.Game.Model.Utils.Position;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
  private final int tileSize;
  private final int[][] tileMap;

  private final List<Integer> enumBigElement = List.of(MapElementEnum.PIPE_30.getValue());

  private List<Element> elements = new ArrayList<>();

  public GameMap(int tileSize, int[][] tileMap) {
    this.tileSize = tileSize;
    this.tileMap = tileMap;
    generateMap();
  }

  public void generateMap() {
    boolean[][] processed = new boolean[tileMap.length][tileMap[0].length]; // Marquer les cases déjà utilisées

    for (int row = 0; row < tileMap.length; row++) {
      for (int column = 0; column < tileMap[row].length; column++) {
        if (processed[row][column]) {
          continue;
        }
        MapElementEnum tileType = MapElementEnum.getMapElementEnum(tileMap[row][column]);

        if( tileType == null || tileType == MapElementEnum.EMPTY_00) {
          continue;
        }

        if (enumBigElement.contains(tileType.getValue())) {
          Element element = generateBigElement(column, row, tileType, processed);
          if (element != null) {
            elements.add(element);
          }
        }
        else {
          processed[row][column] = true;

          Element element = createElement(tileType, (column) * tileSize, ((row) * tileSize));
          if (element != null) {
            elements.add(element);
          }
        }
      }
    }
  }

  private Element generateBigElement(int column, int row, MapElementEnum mapElementEnum, boolean[][] processed) {
    int width = 1, height = 1;
    // Vérifier la largeur du tuyau
    while (column + width < tileMap[row].length && tileMap[row][column + width] == mapElementEnum.getValue()) {
      processed[row][column + width] = true; // Marquer comme traité
      width++;
    }

    // Vérifier la hauteur du tuyau
    while (row + height < tileMap.length && tileMap[row + height][column] == mapElementEnum.getValue()) {
      for (int w = 0; w < width; w++) { // Marquer toute la largeur comme prise
        processed[row + height][column + w] = true;
      }
      height++;
    }

    return createBigElement(mapElementEnum, column * tileSize, row * tileSize, width, height);
  }

  public int getHeight() {
    return tileMap.length * tileSize;
  }

  public int getWidth() {
    return tileMap[0].length * tileSize;
  }

  public List<Element> getElements() {
    return elements;
  }

  private Element createElement(MapElementEnum tileType, int x, int y) {
    switch (tileType) {
      case GROUND_10:
        return new Ground(new Position(x, y), tileSize, tileType.getResource());
      case BLOCK_20:
      case BLOCK_21:
      case BLOCK_22:
        return new Block(new Position(x, y), tileSize, tileType.getResource());
      default:
        return null;
    }
  }

  private Element createBigElement(MapElementEnum tileType, int x, int y, int width, int height) {
    switch (tileType) {
      case PIPE_30:
        return new Pipe(new Position(x, y), tileSize, width, height, tileType.getResource());
      default:
        return null;
    }
  }
}
