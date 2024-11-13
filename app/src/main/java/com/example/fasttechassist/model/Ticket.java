package com.example.fasttechassist.model;

public class Ticket {
    private String ticketId;
    private String title;
    private String description;
    private String room;
    private String status; // e.g., "Pending", "Processing", "Completed"
    private String createdBy; // Faculty member ID
    private String assignedTo; // Tech member ID
    private String facultyContact;
    private String techContact;
    private long timestampCreated;
    private long timestampCompleted;

    public Ticket() { } // Required for Firebase

    public Ticket(String ticketId, String title, String description, String room, String status,
                  String createdBy, String assignedTo, String facultyContact, String techContact,
                  long timestampCreated, long timestampCompleted) {
        this.ticketId = ticketId;
        this.title = title;
        this.description = description;
        this.room = room;
        this.status = status;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.facultyContact = facultyContact;
        this.techContact = techContact;
        this.timestampCreated = timestampCreated;
        this.timestampCompleted = timestampCompleted;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getFacultyContact() {
        return facultyContact;
    }

    public void setFacultyContact(String facultyContact) {
        this.facultyContact = facultyContact;
    }

    public String getTechContact() {
        return techContact;
    }

    public void setTechContact(String techContact) {
        this.techContact = techContact;
    }

    public long getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(long timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public long getTimestampCompleted() {
        return timestampCompleted;
    }

    public void setTimestampCompleted(long timestampCompleted) {
        this.timestampCompleted = timestampCompleted;
    }
}
