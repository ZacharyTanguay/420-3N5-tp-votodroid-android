package tanguay.votedroid.service;

import tanguay.votedroid.bd.BD;
import tanguay.votedroid.exceptions.MauvaisVote;
import tanguay.votedroid.exceptions.MauvaiseQuestion;
import tanguay.votedroid.modele.VDQuestion;
import tanguay.votedroid.modele.VDVote;

import java.util.List;
import java.util.Map;

public class Service {

    private BD maBD;

    public Service(BD maBD){
        this.maBD = maBD;
    }

    public void creerQuestion(VDQuestion vdQuestion) throws MauvaiseQuestion {
        // Validation
        if (vdQuestion.texteQuestion == null || vdQuestion.texteQuestion.trim().length() == 0) throw new MauvaiseQuestion("Question vide");
        if (vdQuestion.texteQuestion.trim().length() < 5) throw new MauvaiseQuestion("Question trop courte");
        if (vdQuestion.texteQuestion.trim().length() > 255) throw new MauvaiseQuestion("Question trop longue");
        if (vdQuestion.idQuestion != null) throw new MauvaiseQuestion("Id non nul. La BD doit le gérer");

        // Doublon du texte de la question
        for (VDQuestion q : toutesLesQuestions()){
            if (q.texteQuestion.toUpperCase().equals(vdQuestion.texteQuestion.toUpperCase())){
                    throw new MauvaiseQuestion("Question existante");
            }
        }

        vdQuestion.idQuestion = maBD.monDao().insertQuestion(vdQuestion);
    }

    
    public void creerVote(VDVote vdVote) throws MauvaisVote {
        if (vdVote.votant == null || vdVote.votant.trim().length() == 0) throw new MauvaisVote("Nom du votant vide");
        if (vdVote.votant.trim().length() < 5) throw new MauvaisVote("Nom du votant trop court");

        vdVote.idQuestion = maBD.monDao().insertVote(vdVote);
    }

    
    public List<VDQuestion> toutesLesQuestions() {
        //TODO Présentement :   retourne une liste vide
        //TODO À faire :        trier la liste reçue en BD par le nombre de votes et la retourner
        List<VDQuestion> toutesLesQuestions = maBD.monDao().listeQuestions();
        return toutesLesQuestions;
    }

    public VDQuestion questionParId(long id) {
        List<VDQuestion> listQuestion = maBD.monDao().listeQuestions();
        for (VDQuestion q: listQuestion) {
            if (q.idQuestion == id) {
                return q;
            }
        }
        return null;
    }

    
    public float moyenneVotes(VDQuestion question) {
        return 0;
    }

    
    public Map<Integer, Integer> distributionVotes(VDQuestion question) {
        return null;
    }
	
	public void supprimerQuestions(){
        List<VDQuestion> listQuestion = maBD.monDao().listeQuestions();
        for (VDQuestion q: listQuestion) {
            maBD.monDao().deleteQuestion(q);
        }
	}
	
	public void supprimerVotes(){
	}
}
