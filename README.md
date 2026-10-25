# Shagani – Spring Boot online shop

Homemade pickles, snacks and sweets shop for Shagani, Ellanthakunta, Telangana.
Built with Java, Spring Boot 3, Thymeleaf, Spring Data JPA and an H2 file database.

## What it does
- Home page with categories, best sellers, shop grid with category filter and search
- Cart (250 g / 500 g / 1 kg sizes) kept per visitor
- Checkout form (name, phone, address, Cash or UPI) with validation
- Every order is **saved in the database**, then the customer gets a button that sends the order to WhatsApp 8121842939
- Owner page at `/admin/orders` (login: user `admin`) to see orders and mark them done
- Owner page at `/admin/products` to upload or remove each product's photo

## Run it
You need Java 17 or newer and Maven (or open the folder in IntelliJ / VS Code / Eclipse).

```
mvn spring-boot:run
```

Open http://localhost:8080

Admin orders page: http://localhost:8080/admin/orders
- username: `admin`
- password: the `ADMIN_PASSWORD` environment variable. If you don't set it, the password is `change-me-now`.
  **Set your own before putting the site online**, e.g. `ADMIN_PASSWORD=MyStrongPass123 mvn spring-boot:run`

## Folder map (what to edit)
| What | Where |
|------|-------|
| Products and prices (first run only) | `src/main/java/com/shagani/shop/DataLoader.java` |
| Phone, location, FSSAI number | `src/main/resources/application.properties` |
| Product photos | upload at `/admin/products` (saved in `uploads/`) |
| Colours and styling | `src/main/resources/static/css/style.css` |
| Pages | `src/main/resources/templates/*.html` |
| Database files (created on first run) | `data/` |

### Product photos
Log in at http://localhost:8080/admin/products (same `admin` login), choose a product and upload
a JPG, PNG or WebP photo (up to 5 MB). It appears on the shop straight away. Products without a
photo show an emoji. Use only photos you took yourself.
Uploaded photos are saved in the `uploads/` folder next to the app and their names are stored in
the database, so keep both the `data/` and `uploads/` folders when you move or back up the shop.

### Changing prices after the first run
`DataLoader` only adds products when the database is empty. To reload after editing it,
stop the app, delete the `data/` folder and start again (this also deletes saved orders).

### FSSAI number
Put it in `application.properties` as `shagani.fssai=...`. It then appears in the footer.
Leave it empty until you have received it.

## Notes
- The 500 g prices were estimated from the 250 g and 1 kg prices. Please check them.
- H2 is fine for a small shop. For heavy use, switch to MySQL or PostgreSQL by changing the
  datasource settings and adding that driver in `pom.xml`.
- To go live, deploy the jar (`mvn package`, then `java -jar target/shop-1.0.0.jar`) on a server
  or a Java hosting service, and use HTTPS.
