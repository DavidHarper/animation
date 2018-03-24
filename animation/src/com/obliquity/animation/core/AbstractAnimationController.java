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

import javax.swing.JButton;
import javax.swing.Timer;

public abstract class AbstractAnimationController implements ActionListener {
	private Timer timer;
	private boolean reverse = false;
	private boolean fast = false;
	
	public enum Action { PLAY, PAUSE, STEP_FORWARD, STEP_BACKWARD, FAST_FORWARD, REWIND };
	
	public AbstractAnimationController(int timerDelay, int timerSpeed) {
		timer = createTimer(timerDelay, timerSpeed);
	}

	private Timer createTimer(int timerDelay, int timerSpeed) {
		timer = new Timer(timerSpeed, this);
		timer.setInitialDelay(timerDelay);
		timer.setCoalesce(false);
		timer.start();
		
		return timer;
	}
	
	public void registerActionButton(JButton button, Action action) {
		switch (action) {
			case PLAY:
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						actionPlay();
					}
				});
				break;

			case PAUSE:
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						actionPause();
					}
				});
				break;
				
			case STEP_FORWARD:
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						actionStepForward();
					}
				});
				break;
			
			case STEP_BACKWARD:
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						actionStepBackward();
					}
				});
				break;
			
			case FAST_FORWARD:
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						actionFastForward();
					}
				});
				break;
				
			case REWIND:
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						actionRewind();
					}
				});
				break;
		}
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
