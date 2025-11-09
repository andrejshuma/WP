package mk.ukim.finki.wp.lab.web.controllers;

import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.service.BookReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookReservationController {

    private final BookReservationService bookReservationService;

    public BookReservationController(BookReservationService bookReservationService) {
        this.bookReservationService = bookReservationService;
    }

    @PostMapping("/bookReservation")
    public String createReservation(@RequestParam String chosenBook,
                                    @RequestParam String readerName,
                                    @RequestParam String readerAddress,
                                    @RequestParam int numCopies,
                                    HttpServletRequest request,
                                    Model model) {
        // Get client IP address
        String ipAddress = request.getRemoteAddr();

        try {
            // Create reservation using the service
            bookReservationService.placeReservation(chosenBook, readerName, readerAddress, numCopies);

            // Create a new reservation object
            BookReservation reservation = new BookReservation(chosenBook, readerName, readerAddress, (long) numCopies);

            // Store the reservation in the session
            HttpSession session = request.getSession();
            List<BookReservation> reservations = (List<BookReservation>) session.getAttribute("reservations");
            if (reservations == null) {
                reservations = new ArrayList<>();
                session.setAttribute("reservations", reservations);
            }
            reservations.add(reservation);

            // Add reservation details to the model
            model.addAttribute("readerName", readerName);
            model.addAttribute("readerAddress", readerAddress);
            model.addAttribute("numCopies", numCopies);
            model.addAttribute("bookTitle", chosenBook);
            model.addAttribute("ip", ipAddress);

            // Display the confirmation page
            return "reservationConfirmation";
        } catch (IllegalArgumentException e) {
            // Redirect to the homepage with an error message if invalid arguments are provided
            return "redirect:/?error=Invalid arguments!";
        }
    }
}