package com.app.employeeManagement;

class EmailNotFoundException extends RuntimeException {
    
    EmailNotFoundException(String email) {
        super("Could not find employee with email " + email);
      }

}
