package com.fyregroup.openpos;

import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.fyregroup.openpos.enums.POSStatus;

import com.fyregroup.openpos.variations.Manager;

public class OpenPOS {

	public JFrame f;

	public static int width = 1920;
	public static int height = 1080;

	public boolean wayStation = false;
	public static String businessDay = null;

	public void createFrame() throws IOException {
		f = new JFrame();
		f.setTitle(
				"OpenPOS vOP0.000001  Development Pkg: 100019              Way Station: [%waystation%]              Business Day: %businessday%");

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		width = gd.getDisplayMode().getWidth();
		height = gd.getDisplayMode().getHeight();

		JFrame.setDefaultLookAndFeelDecorated(true);
		f.setLayout(new FlowLayout());
		f.setVisible(false);
		f.setSize(width, height);
		f.setLocation(0, 0);
		f.setAlwaysOnTop(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// System.out.println(getClass().getClassLoader().getResource("Assets/logo.png").getPath());

		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Assets/logo.png"));
		f.setIconImage(icon.getImage());

		ImageIcon status = null;
		if (!checkInternet()) {
			final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit()
					.getDesktopProperty("win.sound.exclamation");
			if (runnable != null)
				runnable.run();
			status = new ImageIcon(ImageIO.read(getStatusImage(POSStatus.POS_NO_CONNECTION)));
			wayStation = false;
			f.setTitle(f.getTitle().replace("%waystation%", "Offline"));
		} else {
			status = new ImageIcon(ImageIO.read(getStatusImage(POSStatus.POS_NOT_INIT)));
			wayStation = true;
			f.setTitle(f.getTitle().replace("%waystation%", "Online"));
		}

		f.setTitle(f.getTitle().replace("%businessday%", businessDay));

		JLabel label = new JLabel(status, JLabel.LEFT);
		f.add(label);

		f.setVisible(true);

		Manager.managerApproval(
				"<html><div style=\"text-align: center\">This sale has reached 562.30 GBP. The limit is 500.00 GBP. Please swipe your Manager's card to authorise.</div></html>");
	}

	public InputStream getStatusImage(POSStatus status) {
		if (status == POSStatus.POS_CLOSED) {
			return getClass().getClassLoader().getResourceAsStream("Assets/POS Statis Icons/POS Closed.png");
		}
		if (status == POSStatus.POS_NO_CONNECTION) {
			return getClass().getClassLoader().getResourceAsStream("Assets/POS Statis Icons/POS No Connection.png");
		}
		if (status == POSStatus.POS_NOT_INIT) {
			return getClass().getClassLoader().getResourceAsStream("Assets/POS Statis Icons/POS Not Init.png");
		}
		if (status == POSStatus.POS_OPEN) {
			return getClass().getClassLoader().getResourceAsStream("Assets/POS Statis Icons/POS Open.png");
		}
		if (status == POSStatus.POS_USER_LOGGED) {
			return getClass().getClassLoader().getResourceAsStream("Assets/POS Statis Icons/POS User Logged.png");
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				businessDay = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
				try {
					new OpenPOS().createFrame();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public boolean checkInternet() {
		try {
			URL url = new URL("http://www.google.com");
			URLConnection connection = url.openConnection();
			connection.connect();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	public void changeBusinessDay() {
		businessDay = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		f.setTitle(f.getTitle().replace("%businessday%", businessDay));
	}
}
