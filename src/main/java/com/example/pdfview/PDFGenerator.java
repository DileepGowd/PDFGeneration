package com.example.pdfview;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import com.example.model.Employee;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFGenerator {
	public static ByteArrayInputStream customerPDFReport(List<Employee> employees) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfWriter.getInstance(document, out);
			document.open();
			Image im = Image.getInstance("/home/dileep/Downloads/purpletalk.png");
			im.setAlignment(Element.ALIGN_CENTER);
			document.add(im);
			// Add Text to PDF file ->
			Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
			Paragraph para = new Paragraph("Employee Table", font);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(3);
			// Add PDF Table Header ->
			Stream.of("ID", "Name", "Salary").forEach(headerTitle -> {
				PdfPCell header = new PdfPCell();
				Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
				header.setBackgroundColor(BaseColor.LIGHT_GRAY);
				header.setHorizontalAlignment(Element.ALIGN_CENTER);
				header.setBorderWidth(2);
				header.setPhrase(new Phrase(headerTitle, headFont));
				table.addCell(header);
			});

			for (Employee employee : employees) {
				PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(employee.getId())));
				idCell.setPaddingLeft(4);
				idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(idCell);

				PdfPCell firstNameCell = new PdfPCell(new Phrase(employee.getName()));
				firstNameCell.setPaddingLeft(4);
				firstNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				firstNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(firstNameCell);

				PdfPCell lastNameCell = new PdfPCell(new Phrase(String.valueOf(employee.getSalary())));
				lastNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				lastNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				lastNameCell.setPaddingRight(4);
				table.addCell(lastNameCell);
			}
			document.add(table);

			document.close();
		} catch (DocumentException | IOException e) {
			System.out.println(e.toString());
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
}
