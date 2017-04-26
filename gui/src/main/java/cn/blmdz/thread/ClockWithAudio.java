package cn.blmdz.thread;

import java.applet.Applet;
import java.applet.AudioClip;

import javax.swing.JApplet;

public class ClockWithAudio extends JApplet {

	private static final long serialVersionUID = 1L;

	protected AudioClip[] hourAudio = new AudioClip[12];
	protected AudioClip[] minuteAudio = new AudioClip[60];
	
	protected AudioClip amAudio = Applet.newAudioClip(this.getClass().getResource(""));
	protected AudioClip pmAudio = Applet.newAudioClip(this.getClass().getResource(""));
	

}
