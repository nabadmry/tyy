<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.eventmanagement.model.Event"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Event</title>
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
                <li class="nav-item">
                    <a class="nav-link" href="create-event">Create Event</a>
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
        <h2>Edit Event</h2>
        
        <% Event event = (Event) request.getAttribute("event"); %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">${error}</div>
        <% } %>
        
        <form action="update-event" method="POST">
            <input type="hidden" name="eventId" value="<%= event.getEventId() %>">
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" class="form-control" id="title" name="title" value="<%= event.getTitle() %>" required>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea class="form-control" id="description" name="description" rows="3" required><%= event.getDescription() %></textarea>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="date">Date</label>
                    <input type="date" class="form-control" id="date" name="date" value="<%= event.getDate() %>" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="time">Time</label>
                    <input type="time" class="form-control" id="time" name="time" value="<%= event.getTime() %>" required>
                </div>
            </div>
            <div class="form-group">
                <label for="location">Location</label>
                <input type="text" class="form-control" id="location" name="location" value="<%= event.getLocation() %>" required>
            </div>
            <button type="submit" class="btn btn-primary">Update Event</button>
            <a href="events" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>