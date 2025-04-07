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
