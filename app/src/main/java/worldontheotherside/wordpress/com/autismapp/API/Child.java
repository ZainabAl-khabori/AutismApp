package worldontheotherside.wordpress.com.autismapp.API;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by زينب on 2/23/2018.
 */

public class Child {

    private String name;
    private String gender;
    private int age;
    private String photo;
    private int autismSpectrumScore;

    public Child()
    {
        //
    }

    public Child(DataSnapshot dataSnapshot)
    {
        Child child = dataSnapshot.getValue(Child.class);

        name = child.name;
        gender = child.gender;
        age = child.age;
        photo = child.photo;
        autismSpectrumScore = child.autismSpectrumScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getAutismSpectrumScore() {
        return autismSpectrumScore;
    }

    public void setAutismSpectrumScore(int autismSpectrumScore) {
        this.autismSpectrumScore = autismSpectrumScore;
    }
}
