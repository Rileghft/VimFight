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

	public boolean moveRight(Position pos) {
		int x = pos.x;
		int y = pos.y;
		boolean isUpdateEdge = false;
		MapRow row = rows.get(y);
		String line = row.getLineString();
		if(x + 1 < line.length()) {
			if(x + 1 >= screenStartCol + 20) {
				screenStartCol++;
				isUpdateEdge = true;
			}
			pos.x++;
		}
		return isUpdateEdge;
	}

	public boolean moveLeft(Position pos) {
		int x = pos.x;
		boolean isUpdateEdge = false;
		if(x - 1 >= 0) {
			if(x - 1 < screenStartCol) {
				screenStartCol--;
				isUpdateEdge = true;
			}
			pos.x--;
		}
		return isUpdateEdge;
	}

	public boolean moveUp(Position pos) {
		int y = pos.y;
		boolean isUpdateEdge = false;
		if(y - 1 >= 0) {
			if(y - 1 < screenStartRow) {
				screenStartRow--;
				isUpdateEdge = true;
			}
			pos.y--;
			MapRow row = rows.get(y - 1);
			if(pos.x >= row.getLineString().length()) {
				pos.x = row.getLineString().length() - 1;
			}
		}
		return isUpdateEdge;
	}

	public boolean moveDown(Position pos) {
		int x = pos.x;
		int y = pos.y;
		boolean isUpdateEdge = false;
		if(y + 1 < rows.size()) {
			if(y + 1 >= screenStartRow + 20) {
				screenStartRow++;
				isUpdateEdge = true;
			}
			pos.y++;
			MapRow row = rows.get(y + 1);
			if(pos.x >= row.getLineString().length()) {
				pos.x = row.getLineString().length() - 1;
			}
		}
		return isUpdateEdge;
	}

	public void moveNextWord(Position pos) {
		int x = pos.x;
		int y = pos.y;
	}
}
