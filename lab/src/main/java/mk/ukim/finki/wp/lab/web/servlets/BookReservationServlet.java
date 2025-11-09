package mk.ukim.finki.wp.lab.web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.service.BookReservationService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookReservationServlet ", urlPatterns = "/bookReservation")
public class BookReservationServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final BookReservationService bookReservationService;

    public BookReservationServlet(SpringTemplateEngine springTemplateEngine, BookReservationService bookReservationService) {
        this.springTemplateEngine = springTemplateEngine;
        this.bookReservationService = bookReservationService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        // Retrieve the session and get the reservations
        var session = req.getSession();
        List<BookReservation> reservations = (List<BookReservation>) session.getAttribute("reservations");
        if (reservations != null) {
            // Get the latest 3 reservations
            List<BookReservation> latestReservations = reservations.stream()// Keep the order as added
                    .limit(3)
                    .toList();
            context.setVariable("latestReservations", latestReservations);
        }
        else{
            context.setVariable("latestReservations", List.of());
        }
        springTemplateEngine.process("listBooks", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange= JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req,resp);
        WebContext context=new WebContext(webExchange);

        String readerName=req.getParameter("readerName");
        String readerAddress=req.getParameter("readerAddress");
        int numCopies=Integer.parseInt(req.getParameter("numCopies"));
        String bookTitle=req.getParameter("chosenBook");
        String ipAddress=req.getRemoteAddr();

        context.setVariable("readerName",readerName);
        context.setVariable("readerAddress",readerAddress);
        context.setVariable("numCopies",numCopies);
        context.setVariable("bookTitle",bookTitle);
        context.setVariable("ip",ipAddress);

        try{
            bookReservationService.placeReservation(bookTitle,readerName,readerAddress,numCopies);

            BookReservation reservation = new BookReservation(bookTitle, readerName, readerAddress, (long) numCopies);

            var session = req.getSession();
            List<BookReservation> reservations = (List<BookReservation>) session.getAttribute("reservations");
            if (reservations == null) {
                reservations = new ArrayList<>();
                session.setAttribute("reservations", reservations);
            }
            reservations.add(reservation);
        }catch (IllegalArgumentException e){
            resp.sendRedirect("/?error=Invalid arguments!");
            return;
        }

        springTemplateEngine.process("reservationConfirmation",context,resp.getWriter());


    }
}
