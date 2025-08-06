package com.eventmanagement.controller;

import com.eventmanagement.dao.EventDAO;
import com.eventmanagement.dao.RegistrationDAO;
import com.eventmanagement.model.Event;
import com.eventmanagement.model.User;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "EventServlet", urlPatterns = {
    "/events", "/create-event", "/edit-event", "/update-event", "/delete-event", 
    "/view-event", "/register-event", "/cancel-registration"
})
public class EventServlet extends HttpServlet {
    private EventDAO eventDao;
    private RegistrationDAO registrationDao;
    
    @Override
    public void init() {
        eventDao = new EventDAO();
        registrationDao = new RegistrationDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        switch (action) {
            case "/events":
                listEvents(request, response, user);
                break;
            case "/create-event":
                request.getRequestDispatcher("create-event.jsp").forward(request, response);
                break;
            case "/edit-event":
                showEditForm(request, response, user);
                break;
            case "/delete-event":
                deleteEvent(request, response, user);
                break;
            case "/view-event":
                viewEvent(request, response, user);
                break;
            case "/register-event":
                registerForEvent(request, response, user);
                break;
            case "/cancel-registration":
                cancelRegistration(request, response, user);
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        switch (action) {
            case "/create-event":
                createEvent(request, response, user);
                break;
            case "/update-event":
                updateEvent(request, response, user);
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }
    
    private void listEvents(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        if (user.getRole().equals("organizer")) {
            request.setAttribute("events", eventDao.getEventsByOrganizer(user.getUserId()));
            request.getRequestDispatcher("organizer-dashboard.jsp").forward(request, response);
        } else {
            request.setAttribute("events", eventDao.getAllEvents());
            request.getRequestDispatcher("attendee-dashboard.jsp").forward(request, response);
        }
    }
    
    private void createEvent(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            Date date = Date.valueOf(request.getParameter("date"));
            
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Time time = new Time(timeFormat.parse(request.getParameter("time")).getTime());
            
            String location = request.getParameter("location");
            
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setDate(date);
            event.setTime(time);
            event.setLocation(location);
            event.setOrganizerId(user.getUserId());
            
            if (eventDao.createEvent(event)) {
                response.sendRedirect("events");
            } else {
                request.setAttribute("error", "Failed to create event");
                request.getRequestDispatcher("create-event.jsp").forward(request, response);
            }
        } catch (ParseException e) {
            request.setAttribute("error", "Invalid time format");
            request.getRequestDispatcher("create-event.jsp").forward(request, response);
        }
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));
        Event event = eventDao.getEventById(eventId);
        
        if (event != null && event.getOrganizerId() == user.getUserId()) {
            request.setAttribute("event", event);
            request.getRequestDispatcher("edit-event.jsp").forward(request, response);
        } else {
            response.sendRedirect("events");
        }
    }
    
    private void updateEvent(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            Event existingEvent = eventDao.getEventById(eventId);
            
            if (existingEvent == null || existingEvent.getOrganizerId() != user.getUserId()) {
                response.sendRedirect("events");
                return;
            }
            
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            Date date = Date.valueOf(request.getParameter("date"));
            
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Time time = new Time(timeFormat.parse(request.getParameter("time")).getTime());
            
            String location = request.getParameter("location");
            
            Event event = new Event();
            event.setEventId(eventId);
            event.setTitle(title);
            event.setDescription(description);
            event.setDate(date);
            event.setTime(time);
            event.setLocation(location);
            event.setOrganizerId(user.getUserId());
            
            if (eventDao.updateEvent(event)) {
                response.sendRedirect("events");
            } else {
                request.setAttribute("error", "Failed to update event");
                request.setAttribute("event", event);
                request.getRequestDispatcher("edit-event.jsp").forward(request, response);
            }
        } catch (ParseException e) {
            request.setAttribute("error", "Invalid time format");
            request.getRequestDispatcher("edit-event.jsp").forward(request, response);
        }
    }
    
    private void deleteEvent(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));
        Event event = eventDao.getEventById(eventId);
        
        if (event != null && event.getOrganizerId() == user.getUserId()) {
            eventDao.deleteEvent(eventId);
        }
        response.sendRedirect("events");
    }
    
    private void viewEvent(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));
        Event event = eventDao.getEventById(eventId);
        
        if (event != null) {
            boolean isRegistered = registrationDao.isRegistered(eventId, user.getUserId());
            request.setAttribute("event", event);
            request.setAttribute("isRegistered", isRegistered);
            
            if (user.getRole().equals("organizer") && event.getOrganizerId() == user.getUserId()) {
                request.setAttribute("attendees", registrationDao.getRegistrationsByEvent(eventId));
                request.getRequestDispatcher("organizer-view-event.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("attendee-view-event.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("events");
        }
    }
    
    private void registerForEvent(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));
        
        if (!registrationDao.isRegistered(eventId, user.getUserId())) {
            registrationDao.registerForEvent(eventId, user.getUserId());
        }
        response.sendRedirect("view-event?id=" + eventId);
    }
    
    private void cancelRegistration(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));
        
        if (registrationDao.isRegistered(eventId, user.getUserId())) {
            registrationDao.cancelRegistration(eventId, user.getUserId());
        }
        response.sendRedirect("view-event?id=" + eventId);
    }
}
