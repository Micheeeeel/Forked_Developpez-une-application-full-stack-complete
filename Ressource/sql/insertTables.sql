
-- Insert into tables


-- Insertion de données de test pour la table "user"
INSERT INTO users (email, username, password)
VALUES
  ('john@example.com', 'JohnDoe', 'password123'),
  ('jane@example.com', 'JaneSmith', 'securePass!'),
  ('bob@example.com', 'BobJohnson', '12345678');

-- Insertion de données de test pour la table "subject"
INSERT INTO subjects (name, description)
VALUES
  ('JavaScript', 'Langage de programmation pour le développement web.'),
  ('Java', 'Langage polyvalent orienté objet utilisé dans divers domaines.'),
  ('Python', 'Langage interprété largement utilisé en développement logiciel.'),
  ('Web3', 'Nouvelle version des technologies web avec un focus sur la décentralisation.'),
  ('C++', 'Langage de programmation pour le développement logiciel et les jeux vidéo.'),
  ('Data Science', 'Domaine utilisant des techniques statistiques pour l analyse de données.'),
  ('Mobile App Development', 'Création d applications pour les appareils mobiles.'),
  ('Artificial Intelligence', 'Simulation de processus intelligents par des machines.'),
  ('Cybersecurity', 'Protection des systèmes informatiques contre les menaces.');

-- Insertion de données de test pour la table "article"
INSERT INTO articles (subject_id, user_id, title, content, published_at)
VALUES
  (1, 1, 'Introduction to JavaScript', 'JavaScript is a popular programming language used for creating dynamic and interactive web applications.', '2023-06-06 14:30:00'),
  (2, 2, 'Getting Started with Java', 'Java is a versatile object-oriented programming language widely used in various domains.', '2023-06-06 14:30:00'),
  (3, 1, 'Python Basics', 'Python is an easy-to-learn interpreted language widely used in software development.', '2023-06-06 14:30:00'),
  (4, 3, 'Introduction to Web3', 'Web3 is a set of libraries that enables developers to interact with blockchain networks, focusing on decentralization.', '2023-06-06 14:30:00'),
  (1, 2, 'Advanced JavaScript Patterns', 'Dive deep into advanced JavaScript concepts and design patterns for more maintainable code.', '2023-06-07 10:15:00'),
  (3, 2, 'Machine Learning with Python', 'Leverage Python to dive into the exciting field of machine learning and create predictive models.', '2023-06-08 11:10:00'),
  (1, 3, 'Functional Programming in JavaScript', 'Discover the power of functional programming paradigms in JavaScript for more modular code.', '2023-06-11 12:20:00'),
  (4, 1, 'Web3 and Cryptocurrencies', 'Explore the intersection between Web3 and cryptocurrencies and their impact on finance.', '2023-06-09 09:45:00'),
  (2, 3, 'Java in Modern Web', 'Explore how Java fits into modern web development and interacts with frontend technologies.', '2023-06-10 15:55:00'),
  (2, 1, 'Building Scalable Java Applications', 'Learn best practices for building scalable Java applications for robust performance.', '2023-06-12 14:30:00');

-- Insertion de données de test pour la table "comment"
INSERT INTO comments (article_id, user_id, content, created_at)
VALUES
  (1, 2, 'Great article! Thanks for sharing.', '2023-06-06 14:30:00'),
  (1, 3, 'I found this really helpful.', '2023-06-06 14:30:00'),
  (3, 1, 'Python is my favorite language!', '2023-06-06 14:30:00');

-- Insertion de données de test pour la table "subscription"
INSERT INTO subscriptions (user_id, subject_id)
VALUES
  (1, 1),
  (1, 2),
  (2, 3),
  (3, 4);
