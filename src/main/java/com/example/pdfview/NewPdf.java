package com.example.pdfview;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class NewPdf {
	public static void main(String args[]) {
		try {
			// Create Document instance.
			Document document = new Document();

			// Create OutputStream instance.
			OutputStream outputStream = new FileOutputStream(new File("/home/dileep/2019/TestPhraseFile.pdf"));

			// Create PDFWriter instance.
			PdfWriter.getInstance(document, outputStream);

			// Open the document.
			document.open();
			
			Rectangle rect = new Rectangle(25,100);
			
			rect.setBorder(5);
			rect.setBorderColor(BaseColor.BLACK);
			 //Create Chapter object
	        Chapter chapter = new Chapter(new Paragraph("Chapter 1"), 1); 	
	 
	        //Add section to chapter
	        chapter.addSection("Section 1", 2);
	        chapter.addSection("Section 2", 2);
	 
	        //Add content to the document using chapter objects.
	        document.add(chapter);

			// Close document and outputStream.
			document.close();
			outputStream.close();

			System.out.println("Pdf created successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
