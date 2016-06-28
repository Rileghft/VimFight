/**
 *
 */
package game.Map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author 楊舜宇
 * @since 2016/6/15
 *
 */
public class GameMap {
	private int lineCount;
	private ArrayList<MapRow> rows;
	public int lineno;
	public int colno;
	public int screenStartRow;
	public int screenStartCol;

	public GameMap(ArrayList<MapRow> mainRows, Integer startRow, Integer startCol) {
		rows = new ArrayList<MapRow>(500);
		for(int row = startRow; row < startRow + 20; ++row) {
			rows.add(mainRows.get(row).getScreenRow(startCol));
		}
		rows.trimToSize();
	}

	public GameMap(BufferedReader mapReader) {
		rows = new ArrayList<MapRow>(500);
		try {
			Integer lineno = 1;
			while(mapReader.ready()) {
				String line = mapReader.readLine();
				MapRow row = new MapRow(line, lineno++);
				rows.add(row);
			}
			mapReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("read text map failed");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("read text map io problem");
		}
		rows.trimToSize();
		screenStartRow = 0;
		screenStartCol = 0;
	}

	private GameMap cutScreenMap(int startRow, int startCol) {
		GameMap screenMap = new GameMap(rows, startRow, startCol);
		screenMap.lineno = startRow;
		screenMap.colno = startCol;
		return screenMap;
	}

	public Integer getLineCount() {
		return lineCount;
	}

	private ArrayList<MapRow> getMapRows() {
		return rows;
	}

	public ArrayList<MapRow> getMapScreenRows() {
		GameMap screenMap = cutScreenMap(screenStartRow, screenStartCol);
		ArrayList<MapRow> screenRows = screenMap.getMapRows();
		return screenRows;
	}

	public void updateScreenMap(Position pos) {
  int nextScreenCol = screenStartCol;
  int nextScreenRow = screenStartRow;
  if(pos.x > screenStartCol + 19) {
     nextScreenCol = pos.x - 1;
  		}
   else if(pos.x < screenStartCol) {
       nextScreenCol = pos.x - 19;
        }
   if(pos.y > screenStartRow + 19) {
       nextScreenRow = pos.y - 1;
        }
   else if(pos.y < screenStartRow) {
       nextScreenRow = pos.y - 19;
        }
   if(nextScreenCol < 0) nextScreenCol = 0;
   if(nextScreenRow < 0) nextScreenRow = 0;
   screenStartCol = nextScreenCol;
   screenStartRow = nextScreenRow;
    }

	public void moveRight(Position pos) {
		int x = pos.x;
		int y = pos.y;
		MapRow row = rows.get(y);
		String line = row.getLineString();
		if(x + 1 < line.length()) {
			if(x + 1 >= screenStartCol + 20) {
				screenStartCol++;
			}
			pos.x++;
		}
	}

	public void moveLeft(Position pos) {
		int x = pos.x;
		if(x - 1 >= 0) {
			if(x - 1 < screenStartCol) {
				screenStartCol--;
			}
			pos.x--;
		}
	}

	public void moveUp(Position pos) {
		int y = pos.y;
		if(y - 1 >= 0) {
			if(y - 1 < screenStartRow) {
				screenStartRow--;
			}
			pos.y--;
			MapRow row = rows.get(y - 1);
			if(pos.x >= row.getLineString().length()) {
				pos.x = row.getLineString().length() - 1;
				if(pos.x < 0) pos.x = 0;
			}
		}
	}

	public void moveDown(Position pos) {
		int x = pos.x;
		int y = pos.y;
		if(y + 1 < rows.size()) {
			if(y + 1 >= screenStartRow + 20) {
				screenStartRow++;
			}
			pos.y++;
			MapRow row = rows.get(y + 1);
			if(pos.x >= row.getLineString().length()) {
				pos.x = row.getLineString().length() - 1;
				if(pos.x < 0) pos.x = 0;
			}
		}
	}

	public void moveNextWord(Position pos) {
		int x = pos.x;
		int y = pos.y;
   int startLineCol = x;
   int nextPosX = x;
   int nextPosY = y;
   Character curChar = rows.get(y).getLineString().charAt(x);
   boolean isBreakPoint = false;

   int curType;
   if(curChar.toString().matches("\\s")) curType = 0;
   else if(curChar.toString().matches("\\w")) curType = 1;
   else curType = 2;

   for(int rowNum = y; rowNum < rows.size(); ++rowNum) {
       MapRow row = rows.get(rowNum);
       String line = row.getLineString();
       boolean isFound = false;
       for (int i = startLineCol; i < line.length(); ++i) {
           Character c = line.charAt(i);
           if (c.toString().matches("\\s")) {
               isBreakPoint = true;
               continue;
           } else if (c.toString().matches("\\w") && c != '_' && curType != 1 || isBreakPoint) {
               isFound = true;
               nextPosX = i;
               nextPosY = rowNum;
               break;
           } else if (c.toString().matches("\\p{Punct}") && curChar != c && curType != 2 || isBreakPoint) {
               isFound = true;
               nextPosX = i;
               nextPosY = rowNum;
               break;
                			}
            	}
      if(isFound) {
          break;
            	}
      startLineCol = 0;
        }
   pos.x = nextPosX;
   pos.y = nextPosY;
	}

	public void movePreWord(Position pos) {
		int x = pos.x;
		int y = pos.y;
   int startLineCol = x;
   int nextPosX = x;
   int nextPosY = y;
   Character curChar = rows.get(y).getLineString().charAt(x);
   boolean isBreakPoint = false;

   int curType;
   if(curChar.toString().matches("\\s")) curType = 0;
   else if(curChar.toString().matches("\\w") && curChar != '_') curType = 1;
   else curType = 2;

   for(int rowNum = y; rowNum >= 0; --rowNum) {
       MapRow row = rows.get(rowNum);
       String line = row.getLineString();
       boolean isFound = false;
       for (int i = startLineCol; i >= 0; --i) {
           Character c = line.charAt(i);
           if (c.toString().matches("\\s")) {
               isBreakPoint = true;
               continue;
           } else if (c.toString().matches("\\w") && c != '_' && (curType != 1 || isBreakPoint)) {
               isFound = true;
               nextPosX = i;
               nextPosY = rowNum;
               curType = 1;
               break;
           } else if (c.toString().matches("\\p{Punct}") && (curChar != c || curType != 2 || isBreakPoint)) {
               isFound = true;
               nextPosX = i;
               nextPosY = rowNum;
               curType = 2;
               break;
                			}
            		}
       if(isFound) {
           int startWordIndex = nextPosX;
           for(int i = nextPosX; i >= 0; --i) {
               Character c = line.charAt(i);
               if (c.toString().matches("\\s")) {
                   startWordIndex = i + 1;
                   break;
               } else if (c.toString().matches("\\w") && c != '_' && curType != 1) {
                   startWordIndex = i + 1;
                   break;
               } else if (c.toString().matches("\\p{Punct}")  && curType != 2) {
                   startWordIndex = i + 1;
                   break;
                    					}
               if(i == 0) nextPosX = 0;
                			}
           nextPosX = startWordIndex;
           break;
            	}
      startLineCol = (rowNum != 0)? rows.get(rowNum - 1).getLineString().length() - 1: rows.get(0).getLineString().length();
        }
   pos.x = nextPosX;
   pos.y = nextPosY;
    }

	public void moveLineBegin(Position pos) {
		pos.x = 0;
	}

	public void moveLineEnd(Position pos) {
		String line = rows.get(pos.y).getLineString();
		pos.x = (line.length() > 0)? line.length() - 1: 0;
	}

}
