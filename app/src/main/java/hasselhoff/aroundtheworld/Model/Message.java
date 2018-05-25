package hasselhoff.aroundtheworld.Model;

public class Message {
    private String message;
    private String date;
    private String nom;
    private String prenom;

    public Message(String message, String date,String nom, String prenom) {
        this.message = message;
        this.date = date;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getMessage() {
        return message;
    }

    public String toString(){
        return prenom+ " " + nom + "  - " +  date +"\n\t" + message;
    }
}
