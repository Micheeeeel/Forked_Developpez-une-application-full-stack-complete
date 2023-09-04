import { Comment } from './comment';

export class ArticleWithComments {
  id!: number;
  userId!: number;
  userName!: string;
  subjectId!: number;
  subjectName!: string;
  title!: string;
  content!: string;
  publishedAt!: string;
  comments!: Comment[];
}
