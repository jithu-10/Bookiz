package hotelbooking;

public class QA {
    private String question;
    private String answer;

    public QA(String question){
        this(question,null);
    }

    public QA(String question, String answer){
        this.question=question;
        this.answer=answer;
    }

    public String getQuestion(){
        return question;
    }

    public String getAnswer(){
        return answer;
    }


    public void setAnswer(String answer){
        Helper.validateString(answer,"Answer can't be null or empty");
        this.answer=answer;
    }

}
