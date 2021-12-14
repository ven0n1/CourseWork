package org.example;

public class HistoryContent {
    String className;
    String createdDate;
    String actor = Constants.ACTOR;
    public enum Status {SUCCESS, FAULT};
    Status status;
    String jsonEntity;

    public HistoryContent(String className, String createdDate, Status status, String jsonEntity) {
        this.className = className;
        this.createdDate = createdDate;
        this.status = status;
        this.jsonEntity = jsonEntity;
    }

    public HistoryContent(String className, String createdDate, String actor, Status status, String jsonEntity) {
        this.className = className;
        this.createdDate = createdDate;
        this.actor = actor;
        this.status = status;
        this.jsonEntity = jsonEntity;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getJsonEntity() {
        return jsonEntity;
    }

    public void setJsonEntity(String jsonEntity) {
        this.jsonEntity = jsonEntity;
    }

    @Override
    public String toString() {
        return "HistoryContent{" +
                "className='" + className + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", actor='" + actor + '\'' +
                ", status=" + status +
                ", jsonEntity='" + jsonEntity + '\'' +
                '}';
    }
}
