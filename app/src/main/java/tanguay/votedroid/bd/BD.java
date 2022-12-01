package tanguay.votedroid.bd;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import tanguay.votedroid.modele.VDQuestion;

@Database(entities = {VDQuestion.class}, version = 2,  exportSchema = true)
public abstract class BD extends RoomDatabase {
    public abstract MonDao monDao();
}
