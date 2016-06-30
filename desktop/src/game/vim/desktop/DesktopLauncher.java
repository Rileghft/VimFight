package game.vim.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.vim.VimFight;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "vim fighter";
		config.width = 850;
		config.height = 700;
		config.useGL30 = true;
		config.resizable = false;
		new LwjglApplication(new VimFight(), config);
	}
}
