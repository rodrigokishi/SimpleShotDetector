package intermidia;

import java.io.File;
import java.io.FileWriter;

import org.openimaj.image.MBFImage;
import org.openimaj.video.Video;
import org.openimaj.video.processing.shotdetector.VideoKeyframe;
import org.openimaj.video.xuggle.XuggleVideo;

import TVSSUnits.Shot;
import TVSSUnits.ShotList;
import TVSSUtils.ShotSegmentator;

public class SimpleShotDetector
{
	public static void main(String[] args) 
	{	
		Video<MBFImage> source = new XuggleVideo(new File(args[0]));
		System.out.println("Determining shot boundaries.");
		ShotList shotList = ShotSegmentator.segmentVideo(source);	
		source.close();
		
		System.out.println("Writing output.");
		try
		{
			FileWriter shotWriter = new FileWriter(args[1]);
			FileWriter keyframeWriter = new FileWriter(args[2]);
			for(int i = 0; i < shotList.listSize(); i++)
			{
				/*Write a shot on the shot segmentation file.*/
				Shot shot = shotList.getShot(i);				
				shotWriter.write(String.valueOf(shot.getStartBoundary().getTimecode().getFrameNumber()) + "\t" +
								 String.valueOf(shot.getEndBoundary().getTimecode().getFrameNumber()) + "\n");
								
				for(int j = 0; j < shot.getKeyFrameList().size(); j++)
				{
					/*Write a keyframe on the keyframe list file*/
					VideoKeyframe<MBFImage> keyframe = shot.getKeyFrameList().get(j);
					keyframeWriter.write(i + "\t" + keyframe.getTimecode().getFrameNumber() + "\n");
				}
			}
			shotWriter.close();
			keyframeWriter.close();
		}catch(Exception e)
		{
			System.out.println("Error: SimpleShotDetector/SimpleShotDetector/main()");
			System.exit(1);  			
		}
		System.out.println("Successful execution.");
	}

}
