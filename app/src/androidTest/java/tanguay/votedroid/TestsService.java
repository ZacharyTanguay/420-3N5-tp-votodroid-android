package tanguay.votedroid;

import tanguay.votedroid.bd.BD;
import tanguay.votedroid.exceptions.MauvaisVote;
import tanguay.votedroid.exceptions.MauvaiseQuestion;
import tanguay.votedroid.modele.VDQuestion;
import tanguay.votedroid.modele.VDVote;
import tanguay.votedroid.service.Service;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestsService {
    private BD bd;
    private Service service;

    // S'exécute avant chacun des tests. Crée une BD en mémoire
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        bd = Room.inMemoryDatabaseBuilder(context, BD.class).build();
        service = new Service(bd);
    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("tanguay.votedroid", appContext.getPackageName());
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOVide() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "";
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOCourte() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "aa";
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOLongue() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        for (int i = 0 ; i < 256 ; i ++) question.texteQuestion += "aa";
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOIDFixe() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "aaaaaaaaaaaaaaaa";
        question.idQuestion = 5L;
        service.creerQuestion(question);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }


    @Test
    public void ajoutQuestionOK() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        service.creerQuestion(question);

        Assert.assertNotNull(question.idQuestion);
    }


    @Test(expected = MauvaiseQuestion.class)
    public void ajoutQuestionKOExiste() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        VDQuestion question2 = new VDQuestion();

        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        question2.texteQuestion = "Aimes-tu les BROWNIES au chocolAT?";

        service.creerQuestion(question);
        service.creerQuestion(question2);

        //TODO Ce test va fail tant que vous n'implémenterez pas toutesLesQuestions() dans ServiceImplementation
        Assert.fail("Exception MauvaiseQuestion non lancée");
    }

    @Test(expected = MauvaiseQuestion.class)
    public void supprimeQuestionOK() throws MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        VDQuestion question2 = new VDQuestion();

        question.texteQuestion = "Aimes-tu les brownies au chocolat?";
        question2.texteQuestion = "Aimes-tu les BROWNIES au chocolAT?";

        service.creerQuestion(question);
        service.creerQuestion(question2);

        service.supprimerQuestions();
        Assert.assertEquals(0, service.toutesLesQuestions().size());
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutVoteKOVide() throws MauvaisVote {
        VDVote vote = new VDVote();
        vote.votant = "";
        service.creerVote(vote);

        Assert.fail("Exception MauvaiseQuestion non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutVoteKOCourte() throws MauvaisVote {
        VDVote vote = new VDVote();
        vote.votant = "Vo";
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test(expected = MauvaisVote.class)
    public void ajoutVoteKOIDFixe() throws MauvaisVote {
        VDVote vote = new VDVote();
        vote.votant = "Votant";
        vote.idVote = 5L;
        service.creerVote(vote);

        Assert.fail("Exception MauvaisVote non lancée");
    }


    @Test
    public void ajoutVoteOK() throws MauvaisVote {
        VDVote vote = new VDVote();
        vote.votant = "Votant";
        service.creerVote(vote);

        Assert.assertNotNull(vote.votant);
    }


    @Test(expected = MauvaisVote.class)
    public void ajoutVoteKOExiste() throws MauvaisVote {
        VDVote vote = new VDVote();
        VDVote vote2 = new VDVote();

        vote.votant = "Votant";
        vote2.votant = "Votant";

        service.creerVote(vote);
        service.creerVote(vote2);

        //TODO Ce test va fail tant que vous n'implémenterez pas toutesLesvotes() dans ServiceImplementation
        Assert.fail("Exception MauvaisVote non lancée");
    }

    @Test
    public void supprimeVoteOK() throws MauvaisVote, MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les BROWNIES au chocolAT?";

        service.creerQuestion(question);
        Assert.assertNotNull(question.idQuestion);

        VDVote vote = new VDVote();
        vote.idQuestion = question.idQuestion;
        vote.votant = "Votant";

        service.creerVote(vote);
        service.supprimerVotes();

        Assert.assertEquals(0, service.toutesLesVotes().size());
    }

    @Test
    public void moyenneVoteOK() throws MauvaisVote, MauvaiseQuestion {
        VDQuestion question = new VDQuestion();
        question.texteQuestion = "Aimes-tu les brownies au chocolat?";

        service.creerQuestion(question);

        VDVote vote = new VDVote();
        VDVote vote2 = new VDVote();
        VDVote vote3 = new VDVote();

        vote.idQuestion = question.idQuestion;
        vote.votant = "Votant";
        vote.valeurVote = 1;
        vote2.idQuestion = question.idQuestion;
        vote2.votant = "VotantDeux";
        vote2.valeurVote = 2;
        vote3.idQuestion = question.idQuestion;
        vote3.votant = "VotantTrois";
        vote3.valeurVote = 3;

        service.creerVote(vote);
        service.creerVote(vote2);
        service.creerVote(vote3);

        service.moyenneVotes(question);
        Assert.assertEquals(2, service.moyenneVotes(question), 0.1);
    }


    /*
    @After
    public void closeDb() {
        bd.close();
    }
    */
}