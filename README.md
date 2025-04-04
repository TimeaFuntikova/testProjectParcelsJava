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
/parcel-app/
│
├── backend/        → Spring Boot REST API (serves API + frontend)
├── processor/      → Core logic for file IO and validation
├── frontend/       → Angular app for parcel form input
└── docs/           → Documentation and JSON schema
```

---

## ▶️ How to Run the App

### Option 1: Development Mode

#### Run Backend (Spring Boot)
```bash
cd backend
mvn spring-boot:run
```

#### Run Frontend (Angular)
```bash
cd frontend
npm install
ng serve
```

Then open your browser at [http://localhost:4200](http://localhost:4200)

---

### Option 2: Production (Single App)
Build Angular first:
```bash
cd frontend
ng build --configuration production
```

Copy contents of `dist/` to:
```
backend/src/main/resources/static/
```

Build the full backend:
```bash
cd backend
mvn clean package
```

Then run:
```bash
java -jar target/parcel-app.jar
```

> The app will auto-launch your default browser.

---

## 🖱️ End-User Instructions

1. Launch the application by double-clicking the provided `.jar` or `.exe`
2. Fill out parcel details
3. Use:
   - **Next** → saves the parcel and clears the form
   - **Submit** → saves the parcel and clears pending list
   - **Clear** → clears form and draft data
4. Parcel ID is generated automatically
5. Parcels are saved to local files based on postcode

---

## 🔌 API Endpoints (when backend is running)

| Endpoint               | Description                   |
|------------------------|-------------------------------|
| `GET /api/parcels`     | Returns all stored parcels    |
| `POST /api/parcels`    | Adds a new parcel             |

---

## 📁 File Storage Details

- Files are created inside the current working directory
- Naming pattern: `parcela_<postcode>.json`
- All files follow a strict JSON schema
- No duplication: Parcel ID must be unique

---

## 🔧 Developer Notes

- Validation is mirrored on both frontend and backend
- JSON schema available in `/docs/schema.json`
- `InputData` and `Address` models shared across layers
- Easily extendable to use a database later

---

## 🏗️ Build & Deploy (for distribution)

Usage [`jpackage`](https://docs.oracle.com/en/java/javase/17/jpackage/overview/jpackage.html) or [`Launch4j`](http://launch4j.sourceforge.net/) to:

- Package the `.jar` as a `.exe` for Windows
- Bundle a JRE so no Java installation is needed
- Add a custom icon & metadata

> Final deliverable = double-clickable `.exe` or `.jar`

---

## 📌 Future Enhancements

- [ ] Dashboard for managing stored parcels
- [ ] Export parcels to CSV
- [ ] Cloud sync / storage backend
- [ ] User login (admin access control)

---

## 👨‍💻 Author

**timea funtikova**  
timea.funtik@gmail.com  
[github.com/TimeaFuntikova](https://github.com/TimeaFuntikova)

---

> Made with ☕, 💻, and a whole lotta love.
