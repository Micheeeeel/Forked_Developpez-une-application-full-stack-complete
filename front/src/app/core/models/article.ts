import { Comment } from './comment';

export class Article {
  userId!: number;
  subjectId!: number;
  title!: string;
  content!: string;
  publishedAt!: string;
  comments!: Comment[];
}
