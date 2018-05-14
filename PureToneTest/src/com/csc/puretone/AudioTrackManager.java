package com.csc.puretone;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AudioTrackManager {
	public static final int RATE=44100;
	public static final float MAXVOLUME=100f;
	public static final int LEFT=1;
	public static final int RIGHT=2;
	public static final int DOUBLE=3;

	AudioTrack audioTrack;
	/** ����**/
	float volume;
	/** ����**/
	int channel;
	/** �ܳ���**/
	int length;
	/** һ�����Ҳ��ĳ���**/
	int waveLen;
	/** Ƶ��**/
	int Hz;
	/** ���Ҳ�**/
	byte[] wave;

	public AudioTrackManager(){
		wave=new byte[RATE];
	}

	/**
	 * ����Ƶ��
	 * @param rate
	 */
	public void start(int rate){
		stop();
		if(rate>0){
			Hz=rate;
			waveLen = RATE / Hz;
			length = waveLen * Hz;
			audioTrack=new AudioTrack(AudioManager.STREAM_MUSIC, RATE,
					AudioFormat.CHANNEL_CONFIGURATION_STEREO, // CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_8BIT, length, AudioTrack.MODE_STREAM);
			//�������Ҳ�
			wave=SinWave.sin(wave, waveLen, length);
			if(audioTrack!=null){
				audioTrack.play();
			}
		}else{
			return;
		}
		
	}

	/**
	 * д������
	 */
	public void play(){
		if(audioTrack!=null){
			audioTrack.write(wave, 0, length);
		}
	}

	/**
	 * ֹͣ����
	 */
	public void stop(){
		if(audioTrack!=null){
			audioTrack.stop();
			audioTrack.release();
			audioTrack=null;
		}
	}

	/**
	 * ��������
	 * @param volume
	 */
	public void setVolume(float volume){
		this.volume=volume;
		if(audioTrack!=null){
			switch (channel) {
			case LEFT:
				audioTrack.setStereoVolume(volume/MAXVOLUME, 0f);
				break;
			case RIGHT:
				audioTrack.setStereoVolume(0f, volume/MAXVOLUME);
				break;
			case DOUBLE:
				audioTrack.setStereoVolume(volume/MAXVOLUME, volume/MAXVOLUME);
				break;
			}
		}
	}

	/**
	 * ��������
	 * @param channel
	 */
	public void setChannel(int channel){
		this.channel=channel;
		setVolume(volume);
	}
}

