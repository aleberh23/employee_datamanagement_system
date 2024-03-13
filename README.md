<h1 align="center">Employee Data Management System</h1>


This system, which I developed as the final project for my Software Development Technician career, originates from a practical need in the healthcare sector. It was born out of the specific requirements conveyed to me by two HR professionals working within a healthcare institution. With their insights and needs in mind, I crafted a solution tailored to the unique demands of managing human resources in a hospital clinic environment. This project serves as a testament to my ability to translate real-world challenges into effective software solutions, demonstrating my skills and expertise gained throughout my academic journey. The main parts of the system are:

<h3>Virtual Employee Profile Management:</h3>
This integral component serves as a comprehensive repository for each employee, housing essential details such as their name, email, phone number, birth date, date of admission, assigned sector, etc. Within this section, the system further categorizes information into four distinct subsections:

- **Contracts:** Provides insight into the various contracts associated with the employee, including details on their status (active, expired, or converted to permanent).
- **Family Charges:** Contains pertinent information concerning the family members of the employee.
- **Addresses:** Offers a centralized location for storing and managing different addresses associated with the employee, ensuring accurate record-keeping.
- **Licenses:** Tracks all licenses acquired or anticipated by the employee, encompassing a range of categories such as vacations, exams, family member sickness, marriage, and more. This section provides visibility into the employee's licensure status, aiding in efficient management of time-off requests and related administrative tasks.

<h3>Employee Absences Management:</h3>
This integral feature empowers HR staff to meticulously track employee absences and leaves of various types, including sick leave, exam leave, family leave, and bereavement leave. The system streamlines the process by providing a dedicated section where HR personnel can effortlessly log absences for specific employees on particular days. These absences are seamlessly reflected in the daily report, ensuring accurate and up-to-date records of employee attendance and leave usage.

<h3>Vacation Entitlement Verification:</h3>
This component encompasses a class that serves as a proof of the vacation days allotted to each employee within a specific period. The system automatically calculates these days based on the employee's hire date and years of service, adhering to the clinic's policies. However, prior to generating an ordinary license for an employee, HR personnel must utilize this class to verify the entitlement of vacation days. This verification ensures that the employee's vacation allocation is accurate and up-to-date before proceeding with the issuance of the license.

<h3>Daily Report Generation:</h3>
In this component, HR employees generate a daily report to capture vital information pertinent to daily operations. The report is generated each day and encompasses details such as employees with active contracts for that date, employees currently on various types of licenses, absences recorded for the day, and employees with scheduled retirement dates within the remainder of the year. This comprehensive report facilitates effective management of workforce dynamics and aids in planning and decision-making processes within the HR department. This component not only facilitates the daily generation of reports but also ensures accessibility to previous and subsequent daily reports at any time. HR employees can consult earlier or later daily reports as needed, with the system automatically updating them to reflect any new information

<h3>Comprehensive Reporting Capabilities:</h3>
In this feature set, the software offers extensive reporting functionalities, empowering HR personnel to generate detailed PDF reports efficiently. Users can produce comprehensive reports containing the complete data file of an employee, encompassing personal information, labor records, contact details, addresses, family charges, active and completed contracts, as well as licenses.
Additionally, the system facilitates the generation of PDF reports specifically tailored to individual daily summaries selected by the user. This capability ensures that HR personnel can swiftly access and share precise information, optimizing decision-making processes and enhancing overall efficiency within the department.

<h3>Streamlined Data Filtering:</h3>
This component simplifies data management by providing basic filtering capabilities for the lists of employees, contracts, and licenses. Users can easily apply filters to these lists to locate specific information swiftly. This functionality enhances efficiency by allowing HR personnel to quickly find relevant employees, contracts, or licenses within the system.

<h3>User Roles and Access Control:</h3>
The system implements two distinct roles to manage access and privileges effectively. 
The first role, designated as "HR Personnel," is tailored for employees within the HR department. Users assigned to this role possess complete access to all features and data within the system, enabling them to perform comprehensive HR tasks.

The second role, named "Entrance Desk," is specifically designed for employees tasked with managing attendance and absences. Users in this role have limited access, restricted solely to the functionality required for generating absences. This streamlined access ensures that entrance desk personnel can efficiently carry out their responsibilities without unnecessary access to other system features.

## Technologies used:

<h3>Backend:</h3>
The project was developed using Spring Boot as the main framework. Spring Boot facilitates the creation of Java applications based on the Spring framework with minimal configuration and a convention-over-configuration approach. This provides a solid and efficient foundation for application development.
In addition, Spring Security was utilized for managing different roles and users, providing robust authentication and authorization features. Spring Data JPA was employed as an ORM (Object-Relational Mapping) tool, simplifying the interaction with the database and enhancing data access capabilities.

<h3>Frontend with HTML Templates and Bootstrap Styles:</h3>
The user interface was implemented using HTML templates, taking advantage of the versatility and ease of development they offer. Additionally, I integrated small JavaScript scripts with JQuery for form validations in the frontend. Bootstrap styles were utilized to ensure an attractive design. Bootstrap simplifies the creation of appealing user interfaces.

<h3>Thymeleaf Template Engine:</h3>
Thymeleaf was used for dynamic HTML page rendering and integration with the backend. This template engine allows the incorporation of dynamic data into HTML pages, facilitating the presentation of server-generated dynamic information.

<h3>PostgreSQL Database:</h3>
PostgreSQL was chosen as the database management system for storing and retrieving data.

<h3>Report Generation with JasperSoft:</h3>
JasperSoft was implemented for report generation. JasperSoft simplifies the creation of detailed and visually appealing reports, offering robust capabilities for data presentation.

The combination of Spring Boot, HTML with Bootstrap, Thymeleaf, PostgreSQL, and JasperSoft provided a solid and comprehensive technological foundation for the project. This architecture enabled efficient development of a web application with a user-friendly interface, a reliable database, and the ability to generate detailed reports.


## Images:
Here are some screenshots of the working system. The displayed information does not correspond to real individuals; rather, it consists of test data.
<div style="display: flex; flex-wrap: wrap;">
    <img src="https://github.com/aleberh23/employee_datamanagement_system/assets/158856472/183f471f-84c4-40c9-9876-38729c902bba" alt="main menu" style="width: 150px; margin: 5px;">
    <img src="https://github.com/aleberh23/employee_datamanagement_system/assets/158856472/228f7da8-cb0f-4607-b461-077225032af4" alt="login" style="width: 150px; margin: 5px;">
    <img src="https://github.com/aleberh23/employee_datamanagement_system/assets/158856472/14503bcf-940b-4c2d-ba04-fffcd9ba639d" alt="licences" style="width: 150px; margin: 5px;">
    <img src="https://github.com/aleberh23/employee_datamanagement_system/assets/158856472/9930a316-e4ac-4d98-9168-18e8c2916955" alt="family charges" style="width: 150px; margin: 5px;">
    <img src="https://github.com/aleberh23/employee_datamanagement_system/assets/158856472/8fd7d250-24d4-4601-b6db-50965cc21f4a" alt="employee view" style="width: 150px; margin: 5px;">
    <img src="https://github.com/aleberh23/employee_datamanagement_system/assets/158856472/406b05ae-6288-4139-98b3-ceaaadbeede0" alt="employee detail" style="width: 150px; margin: 5px;">
    <img src="https://github.com/aleberh23/employee_datamanagement_system/assets/158856472/949235db-c814-4a20-bdc5-83f49273c861" alt="daily report" style="width: 150px; margin: 5px;">
    <img src="https://github.com/aleberh23/employee_datamanagement_system/assets/158856472/e5f41a23-f5f9-4fc0-b4f6-6b386ec40d13" alt="contracts" style="width: 150px; margin: 5px;">
    <img src="https://github.com/aleberh23/employee_datamanagement_system/assets/158856472/1e398f37-5e90-49ad-81ab-21d29c19c484" alt="adresses" style="width: 150px; margin: 5px;">
    <img src="https://github.com/aleberh23/employee_datamanagement_system/assets/158856472/29eac797-cc81-4593-87ac-76462d1d14d0" alt="absences" style="width: 150px; margin: 5px;">
    <img src="https://github.com/aleberh23/employee_datamanagement_system/assets/158856472/967ebac1-c629-48ab-8bc5-6e9bec29a844" alt="vacation days automatic calculation" style="width: 150px; margin: 5px;">
    <img src="https://github.com/aleberh23/employee_datamanagement_system/assets/158856472/04def1e4-9621-4a92-8c1b-50f8fe45b9da" alt="new employee" style="width: 150px; margin: 5px;">
</div>

## Installation instructions:
Before proceeding with the installation steps, you must have the following prerequisites installed:

- JDK 20 (for WINDOWS 64 bits: https://download.oracle.com/java/20/archive/jdk-20.0.2_windows-x64_bin.exe)
- PostgreSQL server running on localhost:5432 (for WINDOWS 64 bits: https://sbp.enterprisedb.com/getfile.jsp?fileid=1258893)

  <h3>Instalation Steps:</h3>
