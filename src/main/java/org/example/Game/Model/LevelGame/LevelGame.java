package org.example.Game.Model.LevelGame;

import org.example.Game.Model.LevelGame.Map.GameMap;
import org.example.Game.Model.Utils.Position;
import org.example.Game.Model.Utils.Size;

import java.util.List;

public class LevelGame {
  private final GameMap gameMap;

  private final Size sizeMap;
  private final Size sizeScreen;

  private final Position positionPlayer;

  private final int tileSize;

  public LevelGame(int level) {

    switch (level) {
      case 1:
        List<SousLevelMap> sousLevelMap1 =
            List.of(
                SousLevelMap.LEVEL_1_1,
                SousLevelMap.LEVEL_1_2,
                SousLevelMap.LEVEL_1_3,
                SousLevelMap.LEVEL_1_4,
                SousLevelMap.LEVEL_1_5);

        List<int[][]> mapLevel1 = sousLevelMap1.stream()
            .map(SousLevelMap::getMap)
            .toList();

        tileSize = 40;

        gameMap = new GameMap(tileSize,concatenateMatrices(mapLevel1));
        this.sizeMap = new Size(gameMap.getWidth(), gameMap.getHeight());
        this.sizeScreen = new Size(gameMap.getHeight() * 2, gameMap.getHeight());
        this.positionPlayer = new Position(50, 200);
        break;
      default:
        throw new RuntimeException("Invalid level");
    }
  }

  private int[][] concatenateMatrices(List<int[][]> listMap) {
    if (listMap.isEmpty()) return new int[0][0]; // Retourner une matrice vide si aucune matrice donnée

    int rows = listMap.getFirst().length; // Nombre de lignes (doivent être identiques pour toutes les matrices)
    int totalCols = listMap.stream().mapToInt(m -> m[0].length).sum(); // Somme des colonnes de toutes les matrices

    int[][] result = new int[rows][totalCols];

    int colIndex = 0;
    for (int[][] matrix : listMap) {
      int cols = matrix[0].length;
      for (int i = 0; i < rows; i++) {
        System.arraycopy(matrix[i], 0, result[i], colIndex, cols);
      }
      colIndex += cols; // Déplacer l'index de colonne pour la prochaine matrice
    }

    return result;
  }

  public GameMap getMap() {
    return gameMap;
  }

  public Size getSizeMap() {
    return sizeMap;
  }

  public Size getSizeScreen() {
    return sizeScreen;
  }

  public Position getPositionPlayer() {
    return positionPlayer;
  }

  public int getTileSize() {
    return tileSize;
  }


}
