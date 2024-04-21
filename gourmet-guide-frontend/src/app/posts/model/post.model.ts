export interface Post {
  id: number;

  image?: File;

  publisher: string;

  title: string;

  text: string;

  publishDate: Date;

  pinned: boolean;

  likes: Array<UserLike>;

  comments: Array<UserComment>;

  cop: string;

  likedByUser: boolean;

  totalPosts: number;
}

export interface UserComment {
  id: number;
  userId: number;
  text: string;
}

export interface UserLike {
  id: number;
  userId: number;
  pressed: boolean;
}
