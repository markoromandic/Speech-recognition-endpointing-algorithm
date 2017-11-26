package model;

import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.packenius.library.xspdf.XSPDF;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import variables.Variables;

public class ExportToPdf {
	private ExportToPdf() {}
	
	public static void doExport(Node linechartN, String audioName) {
	    WritableImage linechart = linechartN.snapshot(new SnapshotParameters(), null);
	    
	    File pdfFile = new File(audioName +  Variables.PDF);
	    File lineChartF = new File("");
	    
	    try {
	        ImageIO.write(SwingFXUtils.fromFXImage(linechart, null), Variables.PNG, lineChartF);
	        
	        BufferedImage linechartToPdf = ImageIO.read(lineChartF);
	        
	        linechartToPdf = convert24(linechartToPdf);
	        
	        XSPDF.getInstance().setPageSize(595, 842).setImage(linechartToPdf, 0, 0, 400, 300, 0).createPdf(Variables.PDF_LOC + pdfFile);
	        lineChartF.delete();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	public static BufferedImage convert24(BufferedImage src) {
	    BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
	    ColorConvertOp cco = new ColorConvertOp(src.getColorModel().getColorSpace(), dest.getColorModel().getColorSpace(), null);
	    cco.filter(src, dest);
	    return dest;
	}
}
