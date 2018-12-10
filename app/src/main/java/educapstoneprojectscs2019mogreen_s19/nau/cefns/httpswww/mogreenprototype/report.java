package educapstoneprojectscs2019mogreen_s19.nau.cefns.httpswww.mogreenprototype;

public class report {
    String content;
    String photoID = "None";
    String user = "Chase Test";

    public void report(String report, String imageName, String email){
        content = report;
        photoID =imageName;
        user = email;

    }

    public void addContent(String report){
        content = report;
    }

    public void addImage(String image){
        photoID = image;
    }

    public void addUser(String email){
        user = email;
    }
}
