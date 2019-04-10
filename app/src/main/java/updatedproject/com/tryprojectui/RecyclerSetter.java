package updatedproject.com.tryprojectui;

public class RecyclerSetter {

    String title, author, date;

    public RecyclerSetter(String title, String author, String date) {
        this.title = title;
        this.author = author;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }
}
