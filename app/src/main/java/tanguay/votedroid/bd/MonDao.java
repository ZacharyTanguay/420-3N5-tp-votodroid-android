package tanguay.votedroid.bd;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tanguay.votedroid.modele.VDQuestion;
import tanguay.votedroid.modele.VDVote;

@Dao
public interface MonDao {
    @Insert
    Long insertQuestion(VDQuestion v);

    @Insert
    Long insertVote(VDVote v);

    @Delete
    void deleteQuestion(VDQuestion q);

    @Delete
    void deleteVote(VDVote v);

    //TODO Compléter les autres actions
    @Query("SELECT * FROM VDQuestion ORDER BY (SELECT COUNT(*) FROM VDVote WHERE idQuestion = VDQuestion.idQuestion) DESC")
    List<VDQuestion> listeQuestions();

    @Query("SELECT * FROM VDVote")
    List<VDVote> listeVotes();

    @Query("SELECT * FROM VDVote WHERE idQuestion = :idQuestion")
    List<VDVote> VoteParQuestion(long idQuestion);

}
