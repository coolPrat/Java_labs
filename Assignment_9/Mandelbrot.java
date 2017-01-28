/*
 * Mandelbrot.java
 *
 * Version: 2.4: Mandelbrot.java,v 1.1 10/26/2015 17:14:23
 *
 * Revisions: 1.0 initial version
 * 			  1.1 Kapil 2015/10/26 22:00:31
 *
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * This program is extension of MultiThreaded version of drawing Mandelbrot set.
 * Here we are creating two different types of thread, Manager thread and pixel
 * thread. Manager thread distributes work to each pixel thread. In other words,
 * a pixelThreads is created, then tasked by the manager to determine the color
 * for particular pixel at position x/y, and after setting the color the same
 * thread is will be tasked by the manager to determine the color for an other
 * pixel x'/y' until all pixels are colored
 *
 * @author Pratik kulkarni
 */

public class Mandelbrot extends JFrame implements Runnable {

	private final int MAX = 5000;
	private final int LENGTH = 800;
	private final double ZOOM = 1000;
	private BufferedImage theImage;
	private int[] colors = new int[MAX];
	private static int count = 0, innerCounter = 0;
	private static int index = 0;

	/**
	 * Initializing frame parameters.
	 *
	 * @param 	none.
	 *
	 */
	public Mandelbrot() {
		super("Mandelbrot Set");
		initColors();
		setBounds(100, 100, LENGTH, LENGTH);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Initializing color values.
	 *
	 * @param 	None.
	 *
	 * @return 	None.
	 */
	public void initColors() {
		for (int index = 0; index < MAX; index++) {
			colors[index] = Color.HSBtoRGB(index / 256f, 1, index / (index + 8f));
		}
	}

	/**
	 * overriding paint() method defined by java.awt.Component.
	 *
	 * @param 	g   Graphics object.
	 *
	 *
	 * @return 		None.
	 */
	public void paint(Graphics g) {
		g.drawImage(theImage, 0, 0, this);
	}

	/**
	 * Inner class of class Mandelbrot which takes x, y, Object values as input
	 * and is basically used for creating pixel thread.
	 *
	 */
	public class InnerMandelbrot extends Thread {
		double zx, zy, cX, cY;
		double tmp;
		int x, y;
		Object o;

		public InnerMandelbrot(int x, int y, Object o) {
			this.x = x;
			this.y = y;
			this.o = o;
		}

		/**
		 * Setting new x and y values provided by manager thread.
		 *
		 * @param 	x 	x pixel co-ordinate.
		 * @param   y   y pixel co-ordinate.
		 * @return 		None.
		 */
		public void setXY(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * implementing run() method from Thread class.
		 *
		 * @param    None.
		 *
		 * @return   None.
		 */
		public void run() {
			boolean done = false;
			while (!done) {
				synchronized (o) {
					System.out.println("Pixel " + this.getName() + " running");
					cX = (x - LENGTH) / ZOOM;
					cY = (y - LENGTH) / ZOOM;
					zx = zy = 0;
					int iter = 0;
					while ((zx * zx + zy * zy < 10) && (iter < MAX - 1)) {
						tmp = zx * zx - zy * zy + cX;
						zy = 2.0 * zx * zy + cY;
						zx = tmp;
						iter++;
					}
					if (iter > 0)
						theImage.setRGB(x, y, colors[iter]);
					else
						theImage.setRGB(x, y, iter | (iter << 8));
					try {
						o.wait();

					} catch (InterruptedException e) {
						if (innerCounter >= getWidth() * getHeight()) {
							done = true;
						} else {
							innerCounter++;
						}
					}
				}
			}
		}
	}

	/**
	 * implementing run() method from Runnable interface.
	 *
	 * @param    None.
	 *
	 * @return   None.
	 */
	public void run() {
		theImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Object[] o = new Object[Runtime.getRuntime().availableProcessors()];
		InnerMandelbrot[] pixelThreads = new InnerMandelbrot[Runtime.getRuntime().availableProcessors()];
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				boolean found = false;
				System.out.println("Inside Manager thread");
				// creating threads equal to total number of cores.
				if (count < Runtime.getRuntime().availableProcessors()) {
					o[count] = new Object();
					pixelThreads[count] = new InnerMandelbrot(x, y, o[count]);
					System.out.println("Starting " + pixelThreads[count].getName());
					pixelThreads[count].start();
					count++;
				} else {
					// Assigning work to waiting pixel thread.
					while (!found) {
						for (int counter = index; counter < Runtime.getRuntime().availableProcessors(); counter++) {
							if (pixelThreads[counter].getState() == Thread.State.WAITING) {
								System.out.println(
										"Assigning pixel (" + x + "," + y + ") to " + pixelThreads[counter].getName());
								pixelThreads[counter].setXY(x, y);
								pixelThreads[counter].interrupt();
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								found = true;
								break;
							}
						}
						index = (++index % Runtime.getRuntime().availableProcessors());
					}
				}
			}
		}
		repaint();
	}

	/**
	 * The main method.
	 *
	 * @param 	args   ignored.
	 *
	 *
	 */
	public static void main(String[] args) {
		Mandelbrot aMandelbrot = new Mandelbrot();
		aMandelbrot.setVisible(true);
		Thread t1 = new Thread(aMandelbrot);
		t1.start();
	}
}
