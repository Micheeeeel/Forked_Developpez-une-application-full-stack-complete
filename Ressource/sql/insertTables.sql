
-- Insert into tables


-- Insertion de données de test pour la table "user"
INSERT INTO users (email, username, password)
VALUES
  ('john@example.com', 'JohnDoe', 'password123'),
  ('jane@example.com', 'JaneSmith', 'securePass!'),
  ('bob@example.com', 'BobJohnson', '12345678');

-- Insertion de données de test pour la table "subject"
INSERT INTO subjects (name)
VALUES
  ('JavaScript'),
  ('Java'),
  ('Python'),
  ('Web3');

-- Insertion de données de test pour la table "article"
INSERT INTO articles (subject_id, user_id, title, content)
VALUES
  (1, 1, 'Introduction to JavaScript', 'JavaScript is a popular programming language...'),
  (2, 2, 'Getting Started with Java', 'Java is a high-level programming language...'),
  (3, 1, 'Python Basics', 'Python is an easy-to-learn programming language...'),
  (4, 3, 'Introduction to Web3', 'Web3 is a set of libraries that allows...');

-- Insertion de données de test pour la table "comment"
INSERT INTO comments (article_id, user_id, content)
VALUES
  (1, 2, 'Great article! Thanks for sharing.'),
  (1, 3, 'I found this really helpful.'),
  (3, 1, 'Python is my favorite language!');

-- Insertion de données de test pour la table "subscription"
INSERT INTO subscriptions (user_id, subject_id)
VALUES
  (1, 1),
  (1, 2),
  (2, 3),
  (3, 4);
