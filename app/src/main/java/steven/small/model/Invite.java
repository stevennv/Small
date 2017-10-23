package steven.small.model;

/**
 * Created by Admin on 10/23/2017.
 */

public class Invite {
//    public Invite(String id, String name, String avatar, boolean isOnline, boolean isSelect) {
//        this.id = id;
//        this.name = name;
//        this.avatar = avatar;
//        this.isOnline = isOnline;
//        this.isSelect = isSelect;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    private String avatar;
    private boolean isOnline;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private boolean isSelect;
}
