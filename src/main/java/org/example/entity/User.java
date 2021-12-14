package org.example.entity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class User {
    UUID id;
    UUID medicineId;
    ArrayList<UUID> noteIds;

    public User() {
    }

    public User(UUID id, UUID medicineId, ArrayList<UUID> noteIds) {
        this.id = id;
        this.medicineId = medicineId;
        this.noteIds = noteIds;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(UUID medicineId) {
        this.medicineId = medicineId;
    }

    public ArrayList<UUID> getNoteIds() {
        return noteIds;
    }

    public void setNoteIds(ArrayList<UUID> noteIds) {
        this.noteIds = noteIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", medicineId=" + medicineId +
                ", noteIds=" + noteIds +
                '}';
    }
}
