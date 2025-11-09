package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BookReservation implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID for serialization compatibility

    String bookTitle;
    String readerName;
    String readerAddress;
    Long numberOfCopies;
}