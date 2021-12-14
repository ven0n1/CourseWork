package org.example.entity;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    Medicine medicine;
    ArrayList<Note> noteArrayList;

    public User(Medicine medicine, ArrayList<Note> noteArrayList) {
        this.medicine = medicine;
        this.noteArrayList = noteArrayList;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public ArrayList<Note> getNoteArrayList() {
        return noteArrayList;
    }

    public void setNoteArrayList(ArrayList<Note> noteArrayList) {
        this.noteArrayList = noteArrayList;
    }

    @Override
    public String toString() {
        return "User{" +
                "medicine=" + medicine +
                ", noteArrayList=" + noteArrayList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(medicine, user.medicine) && Objects.equals(noteArrayList, user.noteArrayList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicine, noteArrayList);
    }
}
