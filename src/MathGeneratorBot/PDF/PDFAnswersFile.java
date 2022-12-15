package MathGeneratorBot.PDF;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PDFAnswersFile {
    private String filePath;



    public void generate(ArrayList<String> answers) throws IOException, DocumentException{
        Document document = new Document();
        try {
        PdfWriter.getInstance(document, new FileOutputStream(filePath));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);

            Paragraph paragraph;
            for (String answer : answers) {
                paragraph = new Paragraph(answer, font);
                document.add(paragraph);
            }
        } finally {
            document.close();
        }
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPath() {
        return filePath;
    }
}
