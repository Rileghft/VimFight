/**
 *
 */
package vimControl.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Input.Keys;

import vimControl.GameKeys;
import vimControl.VimControl;
import vimControl.VimMode;

/**
 * @author 楊舜宇
 * @since 2016/5/28
 */
public class TestVimMode {

	public VimControl vim;
	@Before
	public void setUp() throws Exception {
		vim = new VimControl();
	}

	@Test
	public void NormalToInsert() {
		VimControl vim = new VimControl();
		vim.inputKey('i');
		assertEquals(VimMode.INSERT, vim.getCurrentState());
	}

	@Test
	public void NormalTOCommand() {
		VimControl vim = new VimControl();
		vim.inputKey(':');
		assertEquals(VimMode.COMMAND, vim.getCurrentState());
	}

	@Test
	public void InsertTONormal() {
		VimControl vim = new VimControl();
		vim.inputKey('i');
		vim.inputKey((char)GameKeys.ESC);
		assertEquals(VimMode.NORMAL, vim.getCurrentState());
	}

	@Test
	public void CommandTONormal() {
		VimControl vim = new VimControl();
		vim.inputKey((char)GameKeys.SEMICOLON);
		vim.inputKey((char)GameKeys.ESC);
		assertEquals(VimMode.NORMAL, vim.getCurrentState());
	}

	@Test
	public void TestInsertMode() {
		VimControl vim = new VimControl();
		vim.inputKey((char)GameKeys.i);
		assertEquals(VimMode.INSERT, vim.getCurrentState());
		vim.inputKey((char)GameKeys.i);
		vim.inputKey((char)GameKeys.a);
		vim.inputKey((char)GameKeys.k);
		assertEquals(VimMode.INSERT, vim.getCurrentState());
		vim.inputKey((char)GameKeys.o);
		vim.inputKey((char)GameKeys.d);
		assertEquals(VimMode.INSERT, vim.getCurrentState());
		vim.inputKey((char)GameKeys.ESC);
		assertEquals(VimMode.NORMAL, vim.getCurrentState());
	}
	@Test
	public void TestCommandMode() {
		VimControl vim = new VimControl();
		vim.inputKey((char)GameKeys.SEMICOLON);
		assertEquals(VimMode.COMMAND, vim.getCurrentState());
		vim.inputKey((char)GameKeys.i);
		vim.inputKey((char)GameKeys.a);
		vim.inputKey((char)GameKeys.k);
		vim.inputKey((char)GameKeys.ESC);
		assertEquals(VimMode.NORMAL, vim.getCurrentState());
		vim.inputKey((char)GameKeys.SEMICOLON);
		assertEquals(VimMode.COMMAND, vim.getCurrentState());
		vim.inputKey((char)GameKeys.o);
		vim.inputKey((char)GameKeys.l);
		vim.inputKey((char)GameKeys.p);
		vim.inputKey((char)GameKeys.a);
		assertEquals(VimMode.COMMAND, vim.getCurrentState());
		vim.inputKey((char)GameKeys.ENTER);
		assertEquals(VimMode.NORMAL, vim.getCurrentState());
	}
}
