import java.awt.event.KeyEvent;
import java.util.Random;

public class Stressor implements Runnable {
	Interface i;

	void flood(Interface i) {
		this.i = i;
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			Random key = new Random(9812345);
			int[] keys = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT}; 
			while (true) {
				Thread.sleep(10);
				int k = key.nextInt(keys.length);
				i.testmove(new KeyEvent(i,
						                KeyEvent.VK_UNDEFINED,
						                System.currentTimeMillis(),
						                0,
						                keys[k],
						                (char) 65535));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
