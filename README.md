# Student API v1.0 - documentation by Zhumabek Abylai
	## Student API

Spring Boot CRUD application for managing students.

| Method | URL | Description |
|---|---|---|
| GET | /api/info | Author info |
| GET | /api/students | Get all students |
| GET | /api/students/{id} | Get one student |
| POST | /api/students | Create a student |
| PUT | /api/students/{id} | Update a student |
| DELETE | /api/students/{id} | Delete a student |

## Changes by Zhumabek Abylai (feature/zhumabek-abylai-crud)
- Added Student entity and repository.
- Implemented CRUD controller.
- Improved UPDATE: PUT /api/students/{id} validates input and returns
  404 Not Found for a nonexistent student instead of creating a new one.
Author: Zhumabek Abylai
