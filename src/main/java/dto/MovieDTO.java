package dto;

public class MovieDTO {
    private int seq;
    private String title;
    private String genre;

    public MovieDTO() {
    }

    public MovieDTO(int seq, String title, String genre) {
        this.seq = seq;
        this.title = title;
        this.genre = genre;
    }

    public int getSeq() {
        return seq;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
