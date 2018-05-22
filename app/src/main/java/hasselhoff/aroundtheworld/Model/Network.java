package hasselhoff.aroundtheworld.Model;

public class Network {
    private int numberOfSubscriber ;
    private int isPrivate;
    private String name;
    private String city;
    private int idNetwork;
    private int idUser;

    public Network(int numberOfSubscriber, String isPrivate, String name, String city, int idNetwork, String idUser) {
        this.numberOfSubscriber = numberOfSubscriber;
        try{
            this.isPrivate = Integer.parseInt(isPrivate);
        }catch (Exception e){
            this.isPrivate = 0;
        }
        this.name = name;
        this.city = city;
        this.idNetwork = idNetwork;
        try{
            this.idUser = Integer.parseInt(idUser);
        }catch (Exception e){
            this.idUser = -1;
        }
    }


    public int isPrivate() {
        return isPrivate;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getIdNetwork() {
        return idNetwork;
    }

    public int getIdUser() {
        return idUser;
    }

    public String toString(){
        return name + " de " + city;
    }
}
