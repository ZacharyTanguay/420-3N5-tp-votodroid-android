package tanguay.votedroid.bd;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import tanguay.votedroid.modele.VDQuestion;
import tanguay.votedroid.modele.VDVote;

@Database(entities = {VDQuestion.class, VDVote.class}, version = 1,  exportSchema = true)
public abstract class BD extends RoomDatabase {
    public abstract MonDao monDao();
}
