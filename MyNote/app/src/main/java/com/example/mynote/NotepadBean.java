package com.example.mynote;

public class NotepadBean {
    private String id;
    private String notepadContent;//记录的内容
    private String notepadTime;//保存记录的时间
    private String photo;


    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotepadContent() {
        return notepadContent;
    }

    public void setNotepadContent(String notepadContent) {
        this.notepadContent = notepadContent;
    }

    public String getNotepadTime() {
        return notepadTime;
    }

    public void setNotepadTime(String notepadTime) {
        this.notepadTime = notepadTime;
    }
}
