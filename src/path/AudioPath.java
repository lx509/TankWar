package path;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
/**
 * 音效工具类
 * 
 */

@SuppressWarnings("deprecation")
public class AudioPath{
	/**
	 * 坦克诞生音效
	 */
	public static final String ADD="audio//add.wav";
	/**
	 * 爆炸音效
	 */
	public static final String BLAST="audio//blast.wav";
	/**
	 * 发射子弹音效
	 */
	public static final String FIRE="audio//fire.wav";
	/**
	 * 游戏结束音效
	 */
	public static final String GAMEOVER="audio//gameover.wav";
	/**
	 * 子弹撞击音效
	 */
	public static final String HIT="audio//hit.wav";
	/**
	 * 游戏开始音效
	 */
	public static final String START="audio//start.wav";
	/**
	 * 获取到所有背景音效的集合的方法
	 */
	public static List<AudioClip> getAudios(){
		List<AudioClip> audios = new ArrayList<>();
		try {
			audios.add(Applet.newAudioClip(new File(AudioPath.START).toURL()));
			audios.add(Applet.newAudioClip(new File(AudioPath.ADD).toURL()));
			audios.add(Applet.newAudioClip(new File(AudioPath.BLAST).toURL()));
			audios.add(Applet.newAudioClip(new File(AudioPath.FIRE).toURL()));
			audios.add(Applet.newAudioClip(new File(AudioPath.HIT).toURL()));
			audios.add(Applet.newAudioClip(new File(AudioPath.GAMEOVER).toURL()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//audios.add(Applet.newAudioClip(AudioUtil.class.getResource(AudioUtil.BGM)));
		return audios;
	}
}