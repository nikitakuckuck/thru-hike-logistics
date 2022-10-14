package learn.thruhike.domain;

import java.util.ArrayList;

public class Result<T> {
    private final ArrayList<String> errorMessages = new ArrayList<>();
    private T payload;

    public ArrayList<String> getErrorMessages() {
        return new ArrayList<>(errorMessages);
    }

    public void addErrorMessage(String message){
        errorMessages.add(message);
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public boolean isSuccess(){
        return errorMessages.size()==0;
    }

}
