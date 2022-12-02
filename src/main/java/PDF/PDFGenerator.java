package PDF;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGenerator {

    public static void getAnswersFile(String[] answers) throws IOException, DocumentException{
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream("resources/answersFile.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
        Chunk chunk;
        for (String answer : answers) {
            chunk = new Chunk(answer, font);
            document.add(chunk);
        }
        document.close();
    }

    public static void main(String[] args) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.RED);
        Chunk chunk = new Chunk("Hello World", font);

        document.add(chunk);
        document.close();
    }
}
