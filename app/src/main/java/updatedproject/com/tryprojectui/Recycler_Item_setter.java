package updatedproject.com.tryprojectui;

public class Recycler_Item_setter {
    private int ImageBook;
    private String title, author;

    public Recycler_Item_setter(int ImageBook, String title, String author) {
        this.ImageBook = ImageBook;
        this.title = title;
        this.author = author;
    }

    public int getImageBook() {
        return ImageBook;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
