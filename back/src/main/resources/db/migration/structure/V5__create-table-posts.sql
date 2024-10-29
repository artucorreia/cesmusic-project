CREATE TABLE posts (
    id UUID PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    summary VARCHAR(200) NOT NULL,
    cape_image_path VARCHAR(255),
    content TEXT NOT NULL,
    user_id UUID REFERENCES users(id) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);