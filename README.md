# job-search-springboot-project

Project Introduction

This Project is a web application designed to search for jobs, apply for positions, and manage their profiles. The application provides a platform for both companies to post job openings and candidates to find and apply for jobs that match their skills.

Functional Description

The project includes the following key features:

- Job Listings: Companies can post job recruitments, and users can browse through available job listings.
- Security: Log in with 2 roles: job seeker and employer.
- User Profile Management: Users can create and manage their profiles, including updating their resumes and contact details.
- Apply for Jobs: Users can apply for jobs by either using their saved resumes or uploading a new resume for specific applications.
- Recruitment Management: Companies can manage their job postings, view candidates who have applied.
- Search Jobs: Job seekers can search for jobs based on title, address, or company.
- Pagination: The application includes pagination for displaying job listings and candidates to enhance the user experience.

Deployment Guide (on local)
Prerequisites:

Java 17 or higher
Maven installed
MySQL
Eclipse (or any Java IDE) 

To run the project locally, follow these steps:

- Clone the Repository: git clone https://github.com/lhhuy99/job-search-springboot-project.git
- Open project and run 2 file sql in mysql-setup folder
- Build the project using Maven: mvn clean install
- Run the application: mvn spring-boot:run
- On browser access: http://localhost:8080/

NOTE: 
- Employer:
  + email: test1@gmail.com, password: test1
  + email: test2@gmail.com, password: test2
  + email: test3@gmail.com, password: test3
- Job seeker:
  + email: test4@gmail.com, password: test4
  + email: test5@gmail.com, password: test5
  + email: test6@gmail.com, password: test6
