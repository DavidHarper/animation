/*
 * animation - a package for simple animations
 *
 * Copyright (C) 2018 David Harper at obliquity.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 * 
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 *
 * See the COPYING file located in the top-level-directory of
 * the archive of this library for complete text of license.
 */

package com.obliquity.animation.neptune;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class NeptuneApp {
	private static final int DEFAULT_SPEED = 100;
	private static final int DEFAULT_PAUSE = 1000;

	private static final double DEFAULT_ORBIT_SCALE = 5.0;
	private static final double DEFAULT_OFFSET_SCALE = 600.0;

	private static final double DEFAULT_DT = 0.125;
	private static final double DEFAULT_T0 = 1781.0;

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static double getDouble(String name, double defaultValue) {
		String stringValue = System.getProperty(name);
		
		return (stringValue == null) ? defaultValue : Double.parseDouble(stringValue);
	}

	protected static void createAndShowGUI() {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		JFrame frame = new JFrame("Neptune Animation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		NeptuneModel model = new NeptuneModel();

		double orbitScale = getDouble("orbitscale", DEFAULT_ORBIT_SCALE);

		double offsetScale = getDouble("offsetscale", DEFAULT_OFFSET_SCALE);

		NeptuneView view = new NeptuneView(model, orbitScale, offsetScale);
		
		int pause = Integer.getInteger("pause", DEFAULT_PAUSE);
		
		int speed = Integer.getInteger("speed", DEFAULT_SPEED);
		
		double t0 = getDouble("t", DEFAULT_T0);
		
		double dt = getDouble("dt", DEFAULT_DT);
		
		NeptuneController controller = new NeptuneController(model, view, pause, speed, t0, dt);
		
		JPanel panel = new JPanel(new BorderLayout());

		JToolBar toolbar = controller.getToolBar();

		panel.add(toolbar, BorderLayout.NORTH);
		panel.add(view, BorderLayout.CENTER);

		frame.setContentPane(panel);

		// Display the window.
		frame.setSize(850, 550);
		
		frame.setVisible(true);
	}
}
