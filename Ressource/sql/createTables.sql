-- Script SQL pour créer les tables du projet MDD

-- Table: User
CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL
);

-- Table: Subject
CREATE TABLE subjects (
  subject_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

-- Table: Article
CREATE TABLE articles (
  article_id INT AUTO_INCREMENT PRIMARY KEY,
  subject_id INT NOT NULL,
  user_id INT NOT NULL,
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (subject_id) REFERENCES subject(subject_id),
  FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- Table: Comment
CREATE TABLE comments (
  comment_id INT AUTO_INCREMENT PRIMARY KEY,
  article_id INT NOT NULL,
  user_id INT NOT NULL,
  content TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (article_id) REFERENCES article(article_id),
  FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- Table: Subscription
CREATE TABLE subscriptions (
  subscription_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  subject_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user(user_id),
  FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);