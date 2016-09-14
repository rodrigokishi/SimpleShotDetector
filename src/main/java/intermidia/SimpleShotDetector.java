package intermidia;

import java.io.File;
import java.io.FileWriter;

import org.openimaj.image.MBFImage;
import org.openimaj.video.Video;
import org.openimaj.video.xuggle.XuggleVideo;

import TVSSUnits.Shot;
import TVSSUnits.ShotList;
import TVSSUtils.ShotSegmentator;

public class SimpleShotDetector
{
	public static void main(String[] args) throws Exception
	{	
		Video<MBFImage> source = new XuggleVideo(new File(args[0]));
		System.out.println("Determining shot boundaries.");		
		ShotSegmentator shotSegmentator = new ShotSegmentator(source);
		ShotList shotList = shotSegmentator.segmentVideo();	
		source.close();
		
		System.out.println("Writing output.");

		FileWriter shotWriter = new FileWriter(args[1]);
		for(int i = 0; i < shotList.listSize(); i++)
		{
			/*Write a shot on the shot segmentation file.*/
			Shot shot = shotList.getShot(i);				
			shotWriter.write(String.valueOf(shot.getStartBoundary().getTimecode().getFrameNumber()) + "\t" +
							 String.valueOf(shot.getEndBoundary().getTimecode().getFrameNumber()) + "\n");
							
		}
		shotWriter.close();

		System.out.println("Successful execution.");
	}

}
