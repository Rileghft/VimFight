/**
 *
 */
package game.Map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author 楊舜宇
 * @since 2016/6/15
 *
 */
public class MapRow implements Iterable<MapSquare>{
	private String line;
	private Integer lineno;
	ArrayList<MapSquare> lineSquares;

	public MapRow(ArrayList<MapSquare> squares, Integer startCol, Integer lineno) {
		this.lineno = lineno;
		StringBuilder builder = new StringBuilder();
		lineSquares = new ArrayList<MapSquare>(20);
		for(int i = startCol; i < squares.size() && i < startCol + 20; ++i) {
			lineSquares.add(squares.get(i));
			builder.append(squares.get(i).getChar());
		}
		line = builder.toString();
	}

	public MapRow(String lineStr, Integer lineno) {
		line = lineStr;
		this.lineno = lineno;
		lineSquares = new ArrayList<MapSquare>(line.length());
		for(int i = 0; i < line.length(); ++i) {
			lineSquares.add(new MapSquare(line.charAt(i)));
		}
		if(lineSquares.isEmpty()) {
			lineSquares.add(new MapSquare(' '));
			line = " ";
		}
	}

	public MapRow getScreenRow(Integer startCol) {
		MapRow subRow = new MapRow(lineSquares, startCol, lineno);
		return subRow;
	}

	public MapSquare getSquare(int index) {
		return lineSquares.get(index);
	}

	public void removeSquare(Integer index) {
		lineSquares.remove(index);
	}

	public void modifySquare(Integer index, Character c) {
		lineSquares.get(index).setChar(c);
	}

	public void insertSquare(Integer index, Character c) {
		lineSquares.add(index, new MapSquare(c));
	}

	public String getLineString() {
		return line;
	}

	public Integer getLineno() {
		return lineno;
	}

	@Override
	public Iterator<MapSquare> iterator() {
		Iterator<MapSquare> it = new Iterator<MapSquare>() {
			private int current_index = 0;

			@Override
			public boolean hasNext() {
				return current_index < lineSquares.size() && lineSquares.get(current_index) != null;
			}

			@Override
			public MapSquare next() {
				return lineSquares.get(current_index++);
			}
		};
		return it;
	}

	@Override
	public void forEach(Consumer<? super MapSquare> action) {
		lineSquares.forEach(action);
	}
}
