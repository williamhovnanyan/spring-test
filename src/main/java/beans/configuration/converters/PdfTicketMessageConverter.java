package beans.configuration.converters;

import beans.models.Ticket;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Table;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class PdfTicketMessageConverter implements HttpMessageConverter<Ticket> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return Ticket.class.isAssignableFrom(clazz) && mediaType.isCompatibleWith(MediaType.parseMediaType("application/pdf"));
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.parseMediaType("application/pdf"));
    }

    @Override
    public Ticket read(Class<? extends Ticket> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(Ticket ticket, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Table table = null;
        try {
            table = new Table(6);
            table.addCell("Id");
            table.addCell("Date");
            table.addCell("Seats");
            table.addCell("User");
            table.addCell("Event name");
            table.addCell("Price");

            table.addCell(String.valueOf(ticket.getId()));
            table.addCell(ticket.getDateTime().toString());
            table.addCell(ticket.getSeats());
            table.addCell(ticket.getUser().getEmail());
            table.addCell(ticket.getEvent().getName());
            table.addCell(String.valueOf(ticket.getPrice()));

            Document document = new Document();
            document.add(table);

            OutputStream httpOOS = outputMessage.getBody();
            ObjectOutputStream oos = new ObjectOutputStream(httpOOS);
            oos.writeObject(document);
            oos.flush();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
