package steven.small.model;

/**
 * Created by TruongNV on 10/7/2017.
 */

public class OTT {
    public OTT(int type, int image1, int image2, int image3){
        this.type = type;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }
    private int type;
    private int image1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public int getImage3() {
        return image3;
    }

    public void setImage3(int image3) {
        this.image3 = image3;
    }

    private int image2;
    private int image3;

}
