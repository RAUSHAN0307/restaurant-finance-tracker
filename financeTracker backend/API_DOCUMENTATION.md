# FinanceTracker REST API Documentation (For Frontend Developers)

**Base URL:** `http://localhost:3000`

---

## 1. Vendor APIs

### `POST /vendors/{userId}/add`
* **Description:** Creates a new vendor associated with the given user.
* **Request Body:**
```json
{
  "name": "API Test Vendor",
  "phoneNumber": 999888777,
  "email": "apitest@test.com",
  "amountPending": 1234.00,
  "address": "API City",
  "note": "api testing",
  "dueDate": "2026-12-31"   // Format: YYYY-MM-DD
}
```
* **Response:** Returns the saved `Vendor` object including the generated `vendorId`.

### `PUT /vendors/{userId}/update/{vendorId}`
* **Description:** Updates an existing vendor.
* **Request Body:** Same JSON format as `add`. All fields are required.
* **Response:** Returns the updated `Vendor` object.

### `DELETE /vendors/{userId}/delete/{vendorId}`
* **Description:** Deletes a specific vendor.
* **Request Body:** None
* **Response:** String: `"Vendor deleted successfully"`

### `GET /vendors/{userId}/get/{vendorId}`
* **Description:** Retrieves the full details of a specific vendor.
* **Request Body:** None
* **Response:**
```json
{
  "vendorId": 21,
  "name": "API Test Vendor",
  "phoneNumber": 999888777,
  "email": "apitest@test.com",
  "amountPending": 1234.0,
  "address": "API City",
  "note": "api testing",
  "createdAt": "2026-03-05",
  "dueDate": "2026-12-31",
  "userId": 1
}
```

### `GET /vendors/{userId}/all`
* **Description:** Retrieves a list of all vendors owned by the user.
* **Request Body:** None
* **Response:** `[ {Vendor_1}, {Vendor_2}, ... ]`

### `GET /vendors/{userId}/names`
* **Description:** Retrieves only IDs and Names (useful for dropdown menus).
* **Request Body:** None
* **Response:**
```json
[
  { "vendorId": 1, "name": "Vendor Corp 1" },
  { "vendorId": 4, "name": "Vendor Corp 4" }
]
```

---

## 2. Inventory APIs

### `POST /inventory/{userId}/add`
* **Description:** Creates a new inventory item.
* **Request Body:**
```json
{
  "itemName": "Rice Packets",
  "category": "Grains",
  "unit": "boxes",
  "unitCost": 99.99,
  "quantity": 100,
  "usedQuantity": 10,
  "minimumQuantity": 20,
  "note": "supplier notes",
  "vendorId": 21   // <--- ID of the associated supplier
}
```
* **Response:** Returns the saved `Inventory` object including the generated `inventoryId`, and `vendorName`.

### `PUT /inventory/{userId}/update/{inventoryId}`
* **Description:** Updates an existing inventory item.
* **Request Body:** Same JSON format as `add`.
* **Response:** Returns the updated `Inventory` object.

### `DELETE /inventory/{userId}/delete/{inventoryId}`
* **Description:** Deletes a specific inventory item.
* **Request Body:** None
* **Response:** 
```json
{
  "success": true,
  "message": "Inventory deleted successfully"
}
```

### `GET /inventory/{userId}/get/{inventoryId}`
* **Description:** Retrieves the full details of a specific inventory item.
* **Request Body:** None
* **Response:** 
```json
{
  "inventoryId": 41,
  "itemName": "Rice Packets",
  "category": "Grains",
  "unit": "boxes",
  "unitCost": 99.99,
  "quantity": 100,
  "usedQuantity": 10,
  "minimumQuantity": 20,
  "note": "supplier notes",
  "currentDate": "2026-03-06",
  "vendorName": "API Test Vendor",
  "vendorId": 21,
  "userId": 1
}
```

### `GET /inventory/{userId}/all`
* **Description:** Retrieves a list of all inventory items owned by the user.
* **Request Body:** None
* **Response:** `[ {Inventory_1}, {Inventory_2}, ... ]`

### `GET /inventory/{userId}/vendor/{vendorId}`
* **Description:** Retrieves all inventory items supplied by a specific vendor.
* **Request Body:** None
* **Response:** `[ {Inventory_A}, {Inventory_B}, ... ]`

---

## 3. Message / Notification APIs

### `POST /messages/{userId}/generate`
* **Description:** Scans Vendors for due payments & Inventory for low stock limits. Generates and returns immediate alerts.
* **Request Body:** None
* **Response:** `[ {Message_1}, {Message_2}, ... ]`

### `GET /messages/{userId}/all`
* **Description:** Retrieves all historical and current notification messages.
* **Request Body:** None
* **Response:** `[ {Message_1}, {Message_2}, ... ]` (See specific Response format below)

### `GET /messages/{userId}/unread`
* **Description:** Retrieves only unread notifications (`read: false`).
* **Request Body:** None
* **Response:** 
```json
[
  {
    "messageId": 1,
    "type": "PENDING_PAYMENT",
    "title": "Pending Payment for Vendor Corp 4",
    "content": "Vendor Vendor Corp 4 (ID: 4) has a pending payment of ₹6960.0.",
    "referenceId": 4,      // The VendorId
    "referenceType": "VENDOR",
    "createdAt": "2026-03-06T22:06:09.489124",
    "userId": 1,
    "read": false
  },
  {
    "messageId": 6,
    "type": "LOW_STOCK",
    "title": "Low Stock: Dummy Item 2",
    "content": "Item Dummy Item 2 (ID: 2) has only 16 boxes remaining.",
    "referenceId": 2,      // The InventoryId
    "referenceType": "INVENTORY",
    "createdAt": "2026-03-06T22:06:09.503652",
    "userId": 1,
    "read": false
  }
]
```

### `GET /messages/{userId}/type/{type}`
* **Description:** Retrieves notifications filtered by `type`.
* **Path Variables:** `{type}` must be either `LOW_STOCK` or `PENDING_PAYMENT`.
* **Request Body:** None
* **Response:** `[ {Message_xyz}, ... ]`

### `PUT /messages/{userId}/read/{messageId}`
* **Description:** Updates the `read` flag to `true` for a specific message.
* **Request Body:** None
* **Response:** Returns the updated `Message` object.

### `PUT /messages/{userId}/read-all`
* **Description:** Marks every single unread message belonging to the user as read.
* **Request Body:** None
* **Response:** String: `"All messages marked as read"`
