package ir.ac.kntu;

public class Request {
    private String request;
    private String answer;
    private RequestStatus status;
    private RequestSection section;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public RequestSection getSection() {
        return section;
    }

    public void setSection(RequestSection section) {
        this.section = section;
    }
}
