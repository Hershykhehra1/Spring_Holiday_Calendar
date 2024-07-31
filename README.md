# US Holidays Web Application

This project is a web application that allows users to view, add, update, and delete US holidays. Built with Java, Spring MVC, and FreeMarker templates, it demonstrates a simple CRUD (Create, Read, Update, Delete) functionality with a user-friendly interface.

## Project Structure

### Model

`HolidayEntry.java`: This class represents a holiday entry with attributes for the date, name, and formatted date. The `idSeed` static variable ensures each holiday has a unique ID. It includes methods for getting and setting these attributes and a method for comparing holiday entries by date.

### Controller

`IndexController.java`: This controller handles the main logic for displaying the list of holidays, as well as adding, updating, and deleting holiday entries. 

### Templates

`index.ftlh`: This template displays the list of holidays in a table format with options to update or delete each entry. It includes a button to navigate to the add holiday form.

`addHoliday.ftlh`: This template provides a form for adding a new holiday. It includes dropdowns for selecting the day, month, and year, an input field for the holiday name, and an error message display if any field is empty or if the holiday already exists.

`updateHoliday.ftlh`: This template provides a form for updating an existing holiday. It includes pre-filled dropdowns and input fields with the current holiday data, and an error message display if any field is empty or if the holiday already exists.

## How It Works

### Viewing Holidays

Navigate to the main page to see a list of all holidays.

### Adding a Holiday

Click the "Add Holiday" button and fill out the form to add a new holiday.

### Updating a Holiday

Click the "Update" link next to a holiday entry, modify the details, and submit the form to update the holiday.

### Deleting a Holiday

Click the "Delete" link next to a holiday entry to remove it from the list.

## How to Run

### Setup

1. Make sure you have Java, Maven, and Spring installed in your IDE.
2. Run the Spring Boot app and navigate to http://localhost:8080/ to interact with the application.
