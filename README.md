# 📦 Parcel Management App

- A full-stack application designed to create, validate, and organize parcel delivery data.
- Parcels are grouped by postcode and stored in JSON files automatically.
- Built for simplicity, reliability, and real-world use by non-technical users.

---

## ✨ Features

- ✅ Clean, user-friendly form for entering parcel details
- ✅ Auto-generated 12-digit Parcel ID
- ✅ Input validation on both frontend & backend
- ✅ Real-time feedback (error messages, "copied to clipboard", etc.)
- ✅ Grouping of parcels into JSON files by postcode
- ✅ Local file-based storage — no external database needed
- ✅ Modular codebase (FE/BE separation for easy scaling)
- ✅ Auto-launch browser on start

---

## ⚙️ Prerequisites
- Before building or running the app, make sure you have the following installed:
- Java 21+ – Required to build and run the Spring Boot backend
- Node.js & npm – Required for running the Angular frontend
- Angular CLI – Install with npm install -g @angular/cli
- Maven – For building the backend (Spring Boot)
- (Optional): Launch4j – For packaging the .jar into a native .exe

---

## 📦 Dependencies

#### Backend (Spring Boot):
- spring-boot-starter-web – REST controller support
- spring-boot-starter-data-jpa – For potential database support
- gson – JSON parsing and manipulation
- lombok – Boilerplate code reduction
- springdoc-openapi – Swagger UI for REST docs
- h2database – Lightweight local DB (optional/test)
- slf4j – Logging

#### Frontend (Angular):
- @angular/forms – Template-driven forms
- rxjs – Reactive programming
- @angular/common/http – HTTP requests

#### Custom services:
- ParcelRestService – Backend communication
- ParcelFormService – Handles form logic
- ParcelBufferService – Stores submitted parcel state

---

## 🧠 Key Design Decisions & Challenges
  ✅ JSON Storage vs Database
  The application writes parcels to structured JSON files based on postcode instead of using a traditional database. This was chosen to simplify deployment and fit project requirements. However, for scalability and better query support, a database is recommended in production scenarios.

✅ Modularization
The frontend and backend were cleanly separated:

- Angular manages all form logic, validation, and feedback.

- Spring Boot handles REST APIs, validation, and file processing.

- Business logic (e.g. duplicate detection, JSON merging) is isolated into service classes for easy testing and reuse.

✅ Input Validation 
- Both layers validate:
- Required fields
- Format of house numbers and postcodes
- Duplicates (checked against stored JSON files)

✅ Executable Distribution
- To improve UX for non-technical users, the final app:
- Auto-launches the browser
- Can be run via .exe or .bat without needing manual Java installation
- Uses Launch4j for packaging

✅ Challenges
- 🧩 Mapping between DTOs and internal models (due to nested structures)

- 📁 Managing parcel persistence cleanly across two JSON structures

- 📦 Delivering a "real app" feel without external infrastructure


## 🧱 Project Structure
```plaintext
/fe/                              → Frontend Angular project
│
├── parcel-ui/                    → Angular app for parcel form input
├── src/                          → (Unused/misc in frontend)
├── testProjectParcels/          → Shared or placeholder directory
├── TestProjectParcelsJava/      → Legacy or transitional naming
├── package-lock.json            → Node.js dependency lock file
└── fe.iml                       → IntelliJ project metadata

/test-project-parcels-java/      → Backend Spring Boot project
│
├── ParcelApp/                   → Final app distribution folder
│   ├── docs/                    → User documentation (e.g. manual, README)
│   ├── parcel-app.bat          → Script to launch the JAR
│   ├── parcel-app.exe          → Windows executable (Launch4j)
│   └── parcel-app.jar          → Compiled backend JAR (Spring Boot)
│
├── src/                         → Main source code (Java)
│   └── main/java/com/example/testProjectParcels/
│       ├── controller/          → REST API controllers
│       ├── service/             → Business logic and JSON handling
│       ├── model/               → Domain models (DTOs, InputData, etc.)
│       ├── enums/               → Enum definitions
│       └── browserLauncher/     → Class that opens the app in the browser
│
├── target/                      → Compiled build files (auto-generated)
├── pom.xml                      → Maven build configuration
└── README.md                    → Project instructions or description
```

## ▶️ How to Run the App

### Option 1: Development Mode

#### Run Backend (Spring Boot)
```bash
cd backend
mvn spring-boot:run
```

#### Run Frontend (Angular)
```bash
cd fe/parcel-ui
npm install
ng serve
```

Then open your browser at [http://localhost:4200](http://localhost:4200)

---

### Option 2: Production (Single App)
Build Angular first:
```bash
cd fe/parcel-ui
ng build --configuration=production
```

Copy contents of `dist/` to:
```
../../src/main/resources/static/
```

Build the full backend:
```bash
./mvnw clean package
```

Then run:
```bash
java -jar target/parcel-app.jar
```

> The app will auto-launch your default browser.

---

## 🖱️ End-User Instructions

1. Launch the application by double-clicking the provided `.exe`
2. Fill out parcel details
3. Use:
   - **Next** → saves the parcel and clears the form
   - **Submit** → saves the parcel and clears pending list
   - **Clear** → clears form and draft data
4. Parcel ID is generated automatically
5. Parcels are saved to local files based on postcode

---

## 🔌 API Endpoints (when backend is running)

| Endpoint                 | Description                   |
|--------------------------|-------------------------------|
| `GET /api/parcels/count` | Returns all stored parcels    |
| `POST /api/parcels/add`  | Adds a new parcel             |

---

## 📁 File Storage Details

- Files are created inside the current working directory
- Naming pattern: `parcels_<postcode>.json`
- All files follow a strict JSON schema
- No duplication: Parcel ID must be unique

---

## 🔧 Developer Notes

- Validation is mirrored on both frontend and backend
- JSON schema available in `/docs/schema.json`
- `ParcelData` and `Address` models shared across layers
- Easily extendable to use a database later

---

## 🏗️ Build & Deploy (for distribution)

Usage of [`Launch4j`](http://launch4j.sourceforge.net/) to:

- Package the `.jar` as a `.exe` for Windows
- Bundle a JRE so no Java installation is needed

> Final deliverable = double-clickable `.exe` or `.jar` (exe recomended)

---

## 👨‍💻 Author

**timea funtikova**  
timea.funtik@gmail.com  
[github.com/TimeaFuntikova](https://github.com/TimeaFuntikova)
