package com.fyregroup.openpos.variations;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JWindow;

import com.fyregroup.openpos.OpenPOS;
import com.fyregroup.openpos.utils.ErrorMessageGenerator;

public class Manager {
	
	public static boolean managerApproval() {
		JWindow jw = new JWindow();	
		jw.setSize((int)(OpenPOS.width/3.5), (int)(OpenPOS.height/2.8));
		jw.setVisible(true);
		jw.setLocationRelativeTo(null);
		jw.setAlwaysOnTop(true);
		
		JLabel prompt = new JLabel("Please swipe your Manager's card to authorise.");
		jw.add(prompt);
		
		System.out.println("Awaiting manager swipe...");
		
		return false;
		
	}
	
	public static boolean managerApproval(String reasonPrompt) {
		if(reasonPrompt == null || reasonPrompt == "") {
			return managerApproval();
		}
		JWindow jw = new JWindow();	
		jw.setSize((int)(OpenPOS.width/3.5), (int)(OpenPOS.height/2.8));
		jw.setVisible(true);
		jw.setLocationRelativeTo(null);
		jw.setAlwaysOnTop(true);
		
		JLabel prompt = new JLabel(reasonPrompt, JLabel.CENTER);
		jw.add(prompt);
		
		swipe(jw);
		return false;
	}
	
	private static boolean swipe(JWindow jw) {
		final ScheduledExecutorService sec = Executors.newSingleThreadScheduledExecutor();
		sec.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				jw.setVisible(false);
				ErrorMessageGenerator.showNewMessage("Card not presented in time. Press OK.");
				sec.shutdown();
			}
		}, 10, 10, TimeUnit.SECONDS);
		return false;
	}
	
}
