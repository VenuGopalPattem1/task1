import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

//append mode
class MyObjectOutputStream extends ObjectOutputStream {
    public MyObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    protected void writeStreamHeader() throws IOException {
        // DO NOTHING (avoid header rewrite)
    }
}

public class Files {

    public static void main(String[] args) throws IOException {
        System.out.println("this is java file system practice");

        // this is for write operations
        // ObjectOutputStream oos = new ObjectOutputStream(new
        // FileOutputStream("data.txt"));

        // oos.writeObject(new User("venu", 21));
        // oos.writeObject(new User("vinod", 25));
        // oos.writeObject(new User("vamsi", 20));
        // oos.writeObject(new User("kasi", 50));
        // oos.close();

        File file = new File("data.txt");

        ObjectOutputStream oos;

        if (file.exists()) {
            oos = new MyObjectOutputStream(
                    new FileOutputStream(file, true));
        } else {
            oos = new ObjectOutputStream(
                    new FileOutputStream(file));
        }

        // oos.writeObject(new User("venu", 21));
        // oos.writeObject(new User("vinod", 25));
        // oos.writeObject(new User("vamsi", 20));
        // oos.writeObject(new User("kasi", 50));
        oos.close();
        System.out.println("-------------------------------");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.txt"));
        try {
            while (true) {
                User u = (User) ois.readObject();
                System.out.println(u.toString());
            }
        } catch (Exception e) {
            // End of file reached (expected)
            System.out.println("Finished reading file");
            // e.printStackTrace();
        }
        ois.close();
    }

}

class User implements Serializable {
    String name;
    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return name + " : " + age;
    }

}
