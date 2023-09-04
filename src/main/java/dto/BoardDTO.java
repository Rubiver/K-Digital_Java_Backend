package dto;

import java.sql.Timestamp;

public class BoardDTO {
    private int seq;
    private String writer;
    private String title;
    private String contents;
    private Timestamp write_date;
    private int view_count;

    public BoardDTO() {
    }

    public BoardDTO(int seq, String writer, String title, String contents, Timestamp write_date, int view_count) {
        this.seq = seq;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.write_date = write_date;
        this.view_count = view_count;
    }

    public int getSeq() {
        return seq;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Timestamp getWrite_date() {
        return write_date;
    }

    public int getView_count() {
        return view_count;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setWrite_date(Timestamp write_date) {
        this.write_date = write_date;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }
}
