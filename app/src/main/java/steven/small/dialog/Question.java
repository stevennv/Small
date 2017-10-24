package steven.small.dialog;

/**
 * Created by Admin on 10/24/2017.
 */

public class Question {
    private String url;
    private String question;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnwserA() {
        return anwserA;
    }

    public void setAnwserA(String anwserA) {
        this.anwserA = anwserA;
    }

    public String getAnwserB() {
        return anwserB;
    }

    public void setAnwserB(String anwserB) {
        this.anwserB = anwserB;
    }

    public String getAnwserC() {
        return anwserC;
    }

    public void setAnwserC(String anwserC) {
        this.anwserC = anwserC;
    }

    public String getAnwserD() {
        return anwserD;
    }

    public void setAnwserD(String anwserD) {
        this.anwserD = anwserD;
    }

    public int getAnwserCorrect() {
        return anwserCorrect;
    }

    public void setAnwserCorrect(int anwserCorrect) {
        this.anwserCorrect = anwserCorrect;
    }

    private String anwserA;
    private String anwserB;
    private String anwserC;
    private String anwserD;
    private int anwserCorrect;
}
