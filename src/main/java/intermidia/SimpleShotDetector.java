package intermidia;

import java.io.File;
import java.io.FileWriter;

import org.openimaj.video.xuggle.XuggleVideo;

import TVSSUnits.Shot;
import TVSSUnits.ShotList;
import TVSSUtils.ShotSegmentator;

public class SimpleShotDetector
{
	//Usage <in: video> <out: shot segmentation> 
	public static void main(String[] args) throws Exception
	{	
		XuggleVideo source = new XuggleVideo(new File(args[0]));
		System.out.println("Determining shot boundaries.");		
		ShotSegmentator shotSegmentator = new ShotSegmentator(source);
		ShotList shotList = shotSegmentator.segmentVideo();	
		source.close();
		
		System.out.println("Writing output.");

		FileWriter shotWriter = new FileWriter(args[1]);
		/*FileWriter keyframeWriter = new FileWriter(args[2]);*/
		for(int i = 0; i < shotList.listSize(); i++)
		{
			/*Write a shot on the shot segmentation file.*/
			Shot shot = shotList.getShot(i);				
			shotWriter.write(String.valueOf(shot.getStartBoundary()) + "\t" +
							 String.valueOf(shot.getEndBoundary()) + "\n");
			/*for(int j = 0; j < shot.getKeyFrameList().size(); j++)
			{
				keyframeWriter.write((i + 1) + "\t" + String.valueOf(shot.getKeyFrameList().get(j).getTimecode().getFrameNumber()) + "\n");
			}*/
		}
		shotWriter.close();
		//keyframeWriter.close();

		System.out.println("Successful execution, " + shotList.listSize() + " shots found.");
	}

}
