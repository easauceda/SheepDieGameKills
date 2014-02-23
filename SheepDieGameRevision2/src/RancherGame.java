import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class RancherGame extends JFrame implements GameSettings {
	private boolean wolfIsPushed = false;
	private RangeCanvas rangeCanvas = new RangeCanvas();

	private JButton huntButton = new JButton("hunt");
	private ArrayList<Wolf> wolfs = new ArrayList<Wolf>();
	private ArrayList<Sheep> sheeps = new ArrayList<Sheep>();
	private settings set = new settings();

	private final Timer timer;

	private void finish() {
		timer.stop();
	}

	private void updatePositions() {

		rangeCanvas.repaint();
	}

	public RancherGame() {
		new EditSheepLocations(sheeps, wolfs);
		// layout etc
		JPanel panel = new JPanel();
		add(rangeCanvas);
		GridLayout layout = new GridLayout(4, 2);
		layout.setVgap(20);
		layout.setHgap(20);
		panel.setLayout(layout);
		panel.add(new JLabel());
		panel.add(huntButton);
		panel.add(new JLabel("Number of Sheep"));
		panel.add(new JTextField(String.valueOf(set.getSheep())));
		panel.add(new JLabel("Number of Wolves"));
		panel.add(new JTextField(String.valueOf(set.getWolves())));
		panel.add(new JLabel("Game Speed"));
		panel.add(new JTextField(String.valueOf(set.getSpeed())));
		add(panel, BorderLayout.EAST);

		setTheAnimals();
		rangeCanvas.addGrassFractal();

		timer = new Timer(BOARD_REFRESH_RATE, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println(wolf + " " + sheep);

				rangeCanvas.repaint();
				if (areSheepsAlive()) {
					finish();
				}
			}

			private boolean areSheepsAlive() {
				for (Sheep sheep : sheeps) {
					if (sheep.isAlive()) {
						return false;
					}
				}
				return true;
			}
		});

		// actions below
		huntButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!wolfIsPushed) {

					timer.start();

					for (Sheep sheep : sheeps) {
						sheep.setWolfs(wolfs);
						sheep.squirm();
					}

					for (Wolf wolf : wolfs) {
						wolf.hunt(sheeps, wolfs);
					}
					wolfIsPushed = true;
				}
			}
		});

	}

	private void setTheAnimals() {
		Random random = new Random();
		for (int q = 0; q < numberOfSheeps; q++) {
			sheeps.add(new Sheep(random.nextInt(max_X - 1), random
					.nextInt(max_Y - 1), SHEEP_COLOR));
		}
		for (int q = 0; q < numberOfWolfs; q++) {
			wolfs.add(new Wolf(random.nextInt(max_X - 1), random
					.nextInt(max_Y - 1), WOLF_COLOR));
		}
		giveTheAnimalsToCanvas();
	}

	private void giveTheAnimalsToCanvas() {

		for (Wolf wolf : wolfs) {
			rangeCanvas.addEntity(wolf);
		}
		for (Sheep sheep : sheeps) {
			rangeCanvas.addEntity(sheep);
		}

	}

	public static void pause() {
		try {
			Thread.sleep(GAME_SPEED);
		} catch (Exception e) {
			// uh oh!
		}
	}

	public static void main(String args[]) {
		RancherGame c = new RancherGame();
		c.setLayout(new GridLayout(1, 1));
		c.setSize(windowSizeY + 400, windowSizeX + 120);
		c.setVisible(true);
		c.setLocationRelativeTo(null);
		c.setLocationRelativeTo(null);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
