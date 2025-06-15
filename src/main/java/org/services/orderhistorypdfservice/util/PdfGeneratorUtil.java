package org.services.orderhistorypdfservice.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.services.orderhistorypdfservice.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfGeneratorUtil {

    private static final Logger logger = LoggerFactory.getLogger(PdfGeneratorUtil.class);

    public byte[] generateOrderPdf(List<Order> orders) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Order History Report"));
            document.add(new Paragraph("Total Orders: " + orders.size()));
            document.add(new Paragraph(" "));

            for (Order order : orders) {
                document.add(new Paragraph(
                        "Item: " + order.getItemName() +
                                " | Price: ₹" + order.getPrice() +
                                " | Date: " + order.getOrderDate()
                ));
            }

            document.close();
            logger.info("PDF report generated successfully.");

        } catch (Exception e) {
            logger.error("Failed to generate PDF: {}", e.getMessage());
        }

        return out.toByteArray();
    }
}
