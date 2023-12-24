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

package com.obliquity.animation.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.Timer;

public abstract class AbstractAnimationController implements ActionListener {
	private Timer timer;
	private boolean reverse = false;
	private boolean fast = false;
	private JToolBar toolbar;
	private JButton btnPlay, btnPause, btnFF, btnRR, btnStepF, btnStepR;
	private ImageIcon iconPause, iconPlay;
	
	public enum Action { PLAY, PAUSE, STEP_FORWARD, STEP_BACKWARD, FAST_FORWARD, REWIND };
	
	public AbstractAnimationController(int timerDelay, int timerSpeed) {
		timer = createTimer(timerDelay, timerSpeed);
		toolbar = createToolBar();
	}

	private Timer createTimer(int timerDelay, int timerSpeed) {
		timer = new Timer(timerSpeed, this);
		timer.setInitialDelay(timerDelay);
		timer.setCoalesce(false);
		timer.start();
		
		return timer;
	}
	
	private JToolBar createToolBar() {
		JToolBar toolbar = new JToolBar();
		
		btnPlay = makeNavigationButton("Play24", "Run animation", "Run");
		
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				actionPlay();
			}
		});
		
		btnPause = makeNavigationButton("Pause24", "Pause animation",
				"Pause");
		
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				actionPause();
			}
		});
		
		btnFF = makeNavigationButton("FastForward24", "Fast forward",
				"FF");
		
		btnFF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				actionFastForward();
			}
		});
		
		btnRR = makeNavigationButton("Rewind24", "Rewind", "RWD");
		
		btnRR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				actionRewind();
			}
		});
		
		btnStepF = makeNavigationButton("StepForward24",
				"Step forward", "StepFwd");
		
		btnStepF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				actionStepForward();
			}
		});
		
		btnStepR = makeNavigationButton("StepBack24", "Step backward",
				"StepBack");
		
		btnStepR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				actionStepBackward();
			}
		});

		toolbar.add(btnRR);
		toolbar.add(btnStepR);

		toolbar.addSeparator();

		toolbar.add(btnPause);
		toolbar.add(btnPlay);

		toolbar.addSeparator();

		toolbar.add(btnStepF);
		toolbar.add(btnFF);

		toolbar.setFloatable(false);

		return toolbar;
	}
	
	protected JButton makeNavigationButton(String imageName,
			String toolTipText, String altText) {
		// Look for the image.
		String imgLocation = "/toolbarButtonGraphics/media/" + imageName
				+ ".gif";

		URL imageURL = getClass().getResource(imgLocation);

		// Create and initialize the button.
		JButton button = new JButton();
		button.setToolTipText(toolTipText);

		if (imageURL != null) { // image found
			button.setIcon(new ImageIcon(imageURL));
		} else { // no image found
			button.setText(altText);
			System.err.println("Resource not found: " + imgLocation);
		}

		return button;
	}

	
	public JToolBar getToolBar() {
		return toolbar;
	}
	
	private void actionRewind() {
		reverse = true;
		fast = true;

		if (!timer.isRunning())
			timer.start();
	}

	private void actionStepBackward() {
		if (!timer.isRunning())
			advanceTime(true, false);
	}

	private void actionPause() {
		if (timer.isRunning())
			timer.stop();
		else
			timer.start();
	}

	private void actionPlay() {
		reverse = false;
		fast = false;

		if (!timer.isRunning())
			timer.start();
	}

	private void actionStepForward() {
		if (!timer.isRunning())
			advanceTime(false, false);
	}

	private void actionFastForward() {
		reverse = false;
		fast = true;

		if (!timer.isRunning())
			timer.start();
	}

	public void start() {
		timer.restart();
	}

	public void stop() {
		timer.stop();
	}

	private void advanceTime() {
		advanceTime(reverse, fast);
	}

	public abstract void advanceTime(boolean reverse, boolean fast);

	public void actionPerformed(ActionEvent ae) {
		advanceTime();
	}

}
