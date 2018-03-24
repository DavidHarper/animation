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

package com.obliquity.animation.galilean;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import com.obliquity.animation.core.AnimationToolBar;
import com.obliquity.animation.core.AnimationApplet;

public class GalileanSatelliteApplet extends AnimationApplet {
	private final int DEFAULT_SPEED = 100;
	private final int DEFAULT_PAUSE = 1000;
	private final double DEFAULT_SCALE = 4000.0;
	private final int DEFAULT_DT = 600;

	private GalileanSatelliteModel model;
	private GalileanSatelliteView view;
	private GalileanSatelliteController controller;
	
	public void init() {
		model = new GalileanSatelliteModel();
		
		int pause = getIntParameter("pause", DEFAULT_PAUSE);
		
		int speed = getIntParameter("speed", DEFAULT_SPEED);
		
		int dt = getIntParameter("dt", DEFAULT_DT);

		controller = new GalileanSatelliteController(model, pause, speed, dt);

		createUI();
	}

	protected void createUI() {
		JPanel panel = new JPanel(new BorderLayout());

		AnimationToolBar toolbar = new AnimationToolBar(controller);

		double scale = getDoubleParameter("scale", DEFAULT_SCALE);

		view = new GalileanSatelliteView(model, scale);

		panel.add(toolbar, BorderLayout.NORTH);
		panel.add(view, BorderLayout.CENTER);

		setContentPane(panel);
	}

	public String getAppletInfo() {
		return "Title: Laplacian Resonance\n"
				+ "Author: David Harper <obliquity.com>\n"
				+ "\n"
				+ "References:\n"
				+ "\n"
				+ "Lieske, J.H., 1998, \"Galilean satellite ephemerides E5\"\n"
				+ "Astron. Astrophys. Suppl. Ser. 129, 205\n"
				+ "\n"
				+ "Lieske, J.H., 1977, \"Theory of Motion of Jupiter's Galilean Satellites\"\n"
				+ "Astron. Astrophys. 56, 333\n";
	}

	public String[][] getParameterInfo() {
		String[][] info = { { "scale", "double", "scale in km per pixel" },
				{ "speed", "int", "interval between frames, in ms" },
				{ "pause", "int", "pause before animation starts, in ms" },
				{ "dt", "int", "step size in seconds" } };

		return info;
	}
}
