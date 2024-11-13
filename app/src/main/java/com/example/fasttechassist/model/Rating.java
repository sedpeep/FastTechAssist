package com.example.fasttechassist.model;

public class Rating {
    private String facultyId; // ID of the faculty who created the rating
    private String ticketId; // ID of the ticket being rated
    private int rating; // Rating out of 5
    private String feedback; // Feedback comment

    public Rating() { } // Required for Firebase

    public Rating(String facultyId, String ticketId, int rating, String feedback) {
        this.facultyId = facultyId;
        this.ticketId = ticketId;
        this.rating = rating;
        this.feedback = feedback;
    }

    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) { this.facultyId = facultyId; }

    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
