package dto;

public class UploadDTO {
    private int seq;
    private String origin_name;
    private String sys_name;
    private  int board_seq;

    public UploadDTO() {
    }

    public UploadDTO(int seq, String origin_name, String sys_name, int board_seq) {
        this.seq = seq;
        this.origin_name = origin_name;
        this.sys_name = sys_name;
        this.board_seq = board_seq;
    }

    public int getSeq() {
        return seq;
    }

    public String getOrigin_name() {
        return origin_name;
    }

    public String getSys_name() {
        return sys_name;
    }

    public int getBoard_seq() {
        return board_seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setOrigin_name(String origin_name) {
        this.origin_name = origin_name;
    }

    public void setSys_name(String sys_name) {
        this.sys_name = sys_name;
    }

    public void setBoard_seq(int board_seq) {
        this.board_seq = board_seq;
    }
}
