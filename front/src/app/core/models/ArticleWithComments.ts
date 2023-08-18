import { Comment } from './comment';

export class ArticleWithComments {
  id!: number;
  userId!: number;
  subjectId!: number;
  title!: string;
  content!: string;
  publishedAt!: string;
  comments!: Comment[];
}
