## **FAST Tech Assist App**

### **Overview**
The **FAST Tech Assist App** is a robust Android application designed to streamline IT support processes for faculty at FAST University. It enables real-time ticket submission, assignment, tracking, and resolution for technical issues such as projector malfunctions, laptop connector problems, and Wi-Fi disruptions. The app fosters efficient communication between faculty and technical staff, ensuring timely problem resolution.

---

### **Key Features**
1. **Ticket Management**:
   - Real-time ticket submission by faculty.
   - Tickets categorized into statuses: *To Be Assigned*, *In Process*, *Completed*.
   - Assign tickets to specific IT staff.

2. **Notifications**:
   - Instant notifications for ticket status updates.

3. **Location Mapping**:
   - Track and manage issue locations within campus facilities.

4. **Feedback System**:
   - A 5-star rating system with optional comments for completed tickets.
   - Feedback visible only to the admin for quality assurance.

5. **User Roles**:
   - **Faculty Members**: Submit tickets and mark them as resolved.
   - **Technical Staff**: Receive and resolve assigned tickets.
   - **Admin**: Oversee ticket assignments and view feedback.

6. **Comprehensive Reporting**:
   - Detailed logs of issue frequency and resolution times for reporting and analysis.

---

### **Technical Stack**
- **Frontend**: Java (Android Studio).
- **Backend**: Firebase for real-time database, authentication, and storage.
- **Database**: Firebase Realtime Database for user and ticket management.

---

### **How It Works**
1. **Login and Authentication**:
   - Secure login for faculty and technical staff using Firebase Authentication.

2. **Ticket Submission**:
   - Faculty members create tickets by filling out details like the issue description, room number, and category.
   - Tickets are automatically added to the database and assigned to the appropriate status.

3. **Assignment and Resolution**:
   - Admin assigns tickets to available technical staff.
   - Technical staff resolves the issue and updates the ticket status.

4. **Feedback**:
   - Faculty members rate the service after marking a ticket as completed.
   - Admins access feedback for performance monitoring.

---

### **Installation and Setup**
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/fast-tech-assist.git
   ```
2. Open the project in **Android Studio**.
3. Configure Firebase:
   - Add your `google-services.json` file to the `app/` directory.
4. Build and run the app on an emulator or physical device.

---

### **Roadmap**
- **Future Enhancements**:
  - Push notifications for real-time updates.
  - Enhanced analytics dashboard for admins.
  - Integration of voice-based ticket creation.

---

### **Contributing**
Contributions are welcome! If you’d like to contribute:
1. Fork the repository.
2. Create a feature branch: 
   ```bash
   git checkout -b feature-name
   ```
3. Submit a pull request.

