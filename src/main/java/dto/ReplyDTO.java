package dto;

import java.sql.Timestamp;

public class ReplyDTO {
    private int seq;
    private String writer;
    private String contents;
    private Timestamp write_date;
    private int board_seq;

    public ReplyDTO() {
    }

    public ReplyDTO(int seq, String writer, String contents, Timestamp write_date, int board_seq) {
        this.seq = seq;
        this.writer = writer;
        this.contents = contents;
        this.write_date = write_date;
        this.board_seq = board_seq;
    }

    public int getSeq() {
        return seq;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public Timestamp getWrite_date() {
        return write_date;
    }

    public int getBoard_seq() {
        return board_seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setWrite_date(Timestamp write_date) {
        this.write_date = write_date;
    }

    public void setBoard_seq(int board_seq) {
        this.board_seq = board_seq;
    }
}
