
import java.awt.Color;

public interface GameSettings {
	int B_WIDTH = 400;
	int B_HEIGHT = 400;
	int X_MARGIN = input.getXBoardSize();
	int Y_MARGIN = input.getYBoardSize();

	boolean debugMode = true;
	int deathCountValue = 10;

	int windowSizeY = 500;
	int windowSizeX = 400;

	int numberOfWolfs = input.getNumberOfWolfs();
	int numberOfSheeps = input.getNumberOfSheep();

	int max_X = 20;
	int max_Y = 20;

	int GAME_SPEED = input.getSpeed();
	int BOARD_REFRESH_RATE = 50;

	Color SHEEP_COLOR = new Color(255, 255, 255);
	Color WOLF_COLOR = Color.BLACK;
}
