<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.eventmanagement.model.Event"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Event</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">Event Management</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="events">Dashboard</a>
                </li>
            </ul>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="logout">Logout</a>
                </li>
            </ul>
        </div>
    </nav>
    
    <div class="container mt-4">
        <% Event event = (Event) request.getAttribute("event"); %>
        <% boolean isRegistered = (Boolean) request.getAttribute("isRegistered"); %>
        
        <div class="card mb-4">
            <div class="card-body">
                <h2 class="card-title"><%= event.getTitle() %></h2>
                <p class="card-text"><%= event.getDescription() %></p>
                <p class="card-text">
                    <strong>Date:</strong> <%= event.getDate() %><br>
                    <strong>Time:</strong> <%= event.getTime() %><br>
                    <strong>Location:</strong> <%= event.getLocation() %>
                </p>
                
                <% if (isRegistered) { %>
                    <p class="text-success">You are registered for this event</p>
                    <a href="cancel-registration?id=<%= event.getEventId() %>" class="btn btn-danger">Cancel Registration</a>
                <% } else { %>
                    <a href="register-event?id=<%= event.getEventId() %>" class="btn btn-primary">Register for Event</a>
                <% } %>
            </div>
        </div>
        
        <a href="events" class="btn btn-secondary">Back to Events</a>
    </div>
</body>
</html>