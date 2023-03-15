package windows;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import objects.OrderItems;
import objects.OrderMenus;
import util.ManagerDB;


public class Receipt {

    private ManagerDB theManagerDB = new ManagerDB();
    private static String path = "";
    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
    private static Font subtitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    private static Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private int order_id;
    private int table_id;
    private String subtotal;
    private String tax;
    private String total;
    private String cash;
    private String change;

    public Receipt(int order_id, int table_id, String subtotal, String tax, String total, String cash, String change) {
        this.order_id = order_id;
        this.table_id = table_id;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
        this.cash = cash;
        this.change = change;

        path = System.getProperty("user.dir") + "/receipts/Order" + order_id + ".pdf";
        
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            addText(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addText(Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date date = new Date();

        paragraph.add(new Paragraph("Eat N' Beat", titleFont));
        paragraph.add(new Paragraph(formatter.format(date), subtitleFont));
        if (table_id != -1) paragraph.add(new Paragraph("Table: " + table_id, normalFont));
        else paragraph.add(new Paragraph("Take Away", normalFont));

        addEmptyLine(paragraph, 2);
        createTable(paragraph);
        addEmptyLine(paragraph, 3);

        paragraph.add(new Paragraph("Paid: " + cash + "€", normalFont));
        paragraph.add(new Paragraph("Change: " + change + "€", normalFont));
        
        addEmptyLine(paragraph, 5);
        
        paragraph.add(new Paragraph("Thank you for eating with us!", subtitleFont));

        document.add(paragraph);
    }

    private void createTable(Paragraph paragraph) throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        ArrayList<OrderItems> orderItems = theManagerDB.getOrderItems(order_id);
        ArrayList<OrderMenus> orderMenus = theManagerDB.getOrderMenus(order_id);
        PdfPCell cell;
        
        table.setWidths(new int[]{2, 1, 1, 1});
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        cell = new PdfPCell(new Phrase("Product", boldFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Quantity", boldFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Price", boldFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Value", boldFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        table.setHeaderRows(1);

        
        for (OrderItems item : orderItems) {
            table.addCell(item.getName());
            table.addCell(Integer.toString(item.getQuantity()));
            table.addCell(df.format(item.getProduct().getPrice()) + "€");
            table.addCell(df.format(item.getProduct().getPrice()*item.getQuantity())+"€");
        }
        for (OrderMenus menu : orderMenus) {
            table.addCell(menu.getName());
            table.addCell(Integer.toString(menu.getQuantity()));
            table.addCell(df.format(menu.getMenu().getPrice()) + "€");
            table.addCell(df.format(menu.getMenu().getPrice()*menu.getQuantity())+"€");
        }

        for (int i = 0; i < 8; i++) {
            table.addCell(" ");
        }
        
        table.addCell("");
        table.addCell("");
        cell = new PdfPCell(new Phrase("Subtotal:" + "\u00a0" + "\u00a0" + "\u00a0", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.addCell(subtotal + "€");
        
        table.addCell("");
        table.addCell("");
        cell = new PdfPCell(new Phrase("Tax:" + "\u00a0" + "\u00a0" + "\u00a0", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.addCell(tax + "€");
        
        table.addCell("");
        table.addCell("");
        cell = new PdfPCell(new Phrase("Total:" + "\u00a0" + "\u00a0" + "\u00a0", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        table.addCell(total + "€");

        paragraph.add(table);
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}