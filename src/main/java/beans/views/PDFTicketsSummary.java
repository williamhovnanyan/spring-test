package beans.views;

import beans.models.Ticket;
import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
@Qualifier("PDFTicketsSummary")
public class PDFTicketsSummary extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Ticket> ticketData = (List<Ticket>) model.get("tickets");

        Table table = new Table(6);
        table.addCell("Id");
        table.addCell("Date");
        table.addCell("Seats");
        table.addCell("User");
        table.addCell("Event name");
        table.addCell("Price");

        for (Ticket ticket: ticketData){
            table.addCell(String.valueOf(ticket.getId()));
            table.addCell(ticket.getDateTime().toString());
            table.addCell(ticket.getSeats());
            table.addCell(ticket.getUser().getEmail());
            table.addCell(ticket.getEvent().getName());
            table.addCell(String.valueOf(ticket.getPrice()));
        }

        document.add(table);
    }
}
