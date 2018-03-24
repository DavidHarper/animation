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

import javax.swing.JPanel;
import java.awt.BorderLayout;

import com.obliquity.animation.core.AnimationToolBar;
import com.obliquity.animation.core.AnimationApplet;

public class NeptuneApplet extends AnimationApplet {
	private final int DEFAULT_SPEED = 100;
	private final int DEFAULT_PAUSE = 1000;

	private final double DEFAULT_ORBIT_SCALE = 5.0;
	private final double DEFAULT_OFFSET_SCALE = 600.0;

	private final double DEFAULT_DT = 0.125;
	private final double DEFAULT_T0 = 1781.0;
	
	private NeptuneModel model;
	private NeptuneView view;
	private NeptuneController controller;
	
	public void init() {
		model = new NeptuneModel();
		
		int pause = getIntParameter("pause", DEFAULT_PAUSE);
		
		int speed = getIntParameter("speed", DEFAULT_SPEED);
		
		double t0 = getDoubleParameter("t", DEFAULT_T0);
		
		double dt = getDoubleParameter("dt", DEFAULT_DT);
		
		controller = new NeptuneController(model, pause, speed, t0, dt);

		createUI();
	}

	protected void createUI() {
		JPanel panel = new JPanel(new BorderLayout());

		AnimationToolBar toolbar = new AnimationToolBar(controller);

		double orbitScale = getDoubleParameter("orbitscale", DEFAULT_ORBIT_SCALE);

		double offsetScale = getDoubleParameter("offsetscale", DEFAULT_OFFSET_SCALE);

		view = new NeptuneView(model, orbitScale, offsetScale);

		panel.add(toolbar, BorderLayout.NORTH);
		panel.add(view, BorderLayout.CENTER);

		setContentPane(panel);
	}

	public String getAppletInfo() {
		return "Title: Uranus and Neptune\n"
				+ "Author: David Harper <obliquity.com>\n"
				+ "\n"
				+ "Reference:\n"
				+ "\n"
				+ "Bretagnon, P. and Francou, G. (1988)\n"
				+ "Planetary theories in rectangular and spherical variables. VSOP87 solutions\n"
				+ "Astron. Astrophys. 202, 309\n\n"
				+ "http://cdsweb.u-strasbg.fr/htbin/Cat?VI/81";
	}

	public String[][] getParameterInfo() {
		String[][] info = { { "scale", "double", "scale in km per pixel" },
				{ "speed", "int", "interval between frames, in ms" },
				{ "pause", "int", "pause before animation starts, in ms" },
				{ "t0", "double", "start date (year)" },
				{ "dt", "double", "step size in years" } };

		return info;
	}
}
